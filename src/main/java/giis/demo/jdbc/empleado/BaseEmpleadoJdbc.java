package giis.demo.jdbc.empleado;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import giis.demo.model.empleado.BaseEmpleado;
import giis.demo.model.empleado.EmpleadoDeportivo;
import giis.demo.model.empleado.EmpleadoNoDeportivo;
import giis.demo.model.empleado.Posicion;

public class BaseEmpleadoJdbc {
	private static final String QUERY_INSERT_BASE = "INSERT INTO BaseEmpleado "
			+ "(dni, nombre, apellido, salario, fecha_nacimiento, numero_de_telefono) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String QUERY_EXISTE_DNI = "SELECT 1 FROM BaseEmpleado WHERE dni = ?";
	private static final String QUERY_EXISTE_TELEFONO = "SELECT 1 FROM BaseEmpleado WHERE numero_de_telefono = ?";
	private static final String QUERY_EXISTE_TELEFONO_OTRO = "SELECT 1 FROM BaseEmpleado WHERE numero_de_telefono = ? AND dni <> ?";
	private static final String QUERY_GET_EMPLEADOS = "SELECT be.dni, be.nombre, be.apellido, be.salario, "
			+ "be.fecha_nacimiento, be.numero_de_telefono, ed.posicion AS posicion_deportiva, ed.id_equipo, "
			+ "nd.posicion AS posicion_no_deportiva FROM BaseEmpleado be "
			+ "LEFT JOIN EmpleadoDeportivo ed ON ed.dni = be.dni "
			+ "LEFT JOIN EmpleadoNoDeportivo nd ON nd.dni = be.dni "
			+ "ORDER BY be.apellido, be.nombre";

	private static final String QUERY_UPDATE_BASE = "UPDATE BaseEmpleado SET dni = ?, nombre = ?, apellido = ?, "
			+ "salario = ?, fecha_nacimiento = ?, numero_de_telefono = ? WHERE dni = ?";
	private static final String[] QUERIES_UPDATE_DNI_DEPENDIENTES = {
			"UPDATE EmpleadoDeportivo SET dni = ? WHERE dni = ?",
			"UPDATE EmpleadoNoDeportivo SET dni = ? WHERE dni = ?",
			"UPDATE HorarioPeriodico SET dni_empleado = ? WHERE dni_empleado = ?",
			"UPDATE HorarioEspecifico SET dni_empleado = ? WHERE dni_empleado = ?",
			"UPDATE FranjaHoraria SET dni_jugador = ? WHERE dni_jugador = ?" ,
			"UPDATE TareaMantenimientoJardinero SET dni_jardinero = ? WHERE dni_jardinero = ?"};
	private static final String[] QUERIES_DELETE_EMPLEADO = {
			"DELETE FROM Entrevista WHERE id_franja IN (SELECT id_franja FROM FranjaHoraria WHERE dni_jugador = ?)",
			"DELETE FROM FranjaHoraria WHERE dni_jugador = ?",
			"DELETE FROM HorarioPeriodico WHERE dni_empleado = ?",
			"DELETE FROM HorarioEspecifico WHERE dni_empleado = ?",
			"DELETE FROM EmpleadoDeportivo WHERE dni = ?",
			"DELETE FROM EmpleadoNoDeportivo WHERE dni = ?",
			"DELETE FROM BaseEmpleado WHERE dni = ?" ,
			"DELETE FROM TareaMantenimientoJardinero WHERE dni_jardinero = ?"};

	/**
	 * Inserta los datos comunes de un empleado (deportivo o no deportivo)
	 * @param con
	 * @param emp
	 * @throws SQLException
	 */
	public static void insertarBaseEmpleado(Connection con, BaseEmpleado emp) throws SQLException {
		try (PreparedStatement pst = con.prepareStatement(QUERY_INSERT_BASE)) {
			pst.setString(1, emp.getDni());
			pst.setString(2, emp.getNombre());
			pst.setString(3, emp.getApellido());
			pst.setBigDecimal(4, emp.getSalario());
			pst.setString(5, emp.getFechaNacimiento().toString()); // yyyy-MM-dd, igual que en el resto de la BBDD
			pst.setString(6, emp.getNumeroTelefono());
			pst.executeUpdate();
		}
	}

	/**
	 * @return true si ya existe un empleado con ese dni
	 */
	public static boolean existeDni(Connection con, String dni) throws SQLException {
		return existe(con, QUERY_EXISTE_DNI, dni);
	}

	/**
	 * @return true si ya existe un empleado con ese numero de telefono
	 */
	public static boolean existeTelefono(Connection con, String telefono) throws SQLException {
		return existe(con, QUERY_EXISTE_TELEFONO, telefono);
	}

	private static boolean existe(Connection con, String query, String valor) throws SQLException {
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setString(1, valor);
			try (ResultSet rs = pst.executeQuery()) {
				return rs.next();
			}
		}
	}
	
	/**
	 * @return true si otro empleado (con dni distinto a dniPropio) ya tiene ese telefono
	 */
	public static boolean existeTelefonoEnOtroEmpleado(Connection con, String telefono, String dniPropio)
			throws SQLException {
		try (PreparedStatement pst = con.prepareStatement(QUERY_EXISTE_TELEFONO_OTRO)) {
			pst.setString(1, telefono);
			pst.setString(2, dniPropio);
			try (ResultSet rs = pst.executeQuery()) {
				return rs.next();
			}
		}
	}

	/**
	 * Devuelve todos los empleados (deportivos y no deportivos) ordenados por apellido
	 */
	public static List<BaseEmpleado> getEmpleados(Connection con) throws SQLException {
		List<BaseEmpleado> empleados = new ArrayList<>();

		try (PreparedStatement pst = con.prepareStatement(QUERY_GET_EMPLEADOS);
				ResultSet rs = pst.executeQuery()) {
			while (rs.next()) {
				String dni = rs.getString("dni");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				BigDecimal salario = new BigDecimal(rs.getString("salario"));
				LocalDate fechaNacimiento = LocalDate.parse(rs.getString("fecha_nacimiento"));
				String telefono = rs.getString("numero_de_telefono");

				String posicionDeportiva = rs.getString("posicion_deportiva");
				String posicionNoDeportiva = rs.getString("posicion_no_deportiva");

				if (posicionDeportiva != null) {
					EmpleadoDeportivo emp = new EmpleadoDeportivo(dni, nombre, apellido, salario, fechaNacimiento,
							telefono, Posicion.valueOf(posicionDeportiva));
					int idEquipo = rs.getInt("id_equipo");
					if (!rs.wasNull()) {
						emp.setIdEquipo(idEquipo);
					}
					empleados.add(emp);
				} else if (posicionNoDeportiva != null) {
					empleados.add(new EmpleadoNoDeportivo(dni, nombre, apellido, salario, fechaNacimiento, telefono,
							Posicion.valueOf(posicionNoDeportiva)));
				}
				// Si no esta en ninguna de las dos tablas es un registro incompleto y se ignora
			}
		}
		return empleados;
	}

	/**
	 * Actualiza los datos personales y el salario del empleado identificado por dniOriginal.
	 * Si el dni ha cambiado, lo propaga a las tablas que lo referencian.
	 * La posicion no se modifica. Debe llamarse dentro de una transaccion
	 */
	public static void actualizarEmpleado(Connection con, String dniOriginal, BaseEmpleado emp) throws SQLException {
		try (PreparedStatement pst = con.prepareStatement(QUERY_UPDATE_BASE)) {
			pst.setString(1, emp.getDni());
			pst.setString(2, emp.getNombre());
			pst.setString(3, emp.getApellido());
			pst.setBigDecimal(4, emp.getSalario());
			pst.setString(5, emp.getFechaNacimiento().toString());
			pst.setString(6, emp.getNumeroTelefono());
			pst.setString(7, dniOriginal);
			if (pst.executeUpdate() != 1) {
				throw new SQLException("No existe ningun empleado con DNI " + dniOriginal);
			}
		}

		if (!dniOriginal.equals(emp.getDni())) {
			for (String query : QUERIES_UPDATE_DNI_DEPENDIENTES) {
				try (PreparedStatement pst = con.prepareStatement(query)) {
					pst.setString(1, emp.getDni());
					pst.setString(2, dniOriginal);
					pst.executeUpdate();
				}
			}
		}
	}

	/**
	 * Elimina al empleado y todo lo que depende de el (horarios, franjas y entrevistas).
	 * Debe llamarse dentro de una transaccion
	 */
	public static void eliminarEmpleado(Connection con, String dni) throws SQLException {
		for (String query : QUERIES_DELETE_EMPLEADO) {
			try (PreparedStatement pst = con.prepareStatement(query)) {
				pst.setString(1, dni);
				pst.executeUpdate();
			}
		}
	}
}
