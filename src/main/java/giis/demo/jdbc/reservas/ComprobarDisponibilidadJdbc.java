package giis.demo.jdbc.reservas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import giis.demo.model.reserva.Intervalo;

public class ComprobarDisponibilidadJdbc {

	private static final int MARGEN_MINUTOS = 90;
	private static final LocalTime APERTURA = LocalTime.of(8, 0);
	private static final LocalTime CIERRE = LocalTime.of(22, 0);

	private static final String QUERY_ENTRENAMIENTOS = "SELECT hora_inicio, hora_fin, id_equipo "
			+ "FROM Entrenamiento " + "WHERE id_instalacion = ? AND fecha = ?";

	private static final String QUERY_PARTIDOS = "SELECT hora_inicio, hora_fin, id_local, id_visitante  "
			+ "FROM Partido WHERE id_instalacion = ? " + "AND fecha = ?";

	private static final String QUERY_RESERVAS = "SELECT hora_inicio, hora_fin From Reserva "
			+ "WHERE id_instalacion=? AND fecha = ?";
	
	private static final String QUERY_TAREAS_MANTENIMIENTO = "SELECT hora_inicio, hora_fin FROM TareaMantenimiento "
			+ "WHERE id_instalacion = ? AND fecha = ?";

	private static final String QUERY_CHECK_TEAM_IS_LOCAL = "SELECT es_propio FROM Equipo WHERE id_equipo = ?";

	/**
	 * Comprueba si para una fecha y horas dadas la instalación está disponible
	 * 
	 * @param con
	 * @param idInstalacion
	 * @param fecha
	 * @param inicio
	 * @param fin
	 * @return true si está disponible, false en caso contrario
	 * @throws SQLException
	 */
	public static boolean estaDisponible(Connection con, int idInstalacion, LocalDate fecha, LocalTime inicio,
			LocalTime fin) throws SQLException {

		if (inicio == null || fin == null || inicio.isBefore(APERTURA) || !inicio.isBefore(fin) || fin.isAfter(CIERRE)
				|| inicio.plusMinutes(60).isAfter(fin)) {

			return false;

		}

		List<Intervalo> ocupados = obtenerIntervalosOcupados(con, idInstalacion, fecha);

		for (Intervalo intervalo : ocupados) {

			boolean solapamiento = inicio.isBefore(intervalo.getHoraFin()) && fin.isAfter(intervalo.getHoraInicio());

			if (solapamiento) {
				return false;
			}

		}

		return true;
	}

	/**
	 * Obtiene los intervalos disponibles
	 * 
	 * @param con
	 * @param idInstalacion
	 * @param fecha
	 * @return Una lista con los intervalos disponibles
	 * @throws SQLException
	 */
	public static List<Intervalo> obtenerIntervalosDisponibles(Connection con, int idInstalacion, LocalDate fecha)
			throws SQLException {

		List<Intervalo> ocupados = obtenerIntervalosOcupados(con, idInstalacion, fecha);
		ocupados.sort(Comparator.comparing(Intervalo::getHoraInicio));

		List<Intervalo> unidos = new ArrayList<Intervalo>();
		List<Intervalo> disponibles = new ArrayList<Intervalo>();

		for (Intervalo ocupado : ocupados) {

			LocalTime inicio = ocupado.getHoraInicio();
			LocalTime fin = ocupado.getHoraFin();

			if (unidos.isEmpty()) {

				unidos.add(new Intervalo(inicio, fin, "Ocupado"));
				continue;
			}

			Intervalo ultimo = unidos.get(unidos.size() - 1);

			if (!inicio.isAfter(ultimo.getHoraFin())) {

				if (fin.isAfter(ultimo.getHoraFin())) {

					unidos.set(unidos.size() - 1, new Intervalo(ultimo.getHoraInicio(), fin, "Ocupado"));

				}

			} else {
				unidos.add(new Intervalo(inicio, fin, "Ocupado"));
			}

		}

		LocalTime current = APERTURA;

		for (Intervalo intervalo : unidos) {

			if (current.isBefore(intervalo.getHoraInicio())) {

				aniadirDisponible(disponibles, current, intervalo.getHoraInicio());

			}

			if (intervalo.getHoraFin().isAfter(current)) {

				current = intervalo.getHoraFin();
			}

		}

		if (current.isBefore(CIERRE)) {

			aniadirDisponible(disponibles, current, CIERRE);

		}

		return disponibles;
	}

	/**
	 * Obtiene los intervalos en los que una instalación está ocupada para una fecha
	 * 
	 * @param con
	 * @param idInstalacion
	 * @param fecha
	 * @return lista con todos los intervales ocupados
	 * @throws SQLException
	 */
	public static List<Intervalo> obtenerIntervalosOcupados(Connection con, int idInstalacion, LocalDate fecha)
			throws SQLException {

		List<Intervalo> ocupados = new ArrayList<Intervalo>();

		cargarIntervalos(con, QUERY_ENTRENAMIENTOS, idInstalacion, fecha, ocupados, true, false);
		cargarIntervalos(con, QUERY_PARTIDOS, idInstalacion, fecha, ocupados, false, true);
		cargarIntervalos(con, QUERY_RESERVAS, idInstalacion, fecha, ocupados, false, false);
		cargarIntervalosMantenimiento(con, idInstalacion, fecha, ocupados);

		ocupados.sort(Comparator.comparing(Intervalo::getHoraInicio));
		return ocupados;
	}

	private static void aniadirDisponible(List<Intervalo> disponibles, LocalTime inicio, LocalTime fin) {

		if (inicio.plusMinutes(60).isAfter(fin)) {
			return; // No se puede añadir al pasarse de la hora de cierrer
		}

		disponibles.add(new Intervalo(inicio, fin, "Disponible"));

	}

	/**
	 * Metodo para cargar los intervalos y definir los motivos
	 * 
	 * @param con
	 * @param query
	 * @param idInstalacion
	 * @param fecha
	 * @param ocupados
	 * @param isTraining
	 * @param isMatch
	 * @throws SQLException
	 */
	private static void cargarIntervalos(Connection con, String query, int idInstalacion, LocalDate fecha,
			List<Intervalo> ocupados, boolean isTraining, boolean isMatch) throws SQLException {

		try (PreparedStatement ps = con.prepareStatement(query)) {

			ps.setInt(1, idInstalacion);
			ps.setString(2, fecha.toString());

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					LocalTime inicio = LocalTime.parse(rs.getString("hora_inicio"));
					LocalTime fin = LocalTime.parse(rs.getString("hora_fin"));

					boolean equipoLocal = false;
					String motivo;

					if (isTraining) {

						int idEquipo = rs.getInt("id_equipo");
						equipoLocal = isPropio(con, idEquipo);
						motivo = "Entrenamiento";

					} else if (isMatch) {

						int idEquipoLocal = rs.getInt("id_local");
						int idEquipoVisitante = rs.getInt("id_visitante");

						equipoLocal = isPropio(con, idEquipoLocal) || isPropio(con, idEquipoVisitante);

						motivo = "Partido";

					} else {

						motivo = "Reserva";

					}

					LocalTime inicioReal = inicio.isBefore(APERTURA) ? APERTURA : inicio;
					LocalTime finReal = fin.isAfter(CIERRE) ? CIERRE : fin;

					if (inicioReal.isBefore(finReal)) {

						ocupados.add(new Intervalo(inicioReal, finReal, motivo));

					}

					if (equipoLocal && fin.isBefore(CIERRE)) {

						LocalTime inicioMargen = fin.isBefore(APERTURA) ? APERTURA : fin;
						LocalTime finMargen = fin.plusMinutes(MARGEN_MINUTOS);

						if (finMargen.isAfter(CIERRE)) {

							finMargen = CIERRE;

						}

						if (inicioMargen.isBefore(finMargen)) {

							String motivoMargen = isTraining ? "Margen de mantenimiento tras entrenamiento": "Margen de manteniento tras partido";
							ocupados.add(new Intervalo(inicioMargen, finMargen, motivoMargen));

						}

					}

				}

			}

		}

	}
	
	private static void cargarIntervalosMantenimiento(Connection con, int idInstalacion, LocalDate fecha,
			List<Intervalo> ocupados) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(QUERY_TAREAS_MANTENIMIENTO)) {
			ps.setInt(1, idInstalacion);
			ps.setString(2, fecha.toString());
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					LocalTime inicio = LocalTime.parse(rs.getString("hora_inicio"));
					LocalTime fin = LocalTime.parse(rs.getString("hora_fin"));
					LocalTime inicioReal = inicio.isBefore(APERTURA) ? APERTURA : inicio;
					LocalTime finReal = fin.isAfter(CIERRE) ? CIERRE : fin;
					if (inicioReal.isBefore(finReal)) {
						ocupados.add(new Intervalo(inicioReal, finReal, "Mantenimiento"));
					}
				}
			}
		}
	}

	/**
	 * Metodo auxiliar para comprobar si un equipo es propio
	 * 
	 * @param con
	 * @param idEquipo
	 * @return true si el equipo es propio, false en caso contrario
	 * @throws SQLException
	 */
	private static boolean isPropio(Connection con, int idEquipo) throws SQLException {

		try (PreparedStatement ps = con.prepareStatement(QUERY_CHECK_TEAM_IS_LOCAL)) {

			ps.setInt(1, idEquipo);

			try (ResultSet rs = ps.executeQuery()) {

				return rs.next() && rs.getInt("es_propio") == 1;
			}
		}
	}

}
