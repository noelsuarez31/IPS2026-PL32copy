package giis.demo.jdbc.equipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import giis.demo.model.empleado.EmpleadoDeportivo;
import giis.demo.model.entradas.enumerados.TipoSeccion;
import giis.demo.model.equipo.Equipo;
import giis.demo.model.equipo.EventoOcupacion;

public class EquipoJdbc {

	public final static String QUERY_GET_CATEGORY_ID = "SELECT id_categoria FROM CategoriaEquipo WHERE nombre=?";

	public final static String QUERY_CREATE_TEAM = "INSERT INTO Equipo (nombre, es_propio, tipo_equipo, id_categoria) VALUES (?,?,?,?)";

	public final static String QUERY_SAVE_TEAM_MEMBERS = "UPDATE EmpleadoDeportivo SET id_equipo=? WHERE dni=?";

	public final static String QUERY_CHECK_MEMBER_TEAM = "SELECT id_equipo FROM EmpleadoDeportivo WHERE dni=?";

	public final static String QUERY_GET_LOCAL_TEAMS = "SELECT * FROM Equipo WHERE es_propio = 1 ORDER BY nombre";

	public final static String QUERY_GET_EXTERNAL_TEAMS = "SELECT id_equipo, nombre, tipo_equipo FROM Equipo WHERE es_propio = 0 ORDER BY nombre";

	private static final String QUERY_PARTIDOS_INSTALACION = 
			"SELECT p.hora_inicio, p.hora_fin, eqL.nombre AS local, eqV.nombre AS visitante "
			+ "FROM Partido p JOIN Equipo eqL ON p.id_local = eqL.id_equipo "
			+ "JOIN Equipo eqV ON p.id_visitante = eqV.id_equipo "
			+ "WHERE p.id_instalacion = ? AND p.fecha = ?";
	
	private static final String QUERY_ENTRENAMIENTOS_INSTALACION = 
			"SELECT e.hora_inicio, e.hora_fin, eq.nombre AS equipo "
			+ "FROM Entrenamiento e JOIN Equipo eq ON e.id_equipo = eq.id_equipo "
			+ "WHERE e.id_instalacion = ? AND e.fecha = ?";
	
	private static final String QUERY_FRANJAS_Y_ENTREVISTAS = 
			"SELECT f.id_franja, ent.id_entrevista, be.nombre, be.apellido, f.hora_inicio, f.hora_fin, ent.nombre_medio "
			+ "FROM FranjaHoraria f "
			+ "JOIN EmpleadoDeportivo ed ON f.dni_jugador = ed.dni "
			+ "JOIN BaseEmpleado be ON ed.dni = be.dni "
			+ "LEFT JOIN Entrevista ent ON f.id_franja = ent.id_franja "
			+ "WHERE f.fecha = ? "
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
	
	private static final String QUERY_INSERT_PARTIDO = 
			"INSERT INTO Partido (fecha, hora_inicio, hora_fin, id_local, id_visitante, id_instalacion, dia_del_club, precio_abonado) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String QUERY_INSERT_PRECIO_SECCION = 
			"INSERT INTO PrecioSeccion (id_partido, seccion, precio) VALUES (?, ?, ?)";
	
	private static final String DELETE_RESERVA_BY_ID = "DELETE FROM Reserva WHERE id_reserva = ?";
	
	private static final String DELETE_TAREA_MANTENIMIENTO_JARDINEROS = 
			"DELETE FROM TareaMantenimientoJardinero WHERE id_tarea = ?";
	
	private static final String DELETE_TAREA_MANTENIMIENTO_BY_ID = 
			"DELETE FROM TareaMantenimiento WHERE id_tarea = ?";
	
	private static final String DELETE_ENTREVISTA_BY_FRANJA = "DELETE FROM Entrevista WHERE id_franja = ?";
	
	private static final String DELETE_FRANJA_BY_ID = "DELETE FROM FranjaHoraria WHERE id_franja = ?";
	
	/**
	 * Almacena un equipo en la BBDD. Saca el id_equipo de la BBDD
	 * (autoincremental). Comprueba que los jugadores, los entrenadores y los
	 * miembros del cuerpo tecnico adicional no tienen equipo asignado (ya que no se
	 * pueden repetir). Si no lo tienen, les asigna el id_equipo correspondiente
	 * 
	 * @param con
	 * @param equipo
	 * @param jugadores
	 * @param entrenadores
	 * @param tecnicosAdicionales
	 * @throws SQLException
	 */
	public static void almacenarEquipo(Connection con, Equipo equipo, List<EmpleadoDeportivo> jugadores,
			List<EmpleadoDeportivo> entrenadores, List<EmpleadoDeportivo> tecnicosAdicionales) throws SQLException {

		boolean originalAutoCommit = con.getAutoCommit();

		try {
			con.setAutoCommit(false);

			int idCategoria = 0;
			try (PreparedStatement pstGetCategoryId = con.prepareStatement(QUERY_GET_CATEGORY_ID)) {
				pstGetCategoryId.setString(1, equipo.getCategoriaEquipo().getNombre());

				try (ResultSet rsCategoria = pstGetCategoryId.executeQuery()) {
					while (rsCategoria.next()) {
						idCategoria = rsCategoria.getInt(1);
					}
				}
			}

			for (EmpleadoDeportivo ed : jugadores) {
				validarMiembroSinEquipo(con, ed.getDni(), ed.getNombre(), "jugador");
			}

			for (EmpleadoDeportivo entr : entrenadores) {
				validarMiembroSinEquipo(con, entr.getDni(), entr.getNombre(), "entrenador");
			}

			for (EmpleadoDeportivo ed : tecnicosAdicionales) {
				validarMiembroSinEquipo(con, ed.getDni(), ed.getNombre(), "técnico adicional");
			}

			try (PreparedStatement pstCreateTeam = con.prepareStatement(QUERY_CREATE_TEAM,
					Statement.RETURN_GENERATED_KEYS)) {
				pstCreateTeam.setString(1, equipo.getNombre());
				pstCreateTeam.setInt(2, equipo.getIsPropio() ? 1 : 0);
				pstCreateTeam.setString(3, equipo.getTipoEquipo());
				pstCreateTeam.setInt(4, idCategoria);

				int updatedRows = pstCreateTeam.executeUpdate();
				if (updatedRows != 1) {
					throw new RuntimeException("numero de filas actualizadas incorrecto");
				}

				try (ResultSet generatedKeys = pstCreateTeam.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int idGenerado = generatedKeys.getInt(1);
						equipo.setIdEquipo(idGenerado);
					} else {
						throw new SQLException("Creación de equipo fallida, no se obtuvo el ID.");
					}
				}
			}

			for (EmpleadoDeportivo ed : jugadores) {
				try (PreparedStatement pstSaveJugadores = con.prepareStatement(QUERY_SAVE_TEAM_MEMBERS)) {
					pstSaveJugadores.setInt(1, equipo.getIdEquipo());
					pstSaveJugadores.setString(2, ed.getDni());
					pstSaveJugadores.executeUpdate();
				}
			}

			for (EmpleadoDeportivo entr : entrenadores) {
				try (PreparedStatement pstSaveEntrenadores = con.prepareStatement(QUERY_SAVE_TEAM_MEMBERS)) {
					pstSaveEntrenadores.setInt(1, equipo.getIdEquipo());
					pstSaveEntrenadores.setString(2, entr.getDni());
					pstSaveEntrenadores.executeUpdate();
				}
			}

			for (EmpleadoDeportivo ed : tecnicosAdicionales) {
				try (PreparedStatement pstSaveTecAdicionales = con.prepareStatement(QUERY_SAVE_TEAM_MEMBERS)) {
					pstSaveTecAdicionales.setInt(1, equipo.getIdEquipo());
					pstSaveTecAdicionales.setString(2, ed.getDni());
					pstSaveTecAdicionales.executeUpdate();
				}
			}

			con.commit();

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			throw e;
		} finally {
			try {
				con.setAutoCommit(originalAutoCommit);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void validarMiembroSinEquipo(Connection con, String dni, String nombre, String rol)
			throws SQLException {
		try (PreparedStatement pstCheck = con.prepareStatement(QUERY_CHECK_MEMBER_TEAM)) {
			pstCheck.setString(1, dni);
			try (ResultSet rs = pstCheck.executeQuery()) {
				if (rs.next()) {
					rs.getInt("id_equipo");
					if (!rs.wasNull()) {
						throw new SQLException(
								"El " + rol + " " + nombre + " (" + dni + ") ya pertenece a otro equipo.");
					}
				}
			}
		}
	}

	/**
	 * Devuelve una lista de equipos de la organizacion
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static List<Equipo> getEquiposPropios(Connection con) throws SQLException {

		List<Equipo> equipos = new ArrayList<Equipo>();

		try (PreparedStatement pst = con.prepareStatement(QUERY_GET_LOCAL_TEAMS); ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {

				Equipo eq = new Equipo(rs.getString("nombre"), rs.getString("tipo_equipo"), null);

				eq.setIdEquipo(rs.getInt("id_equipo"));
				eq.setIsPropio(true);
				equipos.add(eq);

			}
		}

		return equipos;
	}

	/**
	 * Obtiene los equipos visitantes externos de la base de datos (es_propio = 0)
	 */
	public static List<Equipo> getEquiposExternos(Connection con) throws SQLException {
		List<Equipo> equipos = new ArrayList<>();
		try (PreparedStatement pst = con.prepareStatement(QUERY_GET_EXTERNAL_TEAMS);
				ResultSet rs = pst.executeQuery()) {
			while (rs.next()) {
				Equipo eq = new Equipo(rs.getString("nombre"), rs.getString("tipo_equipo"), null);
				eq.setIdEquipo(rs.getInt("id_equipo"));
				eq.setIsPropio(false);
				equipos.add(eq);
			}
		}
		return equipos;
	}
	
	/**
	 * Obtiene todos los eventos de ocupación (partidos, entrenamientos, entrevistas, franjas,
	 * reservas y jardinería) para una instalación y fecha dadas.
	 */
	public static List<EventoOcupacion> obtenerEventosOcupacionPartido(Connection con, int idInstalacion,
			LocalDate fecha) throws SQLException {
		List<EventoOcupacion> eventos = new ArrayList<>();
		// 1. Partidos en la instalación (No sobreescribible)
		try (PreparedStatement pst = con.prepareStatement(QUERY_PARTIDOS_INSTALACION)) {
			pst.setInt(1, idInstalacion);
			pst.setString(2, fecha.toString());
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					eventos.add(new EventoOcupacion(
							parseHora(rs.getString("hora_inicio")), parseHora(rs.getString("hora_fin")),
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
					eventos.add(new EventoOcupacion(
							parseHora(rs.getString("hora_inicio")), parseHora(rs.getString("hora_fin")),
							"Entrenamiento", "Equipo: " + rs.getString("equipo"), false));
				}
			}
		}
		
		// 3. Franjas y Entrevistas de jugadores (Sobreescribible)
		try (PreparedStatement pst = con.prepareStatement(QUERY_FRANJAS_Y_ENTREVISTAS)) {
			pst.setString(1, fecha.toString());
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					int idFranja = rs.getInt("id_franja");
					String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
					String medio = rs.getString("nombre_medio");
					LocalTime hIni = parseHora(rs.getString("hora_inicio"));
					LocalTime hFin = parseHora(rs.getString("hora_fin"));
					if (medio != null && !medio.isBlank()) {
						eventos.add(new EventoOcupacion(idFranja, "ENTREVISTA", hIni, hFin,
								"Entrevista", nombreCompleto + " (" + medio + ")", true));
					} else {
						eventos.add(new EventoOcupacion(idFranja, "FRANJA", hIni, hFin,
								"Franja horaria", nombreCompleto + " (Franja libre)", true));
					}
				}
			}
		}
		
		// 4. Reservas externas en la instalación (Sobreescribible)
		try (PreparedStatement pst = con.prepareStatement(QUERY_RESERVAS_INSTALACION)) {
			pst.setInt(1, idInstalacion);
			pst.setString(2, fecha.toString());
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					eventos.add(new EventoOcupacion(rs.getInt("id_reserva"), "RESERVA",
							parseHora(rs.getString("hora_inicio")), parseHora(rs.getString("hora_fin")),
							"Reserva", "Usuario: " + rs.getString("nombre"), true));
				}
			}
		}
		
		// 5. Tareas de jardinería reales creadas en VentanaAsignarMantenimiento (Sobreescribible)
				try (PreparedStatement pst = con.prepareStatement(QUERY_TAREAS_MANTENIMIENTO_INSTALACION)) {
					pst.setInt(1, idInstalacion);
					pst.setString(2, fecha.toString());
					try (ResultSet rs = pst.executeQuery()) {
						while (rs.next()) {
							String jardineros = rs.getString("jardineros");
							String desc = rs.getString("descripcion");
							String det = (jardineros != null && !jardineros.isBlank())
									? jardineros + " (" + desc + ")"
									: desc;
							eventos.add(new EventoOcupacion(rs.getInt("id_tarea"), "TAREA_MANTENIMIENTO",
									parseHora(rs.getString("hora_inicio")), parseHora(rs.getString("hora_fin")),
									"Jardinería", det, true));
						}
					}
				}
		
		eventos.sort(Comparator.comparing(EventoOcupacion::getHoraInicio));
		return eventos;
	}
	
	/**
	 * 1. Comprueba si el horario colisiona con otro partido o entrenamiento en la misma instalación.
	 */
	public static void comprobarColisionesPartido(Connection con, LocalDate fecha, LocalTime horaInicio,
			LocalTime horaFin, int idInstalacion) throws SQLException {
		List<EventoOcupacion> eventos = obtenerEventosOcupacionPartido(con, idInstalacion, fecha);
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
		for (EventoOcupacion e : eventos) {
			if (!e.isSobreescribible()) {
				// Condición de solapamiento: inicio < fin2 && fin > inicio2
				if (horaInicio.isBefore(e.getHoraFin()) && horaFin.isAfter(e.getHoraInicio())) {
					throw new SQLException("Existe una colisión con " + e.getTipo().toLowerCase() + " '"
							+ e.getDetalle() + "' (" + e.getHoraInicio().format(fmt) + " - " + e.getHoraFin().format(fmt)
							+ ").\nPor favor, escoja otro horario o instalación.");
				}
			}
		}
	}
	/**
	 * 2. Obtiene los eventos sobreescribibles (reservas, jardinería, entrevistas, franjas) solapados en el horario propuesto.
	 */
	public static List<EventoOcupacion> obtenerSobreescribiblesPartidoSolapados(Connection con,
			int idInstalacion, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin)
			throws SQLException {
		List<EventoOcupacion> todos = obtenerEventosOcupacionPartido(con, idInstalacion, fecha);
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
	 * Guarda el partido escogido eliminando los eventos
	 * de ocupacion coincidentes
	 * de forma atomica
	 * @param con
	 * @param idLocal
	 * @param idVisitante
	 * @param idInstalacion
	 * @param fecha
	 * @param horaInicio
	 * @param horaFin
	 * @param esDiaClub
	 * @param precioAbonado
	 * @param preciosSeccion
	 * @param cancelables
	 * @throws SQLException
	 */
	public static void guardarPartidoCancelandoCoincidentes(Connection con, int idLocal, int idVisitante,
			int idInstalacion, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
			boolean esDiaClub, double precioAbonado,
			Map<TipoSeccion, Double> preciosSeccion,
			List<EventoOcupacion> cancelables) throws SQLException {
		boolean autoCommitOriginal = con.getAutoCommit();
		con.setAutoCommit(false);
		try {
			// A. Eliminar eventos sobreescritos de menor prioridad
			if (cancelables != null) {
				for (EventoOcupacion e : cancelables) {
					if ("RESERVA".equals(e.getOrigen())) {
						try (PreparedStatement pst = con.prepareStatement(DELETE_RESERVA_BY_ID)) {
							pst.setInt(1, e.getIdReferencia());
							pst.executeUpdate();
						}
					} 	else if ("TAREA_MANTENIMIENTO".equals(e.getOrigen())) {
						try (PreparedStatement pst = con.prepareStatement(DELETE_TAREA_MANTENIMIENTO_JARDINEROS)) {
							pst.setInt(1, e.getIdReferencia());
							pst.executeUpdate();
						}
						try (PreparedStatement pst = con.prepareStatement(DELETE_TAREA_MANTENIMIENTO_BY_ID)) {
							pst.setInt(1, e.getIdReferencia());
							pst.executeUpdate();
						}
					} else if ("ENTREVISTA".equals(e.getOrigen())) {
						try (PreparedStatement pst = con.prepareStatement(DELETE_ENTREVISTA_BY_FRANJA)) {
							pst.setInt(1, e.getIdReferencia());
							pst.executeUpdate();
						}
						try (PreparedStatement pst = con.prepareStatement(DELETE_FRANJA_BY_ID)) {
							pst.setInt(1, e.getIdReferencia());
							pst.executeUpdate();
						}
					} else if ("FRANJA".equals(e.getOrigen())) {
						try (PreparedStatement pst = con.prepareStatement(DELETE_FRANJA_BY_ID)) {
							pst.setInt(1, e.getIdReferencia());
							pst.executeUpdate();
						}
					}
				}
			}
			
			// B. Insertar el Partido y recuperar su id generado
			int idPartidoGenerado;
			try (PreparedStatement pst = con.prepareStatement(QUERY_INSERT_PARTIDO, Statement.RETURN_GENERATED_KEYS)) {
				pst.setString(1, fecha.toString());
				pst.setString(2, horaInicio.toString());
				pst.setString(3, horaFin.toString());
				pst.setInt(4, idLocal);
				pst.setInt(5, idVisitante);
				pst.setInt(6, idInstalacion);
				pst.setInt(7, esDiaClub ? 1 : 0);
				if (esDiaClub) {
					pst.setDouble(8, precioAbonado);
				} else {
					pst.setNull(8, Types.REAL);
				}
				int filas = pst.executeUpdate();
				if (filas != 1) {
					throw new SQLException("No se pudo registrar el partido en la base de datos.");
				}
				try (ResultSet rsKeys = pst.getGeneratedKeys()) {
					if (rsKeys.next()) {
						idPartidoGenerado = rsKeys.getInt(1);
					} else {
						throw new SQLException("Error al obtener el ID del partido generado.");
					}
				}
			}
			
			// C. Insertar los precios por sección en PrecioSeccion
			if (preciosSeccion != null && !preciosSeccion.isEmpty()) {
				try (PreparedStatement pstPrecios = con.prepareStatement(QUERY_INSERT_PRECIO_SECCION)) {
					for (Map.Entry<TipoSeccion, Double> entry : preciosSeccion.entrySet()) {
						pstPrecios.setInt(1, idPartidoGenerado);
						pstPrecios.setString(2, entry.getKey().name());
						pstPrecios.setDouble(3, entry.getValue());
						pstPrecios.addBatch();
					}
					pstPrecios.executeBatch();
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
		if (hStr == null || hStr.isBlank()) {
			return LocalTime.MIDNIGHT;
		}
		return LocalTime.parse(hStr,
				DateTimeFormatter.ofPattern(hStr.length() == 5 ? "HH:mm" : "HH:mm:ss"));
	}

}