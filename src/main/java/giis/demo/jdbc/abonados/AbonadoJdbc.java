package giis.demo.jdbc.abonados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import giis.demo.model.abonos.Abonado;

public class AbonadoJdbc {

	public final static String QUERY_TIENE_ABONO = "SELECT * FROM Abono ab, Abonado a WHERE ab.id_abonado = a.id_abonado"
			+ " AND a.dni = ? AND fecha_inicio = ?";

	public final static String QUERY_GET_ID_ABONADO = "SELECT id_abonado FROM Abonado WHERE dni = ?";

	public final static String QUERY_INSERT_ABONADO = "INSERT INTO Abonado (dni, nombre, apellidos, fecha_nacimiento, email) VALUES (?,?,?,?,?)";

	/**
	 * Consulta a la BBDD si un cliente tiene ya un abono de temporada
	 * 
	 * @param con
	 * @param dni
	 * @param fechaDeInicio
	 * @return true si ya lo tiene false en caso contrario
	 * @throws SQLException
	 */
	public static boolean tieneAbonoTemporada(Connection con, String dni, LocalDate fechaDeInicio) throws SQLException {

		try (PreparedStatement pst = con.prepareStatement(QUERY_TIENE_ABONO)) {

			pst.setString(1, dni);
			pst.setString(2, fechaDeInicio.toString());

			try (ResultSet rs = pst.executeQuery()) {

				return rs.next();
			}

		}

	}

	/**
	 * Se inserta en la base de datos el abonado
	 * @param con
	 * @param abonado
	 * @throws SQLException
	 */
	public static void insertarAbonado(Connection con, Abonado abonado) throws SQLException {

		try (PreparedStatement pst = con.prepareStatement(QUERY_INSERT_ABONADO, Statement.RETURN_GENERATED_KEYS)) {

			pst.setString(1, abonado.getDni());
			pst.setString(2, abonado.getNombre());
			pst.setString(3, abonado.getApellidos());
			pst.setString(4, abonado.getFechaNacimiento().toString());
			pst.setString(5, abonado.getEmail());

			pst.executeUpdate();

			try (ResultSet keys = pst.getGeneratedKeys()) {

				if (keys.next()) {

					abonado.setIdAbonado(keys.getInt(1));
					return;
				}
			}

		}

		throw new SQLException("No se puedo obtener el id del abonado");
	}

	/**
	 * Si existe el abonado devuelve el id del mismo
	 * @param con
	 * @param dni
	 * @return el identificador del abonado
	 * @throws SQLException
	 */
	public static int getIdAbonado(Connection con, String dni) throws SQLException {

		try (PreparedStatement pst = con.prepareStatement(QUERY_GET_ID_ABONADO)) {

			pst.setString(1, dni);

			try (ResultSet rs = pst.executeQuery()) {

				return rs.next() ? rs.getInt(1) : -1;

			}
		}
	}

}
