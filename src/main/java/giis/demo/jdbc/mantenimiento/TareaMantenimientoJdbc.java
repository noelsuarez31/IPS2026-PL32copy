package giis.demo.jdbc.mantenimiento;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import giis.demo.model.empleado.EmpleadoNoDeportivo;
import giis.demo.model.empleado.Posicion;
import giis.demo.model.mantenimiento.TareaMantenimiento;

public class TareaMantenimientoJdbc {

	// 1. Obtener empleados que son exclusivamente JARDINEROS
	private static final String QUERY_GET_JARDINEROS = 
			"SELECT be.dni, be.nombre, be.apellido, be.salario, be.fecha_nacimiento, be.numero_de_telefono, end.posicion "
			+ "FROM EmpleadoNoDeportivo end "
			+ "JOIN BaseEmpleado be ON end.dni = be.dni "
			+ "WHERE end.posicion = 'JARDINERIA' "
			+ "ORDER BY be.apellido, be.nombre";

	// 2. Comprobar si un jardinero ya tiene asignada otra tarea que se solape en fecha y hora
	private static final String QUERY_TIENE_TAREA_SOLAPADA = 
			"SELECT COUNT(*) FROM TareaMantenimiento tm "
			+ "JOIN TareaMantenimientoJardinero tmj ON tm.id_tarea = tmj.id_tarea "
			+ "WHERE tmj.dni_jardinero = ? AND tm.fecha = ? "
			+ "AND (? < tm.hora_fin AND ? > tm.hora_inicio)";

	// 3. Inserciones de la tarea y de los jardineros asignados
	private static final String QUERY_INSERT_TAREA = 
			"INSERT INTO TareaMantenimiento (id_instalacion, fecha, hora_inicio, hora_fin, descripcion) "
			+ "VALUES (?, ?, ?, ?, ?)";

	private static final String QUERY_INSERT_JARDINERO_TAREA = 
			"INSERT INTO TareaMantenimientoJardinero (id_tarea, dni_jardinero) VALUES (?, ?)";

	// 4. Obtener las tareas de mantenimiento de una instalación en una fecha determinada
	private static final String QUERY_GET_TAREAS_INSTALACION_FECHA = 
			"SELECT tm.id_tarea, tm.id_instalacion, i.nombre AS nombre_instalacion, tm.fecha, tm.hora_inicio, tm.hora_fin, tm.descripcion "
			+ "FROM TareaMantenimiento tm "
			+ "JOIN Instalacion i ON tm.id_instalacion = i.id_instalacion "
			+ "WHERE tm.id_instalacion = ? AND tm.fecha = ? "
			+ "ORDER BY tm.hora_inicio";

	// 5. Obtener los jardineros asignados a una tarea concreta
	private static final String QUERY_GET_JARDINEROS_DE_TAREA = 
			"SELECT be.dni, be.nombre, be.apellido, be.salario, be.fecha_nacimiento, be.numero_de_telefono, end.posicion "
			+ "FROM TareaMantenimientoJardinero tmj "
			+ "JOIN EmpleadoNoDeportivo end ON tmj.dni_jardinero = end.dni "
			+ "JOIN BaseEmpleado be ON end.dni = be.dni "
			+ "WHERE tmj.id_tarea = ? "
			+ "ORDER BY be.apellido, be.nombre";

	/**
	 * Devuelve todos los empleados con categoría JARDINERIA.
	 */
	public static List<EmpleadoNoDeportivo> getJardineros(Connection con) throws SQLException {
		List<EmpleadoNoDeportivo> jardineros = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(QUERY_GET_JARDINEROS);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				EmpleadoNoDeportivo emp = new EmpleadoNoDeportivo(
						rs.getString("dni"),
						rs.getString("nombre"),
						rs.getString("apellido"),
						new BigDecimal(rs.getString("salario")),
						LocalDate.parse(rs.getString("fecha_nacimiento")),
						rs.getString("numero_de_telefono"),
						Posicion.valueOf(rs.getString("posicion")));
				jardineros.add(emp);
			}
		}
		return jardineros;
	}

	/**
	 * Comprueba si el jardinero tiene ya alguna tarea que solape con la franja horaria indicada en esa fecha.
	 */
	public static boolean tieneTareaSolapada(Connection con, String dniJardinero, String fecha, String horaInicio,
			String horaFin) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(QUERY_TIENE_TAREA_SOLAPADA)) {
			ps.setString(1, dniJardinero);
			ps.setString(2, fecha);
			ps.setString(3, horaInicio);
			ps.setString(4, horaFin);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	/**
	 * Registra de forma transaccional la tarea de mantenimiento y vincula a todos los jardineros asignados.
	 */
	public static void registrarTareaMantenimiento(Connection con, TareaMantenimiento tarea,
			List<String> dnisJardineros) throws SQLException {
		boolean autoCommitOriginal = con.getAutoCommit();
		try {
			con.setAutoCommit(false);

			// 1. Insertamos la tarea y recuperamos la clave generada (id_tarea)
			int idGenerado = 0;
			try (PreparedStatement ps = con.prepareStatement(QUERY_INSERT_TAREA, Statement.RETURN_GENERATED_KEYS)) {
				ps.setInt(1, tarea.getIdInstalacion());
				ps.setString(2, tarea.getFecha());
				ps.setString(3, tarea.getHoraInicio());
				ps.setString(4, tarea.getHoraFin());
				ps.setString(5, tarea.getDescripcion() != null ? tarea.getDescripcion() : "Mantenimiento de jardinería");
				ps.executeUpdate();

				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						idGenerado = rs.getInt(1);
					}
				}
			}

			if (idGenerado == 0) {
				throw new SQLException("No se pudo obtener el identificador de la tarea creada.");
			}

			// 2. Asociamos los jardineros seleccionados a la tarea
			try (PreparedStatement psJardinero = con.prepareStatement(QUERY_INSERT_JARDINERO_TAREA)) {
				for (String dni : dnisJardineros) {
					psJardinero.setInt(1, idGenerado);
					psJardinero.setString(2, dni);
					psJardinero.addBatch();
				}
				psJardinero.executeBatch();
			}

			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			con.setAutoCommit(autoCommitOriginal);
		}
	}

	/**
	 * Devuelve las tareas de mantenimiento de una instalación en una fecha dada,
	 * cargando dentro de cada tarea sus jardineros asignados.
	 */
	public static List<TareaMantenimiento> getTareasInstalacionFecha(Connection con, int idInstalacion, String fecha)
			throws SQLException {
		List<TareaMantenimiento> tareas = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(QUERY_GET_TAREAS_INSTALACION_FECHA)) {
			ps.setInt(1, idInstalacion);
			ps.setString(2, fecha);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					TareaMantenimiento t = new TareaMantenimiento(
							rs.getInt("id_tarea"),
							rs.getInt("id_instalacion"),
							rs.getString("fecha"),
							rs.getString("hora_inicio"),
							rs.getString("hora_fin"),
							rs.getString("descripcion"));
					t.setNombreInstalacion(rs.getString("nombre_instalacion"));
					tareas.add(t);
				}
			}
		}

		// Cargamos los jardineros asignados a cada tarea
		for (TareaMantenimiento t : tareas) {
			t.setJardinerosAsignados(getJardinerosDeTarea(con, t.getIdTarea()));
		}

		return tareas;
	}

	private static List<EmpleadoNoDeportivo> getJardinerosDeTarea(Connection con, int idTarea) throws SQLException {
		List<EmpleadoNoDeportivo> lista = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(QUERY_GET_JARDINEROS_DE_TAREA)) {
			ps.setInt(1, idTarea);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lista.add(new EmpleadoNoDeportivo(
							rs.getString("dni"),
							rs.getString("nombre"),
							rs.getString("apellido"),
							new BigDecimal(rs.getString("salario")),
							LocalDate.parse(rs.getString("fecha_nacimiento")),
							rs.getString("numero_de_telefono"),
							Posicion.valueOf(rs.getString("posicion"))));
				}
			}
		}
		return lista;
	}
}