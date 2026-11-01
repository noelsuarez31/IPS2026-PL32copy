package giis.demo.jdbc.entradas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import giis.demo.model.entradas.Partido;

public class PartidoJdbc {

	private static final String QUERY_MATCH = "select id_partido, fecha, hora_inicio, hora_fin, "
			+ "id_local, id_visitante, id_instalacion, dia_del_club, precio_abonado, "
			+ "eq1.nombre as nombre_local, eq2.nombre as nombre_visitante from Partido p, Equipo eq1, "
			+ "Equipo eq2 WHERE p.id_local = eq1.id_equipo AND p.id_visitante = eq2.id_equipo "
			+ "AND p.fecha >= ? ORDER BY p.fecha";

	
	/**
	 * Obtiene todos los partidos disponibles
	 * 
	 * @return los partido disponibles
	 * @throws SQLException
	 */
	public static List<Partido> getPartidos(Connection con) throws SQLException {

		List<Partido> partidos = new ArrayList<Partido>();

		try (PreparedStatement ps = con.prepareStatement(QUERY_MATCH)) {

			ps.setString(1, LocalDate.now().toString());// Para mostrar partidos que
			// Aun no se haya jugado

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					Partido partido = new Partido(
							rs.getInt("id_partido"), LocalDate.parse(rs.getString("fecha")),
							LocalTime.parse(rs.getString("hora_inicio")), LocalTime.parse(rs.getString("hora_fin")),
							rs.getInt("id_local"), rs.getInt("id_visitante"), rs.getString("nombre_local"),
							rs.getString("nombre_visitante"), rs.getInt("id_instalacion"),
							rs.getInt("dia_del_club") == 1, rs.getDouble("precio_abonado"));

					partidos.add(partido);

				}

			}

			return partidos;

		}

	}

}
