package giis.demo.jdbc.equipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import giis.demo.exceptions.PersistenceException;
import giis.demo.model.equipo.EventoOcupacion;

public class EntrenamientoJdbc {

	private static final String QUERY_TRAINERS_WITH_TEAM = 
			"SELECT ed.dni FROM EmpleadoDeportivo ed "
			+ "WHERE ed.posicion = 'ENTRENADOR' AND ed.id_equipo IS NOT NULL ORDER BY ed.dni";
	private static final String QUERY_TEAM_NAME_BY_TRAINER = 
			"SELECT e.nombre FROM Equipo e "
			+ "JOIN EmpleadoDeportivo ed ON e.id_equipo = ed.id_equipo "
			+ "WHERE ed.dni = ?";
	private static final String QUERY_TEAM_ID_BY_TRAINER = 
			"SELECT id_equipo FROM EmpleadoDeportivo WHERE dni = ?";
	private static final String QUERY_PARTIDOS_INSTALACION = 
			"SELECT p.hora_inicio, p.hora_fin, eqL.nombre AS local, eqV.nombre AS visitante "
			+ "FROM Partido p JOIN Equipo eqL ON p.id_local = eqL.id_equipo "
			+ "JOIN Equipo eqV ON p.id_visitante = eqV.id_equipo "
			+ "WHERE p.id_instalacion = ? AND p.fecha = ?";
	private static final String QUERY_ENTRENAMIENTOS_INSTALACION = 
			"SELECT e.hora_inicio, e.hora_fin, eq.nombre AS equipo, e.id_equipo "
			+ "FROM Entrenamiento e JOIN Equipo eq ON e.id_equipo = eq.id_equipo "
			+ "WHERE e.id_instalacion = ? AND e.fecha = ?";

	// Obtiene tanto franjas libres como franjas con entrevista asignada para los jugadores del equipo
	private static final String QUERY_FRANJAS_Y_ENTREVISTAS = 
			"SELECT f.id_franja, ent.id_entrevista, be.nombre, be.apellido, f.hora_inicio, f.hora_fin, ent.nombre_medio "
			+ "FROM FranjaHoraria f "
			+ "JOIN EmpleadoDeportivo ed ON f.dni_jugador = ed.dni "
			+ "JOIN BaseEmpleado be ON ed.dni = be.dni "
			+ "LEFT JOIN Entrevista ent ON f.id_franja = ent.id_franja "
			+ "WHERE ed.id_equipo = ? AND f.fecha = ? "
			+ "ORDER BY f.hora_inicio";

	private static final String QUERY_RESERVAS_INSTALACION = 
			"SELECT r.id_reserva, r.hora_inicio, r.hora_fin, u.nombre "
			+ "FROM Reserva r JOIN Usuario u ON r.id_usuario = u.id_usuario "
			+ "WHERE r.id_instalacion = ? AND r.fecha = ?";
	
	private static final String QUERY_TAREAS_MANTENIMIENTO_INSTALACION = 
			"SELECT tm.id_tarea, tm.hora_inicio, tm.hora_fin, tm.descripcion, "
			+ "GROUP_CONCAT(be.nombre || ' ' || be.apellido, ', ') AS jardineros "
			+ "FROM TareaMantenimiento tm "
			+ "LEFT JOIN TareaMantenimientoJardinero tmj ON tm.id_tarea = tmj.id_tarea "
			+ "LEFT JOIN BaseEmpleado be ON tmj.dni_jardinero = be.dni "
			+ "WHERE tm.id_instalacion = ? AND tm.fecha = ? "
			+ "GROUP BY tm.id_tarea "
			+ "ORDER BY tm.hora_inicio";

	private static final String QUERY_INSERT_TRAINING = 
			"INSERT INTO Entrenamiento (fecha, hora_inicio, hora_fin, id_equipo, id_instalacion) "
			+ "VALUES (?, ?, ?, ?, ?)";

	private static final String DELETE_RESERVA_BY_ID = "DELETE FROM Reserva WHERE id_reserva = ?";
	private static final String DELETE_TAREA_MANTENIMIENTO_JARDINEROS = 
			"DELETE FROM TareaMantenimientoJardinero WHERE id_tarea = ?";
	private static final String DELETE_TAREA_MANTENIMIENTO_BY_ID = 
			"DELETE FROM TareaMantenimiento WHERE id_tarea = ?";
	private static final String DELETE_ENTREVISTA_BY_FRANJA = "DELETE FROM Entrevista WHERE id_franja = ?";
	private static final String DELETE_FRANJA_BY_ID = "DELETE FROM FranjaHoraria WHERE id_franja = ?";

	/**
	 * Obtiene una lista de dnis de los entrenadores con equipo asignado
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static List<String> obtenerDnisEntrenadoresConEquipo(Connection con) throws SQLException {
		List<String> dnis = new ArrayList<>();
		try (PreparedStatement pst = con.prepareStatement(QUERY_TRAINERS_WITH_TEAM);
			 ResultSet rs = pst.executeQuery()) {
			while (rs.next()) {
				dnis.add(rs.getString("dni"));
			}
		}
		return dnis;
	}

	/**
	 * Devuelve el nombre del equipo por el id del entrenador
	 * @param con
	 * @param dniEntrenador
	 * @return
	 * @throws SQLException
	 */
	public static String obtenerNombreEquipoPorDniEntrenador(Connection con, String dniEntrenador) throws SQLException {
		try (PreparedStatement pst = con.prepareStatement(QUERY_TEAM_NAME_BY_TRAINER)) {
			pst.setString(1, dniEntrenador);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return rs.getString("nombre");
				}
			}
		}
		return "Sin equipo";
	}

	/**
	 * Devuelve el id del equipo por el dni del entrenador
	 * @param con
	 * @param dniEntrenador
	 * @return
	 * @throws SQLException
	 */
	public static int obtenerIdEquipoPorDniEntrenador(Connection con, String dniEntrenador) throws SQLException {
		try (PreparedStatement pst = con.prepareStatement(QUERY_TEAM_ID_BY_TRAINER)) {
			pst.setString(1, dniEntrenador);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id_equipo");
				}
			}
		}
		return 0;
	}

	/**
	 * Devuelve una lista con los eventos de ocupacion de 
	 * la instalacion para esa fecha y hora
	 * @param con
	 * @param idEquipo
	 * @param idInstalacion
	 * @param fecha
	 * @return
	 * @throws SQLException
	 */
	public static List<EventoOcupacion> obtenerEventosOcupacion(Connection con, int idEquipo, int idInstalacion, LocalDate fecha) throws SQLException {
		List<EventoOcupacion> eventos = new ArrayList<>();

		// 1. Partidos en la instalación (No sobreescribible)
		try (PreparedStatement pst = con.prepareStatement(QUERY_PARTIDOS_INSTALACION)) {
			pst.setInt(1, idInstalacion);
			pst.setString(2, fecha.toString());
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					eventos.add(new EventoOcupacion(parseHora(rs.getString("hora_inicio")), parseHora(rs.getString("hora_fin")),
							"Partido", rs.getString("local") + " vs " + rs.getString("visitante"), false));
				}
			}
		}

		// 2. Entrenamientos en la instalación (No sobreescribible)
		try (PreparedStatement pst = con.prepareStatement(QUERY_ENTRENAMIENTOS_INSTALACION)) {
			pst.setInt(1, idInstalacion);
			pst.setString(2, fecha.toString());
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					String eqNombre = rs.getString("equipo");
					eventos.add(new EventoOcupacion(parseHora(rs.getString("hora_inicio")), parseHora(rs.getString("hora_fin")),
							"Entrenamiento", "Equipo: " + eqNombre, false));
				}
			}
		}

		// 3. Franjas y Entrevistas de jugadores del equipo (Sobreescribibles por el entrenamiento)
		if (idEquipo > 0) {
			try (PreparedStatement pst = con.prepareStatement(QUERY_FRANJAS_Y_ENTREVISTAS)) {
				pst.setInt(1, idEquipo);
				pst.setString(2, fecha.toString());
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						int idFranja = rs.getInt("id_franja");
						String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
						String medio = rs.getString("nombre_medio");
						LocalTime hIni = parseHora(rs.getString("hora_inicio"));
						LocalTime hFin = parseHora(rs.getString("hora_fin"));

						if (medio != null && !medio.isBlank()) {
							// Entrevista asignada
							eventos.add(new EventoOcupacion(idFranja, "ENTREVISTA", hIni, hFin,
									"Entrevista", nombreCompleto + " (" + medio + ")", true));
						} else {
							// Franja libre creada para el jugador
							eventos.add(new EventoOcupacion(idFranja, "FRANJA", hIni, hFin,
									"Franja horaria", nombreCompleto + " (Franja libre)", true));
						}
					}
				}
			}
		}

		// 4. Reservas externas (Sobreescribible)
		try (PreparedStatement pst = con.prepareStatement(QUERY_RESERVAS_INSTALACION)) {
			pst.setInt(1, idInstalacion);
			pst.setString(2, fecha.toString());
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					eventos.add(new EventoOcupacion(rs.getInt("id_reserva"), "RESERVA",
							parseHora(rs.getString("hora_inicio")), parseHora(rs.getString("hora_fin")),
							"Reserva externa", "Socio: " + rs.getString("nombre"), true));
				}
			}
		}

		// 5. Jardinería (Sobreescribible)
		// 5. Tareas de jardinería en la instalación (Sobreescribible)
		try (PreparedStatement pst = con.prepareStatement(QUERY_TAREAS_MANTENIMIENTO_INSTALACION)) {
			pst.setInt(1, idInstalacion);
			pst.setString(2, fecha.toString());
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					String desc = rs.getString("descripcion");
					String jardineros = rs.getString("jardineros");
					String det = (jardineros != null && !jardineros.isBlank()) ? jardineros + " (" + desc + ")" : desc;
					eventos.add(new EventoOcupacion(rs.getInt("id_tarea"), "TAREA_MANTENIMIENTO",
							parseHora(rs.getString("hora_inicio")), parseHora(rs.getString("hora_fin")), "Jardinería",
							det, true));
				}
			}
		}

		eventos.sort(Comparator.comparing(EventoOcupacion::getHoraInicio));
		return eventos;
	}

	/**
	 * Comprueba las colisiones
	 * @param con
	 * @param fecha
	 * @param horaInicio
	 * @param horaFin
	 * @param idEquipo
	 * @param idInstalacion
	 * @throws SQLException
	 * @throws PersistenceException
	 */
	public static void comprobarColisiones(Connection con, LocalDate fecha, LocalTime horaInicio,
			LocalTime horaFin, int idEquipo, int idInstalacion) throws SQLException, PersistenceException {
		List<EventoOcupacion> eventos = obtenerEventosOcupacion(con, idEquipo, idInstalacion, fecha);
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
		for (EventoOcupacion e : eventos) {
			if (!e.isSobreescribible()) {
				if (horaInicio.isBefore(e.getHoraFin()) && horaFin.isAfter(e.getHoraInicio())) {
					throw new PersistenceException("Existe una colisión con " + e.getTipo().toLowerCase() + " '"
							+ e.getDetalle() + "' (" + e.getHoraInicio().format(fmt) + " - " + e.getHoraFin().format(fmt)
							+ ").\nPor favor, escoja otro horario, ya que hay colisión.");
				}
			}
		}
	}

	/**
	 * Devuelve una lista de eventoos de ocupacion sobreescribibles
	 * que se solapan con el entrenamiento a añadir
	 * @param con
	 * @param idEquipo
	 * @param idInstalacion
	 * @param fecha
	 * @param horaInicio
	 * @param horaFin
	 * @return
	 * @throws SQLException
	 */
	public static List<EventoOcupacion> obtenerEventosSobreescribiblesSolapados(Connection con, int idEquipo,
			int idInstalacion, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) throws SQLException {
		List<EventoOcupacion> todos = obtenerEventosOcupacion(con, idEquipo, idInstalacion, fecha);
		List<EventoOcupacion> solapados = new ArrayList<>();
		for (EventoOcupacion e : todos) {
			if (e.isSobreescribible()) {
				if (horaInicio.isBefore(e.getHoraFin()) && horaFin.isAfter(e.getHoraInicio())) {
					solapados.add(e);
				}
			}
		}
		return solapados;
	}

	/**
	 * Guarda el entrenamiento seleccionado, eliminando los eventos 
	 * coincidentes de forma transaccional
	 * @param con
	 * @param fecha
	 * @param horaInicio
	 * @param horaFin
	 * @param idEquipo
	 * @param idInstalacion
	 * @param eventosACancelar
	 * @throws SQLException
	 */
	public static void guardarEntrenamientoCancelandoCoincidentes(Connection con, LocalDate fecha,
			LocalTime horaInicio, LocalTime horaFin, int idEquipo, int idInstalacion,
			List<EventoOcupacion> eventosACancelar) throws SQLException {

		boolean autoCommitOriginal = con.getAutoCommit();
		con.setAutoCommit(false);
		try {
			// 1. Eliminar reservas, jardinería, entrevistas y franjas coincidentes
			if (eventosACancelar != null) {
				for (EventoOcupacion e : eventosACancelar) {
					if ("RESERVA".equals(e.getOrigen())) {
						try (PreparedStatement pst = con.prepareStatement(DELETE_RESERVA_BY_ID)) {
							pst.setInt(1, e.getIdReferencia());
							pst.executeUpdate();
						}
					} else if ("TAREA_MANTENIMIENTO".equals(e.getOrigen())) {
						// 1º Suprimir la asignación a los jardineros
						try (PreparedStatement pst = con.prepareStatement(DELETE_TAREA_MANTENIMIENTO_JARDINEROS)) {
							pst.setInt(1, e.getIdReferencia());
							pst.executeUpdate();
						}
						// 2º Eliminar la tarea de la instalación
						try (PreparedStatement pst = con.prepareStatement(DELETE_TAREA_MANTENIMIENTO_BY_ID)) {
							pst.setInt(1, e.getIdReferencia());
							pst.executeUpdate();
						}
					} else if ("ENTREVISTA".equals(e.getOrigen())) {
						// 1º Borrar la entrevista asociada a la franja
						try (PreparedStatement pst = con.prepareStatement(DELETE_ENTREVISTA_BY_FRANJA)) {
							pst.setInt(1, e.getIdReferencia());
							pst.executeUpdate();
						}
						// 2º Borrar la franja horaria
						try (PreparedStatement pst = con.prepareStatement(DELETE_FRANJA_BY_ID)) {
							pst.setInt(1, e.getIdReferencia());
							pst.executeUpdate();
						}
					} else if ("FRANJA".equals(e.getOrigen())) {
						// Borrar la franja horaria libre
						try (PreparedStatement pst = con.prepareStatement(DELETE_FRANJA_BY_ID)) {
							pst.setInt(1, e.getIdReferencia());
							pst.executeUpdate();
						}
					}
				}
			}

			// 2. Insertar el entrenamiento
			try (PreparedStatement pst = con.prepareStatement(QUERY_INSERT_TRAINING)) {
				pst.setString(1, fecha.toString());
				pst.setString(2, horaInicio.toString());
				pst.setString(3, horaFin.toString());
				pst.setInt(4, idEquipo);
				pst.setInt(5, idInstalacion);
				int filas = pst.executeUpdate();
				if (filas != 1) {
					throw new SQLException("Error al registrar el entrenamiento en la base de datos.");
				}
			}

			con.commit();
		} catch (SQLException ex) {
			con.rollback();
			throw ex;
		} finally {
			con.setAutoCommit(autoCommitOriginal);
		}
	}

	private static LocalTime parseHora(String hStr) {
		return LocalTime.parse(hStr, DateTimeFormatter.ofPattern(hStr.length() == 5 ? "HH:mm" : "HH:mm:ss"));
	}
}