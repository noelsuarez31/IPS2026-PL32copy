package giis.demo.jdbc.accion;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import giis.demo.model.accion.Accion;
import giis.demo.model.accion.Accionista;

public class AccionJdbc {

	// 1. Obtener todos los accionistas ordenados por apellidos y nombre
	private static final String QUERY_GET_ACCIONISTAS = 
			"SELECT dni, nombre, apellidos, email FROM Accionista ORDER BY apellidos, nombre";

	// 2. Comprobar si existe un accionista por DNI y obtener sus datos
	private static final String QUERY_GET_ACCIONISTA_BY_DNI = 
			"SELECT dni, nombre, apellidos, email FROM Accionista WHERE UPPER(dni) = UPPER(?)";

	// 3. Obtener el número total de acciones emitidas por el club
	private static final String QUERY_GET_TOTAL_ACCIONES_CLUB = 
			"SELECT COUNT(*) FROM Accion";

	// 4. Obtener las acciones de un propietario ordenadas por fecha de adquisición
	private static final String QUERY_GET_ACCIONES_PROPIETARIO = 
			"SELECT a.id_accion, a.fecha_adquisicion, a.precio_adquisicion, a.en_venta, a.precio_venta, "
			+ "a.origen_tipo, c.nombre AS nombre_campana, c.fase AS fase_campana, a.vendedor_previo "
			+ "FROM Accion a "
			+ "LEFT JOIN Campana c ON a.id_campana = c.id_campana "
			+ "WHERE UPPER(a.dni_propietario) = UPPER(?) "
			+ "ORDER BY a.fecha_adquisicion ASC, a.id_accion ASC";

	/**
	 * Devuelve la lista de todos los accionistas registrados en el club.
	 */
	public static List<Accionista> getAccionistas(Connection con) throws SQLException {
		List<Accionista> accionistas = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(QUERY_GET_ACCIONISTAS);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Accionista a = new Accionista(
						rs.getString("dni"),
						rs.getString("nombre"),
						rs.getString("apellidos"),
						rs.getString("email"));
				accionistas.add(a);
			}
		}
		return accionistas;
	}

	/**
	 * Busca un accionista por su DNI.
	 */
	public static Accionista getAccionistaByDni(Connection con, String dni) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(QUERY_GET_ACCIONISTA_BY_DNI)) {
			ps.setString(1, dni);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Accionista(
							rs.getString("dni"),
							rs.getString("nombre"),
							rs.getString("apellidos"),
							rs.getString("email"));
				}
			}
		}
		return null;
	}

	/**
	 * Devuelve el número total de acciones emitidas por el club.
	 */
	public static int getTotalAccionesClub(Connection con) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(QUERY_GET_TOTAL_ACCIONES_CLUB);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	/**
	 * Devuelve las acciones en propiedad de un accionista ordenadas por fecha de adquisición.
	 */
	public static List<Accion> getAccionesPropietario(Connection con, String dni) throws SQLException {
		List<Accion> acciones = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(QUERY_GET_ACCIONES_PROPIETARIO)) {
			ps.setString(1, dni);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int idAccion = rs.getInt("id_accion");
					String fechaAdquisicion = rs.getString("fecha_adquisicion");
					BigDecimal precioPagado = BigDecimal.valueOf(rs.getDouble("precio_adquisicion"));
					boolean enVenta = rs.getInt("en_venta") == 1;

					BigDecimal precioVenta = null;
					double precioVentaVal = rs.getDouble("precio_venta");
					if (!rs.wasNull()) {
						precioVenta = BigDecimal.valueOf(precioVentaVal);
					}

					// Construcción del texto descriptivo del origen
					String origenTipo = rs.getString("origen_tipo");
					String origenDesc;
					if ("CAMPANA".equalsIgnoreCase(origenTipo)) {
						String nombreCampana = rs.getString("nombre_campana");
						if (nombreCampana != null && !nombreCampana.isBlank()) {
							origenDesc = nombreCampana;
						} else {
							origenDesc = "Campaña";
						}
					} else {
						origenDesc = "Otro accionista";
					}

					Accion dto = new Accion(idAccion, fechaAdquisicion, origenDesc, precioPagado, enVenta, precioVenta);
					acciones.add(dto);
				}
			}
		}
		return acciones;
	}
}
