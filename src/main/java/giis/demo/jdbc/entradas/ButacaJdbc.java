package giis.demo.jdbc.entradas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import giis.demo.model.entradas.Butaca;
import giis.demo.model.entradas.enumerados.TipoSeccion;
import giis.demo.model.entradas.enumerados.TipoTribuna;


public class ButacaJdbc {

	
	private static final String QUERY_FREE_BUTACAS = "SELECT id_butaca, fila, asiento FROM butaca "
			+ "WHERE tribuna = ? AND seccion = ? "
			+ "AND id_butaca NOT IN (SELECT id_butaca FROM entrada WHERE id_partido = ?) "
			+ "AND id_butaca NOT IN (SELECT a.id_butaca FROM Abono a, Partido p WHERE p.id_partido = ? "
			+ "AND p.fecha>= a.fecha_inicio AND p.fecha<=a.fecha_fin AND a.id_equipo = p.id_local) "
			+ "ORDER BY fila, asiento";

	private static final String QUERY_ALL_BUTACAS = "SELECT id_butaca, fila, asiento FROM butaca "
			+ "WHERE tribuna = ? AND seccion = ? "
			+ "ORDER BY fila, asiento";
	
	/**
	 * Obtiene todas las butacas disponibles
	 * @param idPartido
	 * @param tribuna
	 * @param seccion
	 * @return butacas disponibles
	 * @throws SQLException
	 */
	public static List<Butaca> getFreeButacas(Connection con, int idPartido, TipoTribuna tribuna, TipoSeccion seccion) throws SQLException {

		List<Butaca> butacasLibres = new ArrayList<Butaca>();

		try (PreparedStatement ps = con.prepareStatement(QUERY_FREE_BUTACAS)) {

			ps.setString(1, String.valueOf(tribuna));
			ps.setString(2, String.valueOf(seccion));
			ps.setInt(3, idPartido);
			ps.setInt(4, idPartido);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {

					Butaca butaca = new Butaca(rs.getInt("id_butaca"), tribuna, seccion, rs.getInt("fila"),
							rs.getInt("asiento"));

					butacasLibres.add(butaca);
				}
			}

			return butacasLibres;

		}

	}
	
	/**
	 * Obtiene todas las butacas disponibles
	 * @param idPartido
	 * @param tribuna
	 * @param seccion
	 * @return butacas disponibles
	 * @throws SQLException
	 */
	public static List<Butaca> getAllButacas(Connection con, int idPartido, TipoTribuna tribuna, TipoSeccion seccion) throws SQLException {

		List<Butaca> butacasLibres = new ArrayList<Butaca>();

		try (PreparedStatement ps = con.prepareStatement(QUERY_ALL_BUTACAS)) {

			ps.setString(1, String.valueOf(tribuna));
			ps.setString(2, String.valueOf(seccion));

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {

					Butaca butaca = new Butaca(rs.getInt("id_butaca"), tribuna, seccion, rs.getInt("fila"),
							rs.getInt("asiento"));

					butacasLibres.add(butaca);
				}
			}

			return butacasLibres;

		}

	}


}
