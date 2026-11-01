package giis.demo.jdbc.entradas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrecioSeccionJdbc {

	private static final String QUERY_PRECIO = "SELECT precio FROM PrecioSeccion WHERE id_partido = ? AND seccion = ?";

	/**
	 * Obtiene el precio de un asiento para un partido y una seccion dadas
	 * 
	 * @return los partido disponibles
	 * @throws SQLException
	 */
	public static double getPrecio(Connection con, int idPartido, String seccion) throws SQLException {

		try (PreparedStatement ps = con.prepareStatement(QUERY_PRECIO)) {

			ps.setInt(1, idPartido);
			ps.setString(2, seccion);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					return rs.getDouble("precio");
				}
			}
		}

		throw new SQLException("No hay precio definido para el partido " + idPartido + " y la sección " + seccion);

	}
}
