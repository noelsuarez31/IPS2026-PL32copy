package giis.demo.jdbc.abonados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import giis.demo.exceptions.AbonoNoPermitidoException;
import giis.demo.model.abonos.Abonado;
import giis.demo.model.abonos.Abono;
import giis.demo.model.abonos.Abono.Categoria;

public class AbonoJdbc {

	public final static String QUERY_ASIENTOS_OCUPADOS = "SELECT b.fila, b.asiento "
	        + "FROM Abono a JOIN Butaca b ON b.id_butaca = a.id_butaca "
	        + "WHERE b.tribuna = ? AND b.seccion = ? "
	        + "AND a.fecha_inicio <= ? AND a.fecha_fin >= ? "
	        + "UNION "
	        + "SELECT b.fila, b.asiento "
	        + "FROM Entrada e JOIN Partido p ON p.id_partido = e.id_partido "
	        + "JOIN Butaca b ON b.id_butaca = e.id_butaca "
	        + "WHERE p.id_local = ? AND b.tribuna = ? AND b.seccion = ? "
	        + "AND p.fecha >= ? AND p.fecha <= ?";

	public final static String QUERY_INFO_ABONO = "SELECT e.nombre, b.tribuna, b.seccion, b.fila, b.asiento, "
			+ "a.fecha_inicio, a.fecha_fin, a.fecha_registro, a.categoria, a.precio " + "FROM Abono a "
			+ "JOIN Abonado ab ON ab.id_abonado = a.id_abonado " + "JOIN Butaca b ON b.id_butaca = a.id_butaca "
			+ "JOIN Equipo e ON e.id_equipo = a.id_equipo " + "WHERE ab.dni = ? AND a.fecha_inicio = ?";

	public final static String QUERY_ES_PROPIO = "SELECT es_propio FROM Equipo WHERE id_equipo=?";
	
	public final static String QUERY_GET_ID_BUTACA = "SELECT id_butaca FROM Butaca WHERE tribuna=? AND seccion=? "
			+ "AND fila = ? AND asiento=?";
	
	public final static String QUERY_BUTACA_NO_DISPONIBLE = "SELECT 1 FROM Abono a WHERE a.id_butaca = ? "
		    + "AND a.fecha_inicio <= ? AND a.fecha_fin >= ? "
		    + "UNION "
		    + "SELECT 1 FROM Entrada e JOIN Partido p ON p.id_partido = e.id_partido "
		    + "WHERE p.id_local = ? AND e.id_butaca = ? "
		    + "AND p.fecha >= ? AND p.fecha <= ?";
	
	public final static String QUERY_INSERT_VENTA = "INSERT INTO Venta (fecha, concepto, tipo, total) VALUES "
			+ "(?,?,'ABONOS',?)";
	
	public final static String QUERY_INSERT_ABONO = "INSERT INTO Abono (id_venta, id_abonado, id_equipo, id_butaca,"
			+ "fecha_inicio, fecha_fin, fecha_registro, categoria, precio) VALUES (?,?,?,?,?,?,?,?,?)";


	public static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	/**
	 * Obtiene los asientos que están ocupados dados un equipo, tribuna, seccion y fecha
	 * 
	 * @param con
	 * @param idEquipo
	 * @param tribuna
	 * @param seccion
	 * @param inicio
	 * @param fin
	 * @return una lista con los asientos ocupados
	 * @throws SQLException
	 */
	public static List<int[]> getAsientosOcupados(Connection con, int idEquipo, String tribuna, String seccion,LocalDate inicio, LocalDate fin) throws SQLException {

		List<int[]> asientosOcupados = new ArrayList<>();

		try (PreparedStatement pst = con.prepareStatement(QUERY_ASIENTOS_OCUPADOS)) {

			LocalDate hoy = LocalDate.now();
			
			pst.setString(1, tribuna);
			pst.setString(2, seccion);
			pst.setString(3, fin.toString());
			pst.setString(4, inicio.toString());


			pst.setInt(5, idEquipo);
			pst.setString(6, tribuna);
			pst.setString(7, seccion);
			pst.setString(8, hoy.toString());
			pst.setString(9, fin.toString());

			try (ResultSet rs = pst.executeQuery()) {

				while (rs.next()) {

					int fila = rs.getInt("fila");
					int asiento = rs.getInt("asiento");

					asientosOcupados.add(new int[] { fila, asiento });
				}
			}
		}

		return asientosOcupados;
	}

	/**
	 * Obtiene la información del abono
	 * @param con
	 * @param dni
	 * @param fechaInicio
	 * @return El abono con la información
	 * @throws SQLException
	 */
	public static Abono getInfoAbono(Connection con, String dni, LocalDate fechaInicio) throws SQLException {

		try (PreparedStatement pst = con.prepareStatement(QUERY_INFO_ABONO)) {

			pst.setString(1, dni);
			pst.setString(2, fechaInicio.toString());

			try (ResultSet rs = pst.executeQuery()) {

				if (rs.next()) {

					return new Abono(rs.getString("nombre"), rs.getString("tribuna"), rs.getString("seccion"),
							rs.getInt("fila"), rs.getInt("asiento"), LocalDate.parse(rs.getString("fecha_inicio")),
							LocalDate.parse(rs.getString("fecha_fin")),
							LocalDateTime.parse(rs.getString("fecha_registro"), FORMATO_FECHA_HORA),
							Categoria.valueOf(rs.getString("categoria")), rs.getDouble("precio"));

				}
			}
		}

		return null;

	}

	
	public static void venderAbono(Connection con, Abonado abonado, Abono abono,int idEquipo) throws SQLException, AbonoNoPermitidoException {

		boolean autoCommitOriginal = con.getAutoCommit();

		try {
			con.setAutoCommit(false);

			if (!esEquipoPropio(con, idEquipo)) {
				
				throw new AbonoNoPermitidoException("El equipo seleccionado no es un equipo local.");
			}
			
			if (AbonadoJdbc.tieneAbonoTemporada(con, abonado.getDni(), abono.getFechaIncio())) {
				
				throw new AbonoNoPermitidoException("El DNI " + abonado.getDni() + " ya tiene un abono para esta temporada.");
			}
			
			int idButaca = getIdButaca(con, abono.getTribuna(), abono.getSeccion(), abono.getFila(), abono.getAsiento());

			if (butacaNoDisponible(con, idEquipo, idButaca, abono.getFechaIncio(), abono.getFechaFin())) {
				
				throw new AbonoNoPermitidoException("El asiento seleccionado ya no está disponible.");
			}

			int idVenta = insertarVenta(con, abono.getFechaRegistro(), "Abono " + abono.getNombreEquipo(), abono.getPrecio());

			int idAbonado = AbonadoJdbc.getIdAbonado(con, abonado.getDni());
			
			if (idAbonado == -1) {
				
				AbonadoJdbc.insertarAbonado(con, abonado);
				idAbonado = abonado.getIdAbonado();
			}

			try (PreparedStatement pst = con.prepareStatement(QUERY_INSERT_ABONO)) {
				
				pst.setInt(1, idVenta);
				pst.setInt(2, idAbonado);
				pst.setInt(3, idEquipo);
				pst.setInt(4, idButaca);
				
				pst.setString(5, abono.getFechaIncio().toString());
				pst.setString(6, abono.getFechaFin().toString());
				pst.setString(7, abono.getFechaRegistro().format(FORMATO_FECHA_HORA));
				pst.setString(8, abono.getCategoria().toString());
				pst.setDouble(9, abono.getPrecio());
				pst.executeUpdate();
			}

			con.commit();

		} catch (SQLException | AbonoNoPermitidoException e) {

			try {
				con.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}

			throw e;

		} finally {
			try {
				con.setAutoCommit(autoCommitOriginal);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Coomprueba que un equipo es propio
	 * 
	 * @param con
	 * @param idEquipo
	 * @return true si el equipo es propio, false en caso contrario
	 * @throws SQLException
	 */
	private static boolean esEquipoPropio(Connection con, int idEquipo) throws SQLException {

		try (PreparedStatement pst = con.prepareStatement(QUERY_ES_PROPIO)) {

			pst.setInt(1, idEquipo);

			try (ResultSet rs = pst.executeQuery()) {

				return rs.next() && rs.getInt(1) == 1;

			}
		}
	}

	/**
	 * Dado una tribua, sección, fila y asiento devuelve el id de la butaca
	 * 
	 * @param con
	 * @param tribuna
	 * @param seccion
	 * @param fila
	 * @param asiento
	 * @return el de la butaca
	 * @throws SQLException
	 */
	private static int getIdButaca(Connection con, String tribuna, String seccion, int fila, int asiento) throws SQLException {

		try (PreparedStatement pst = con.prepareStatement(QUERY_GET_ID_BUTACA)) {

			pst.setString(1, tribuna);
			pst.setString(2, seccion);
			pst.setInt(3, fila);
			pst.setInt(4, asiento);

			try (ResultSet rs = pst.executeQuery()) {

				if (rs.next()) {

					return rs.getInt(1);
				}
			}
		}

		throw new SQLException("No existe la butaca " + tribuna + seccion + " fila " + fila + " asiento " + asiento);
	}

	/**
	 * Comprueba si la butaca está disponible
	 * @param con
	 * @param idEquipo
	 * @param idButaca
	 * @param hoy
	 * @return true si la butaca esta disponiblr, false en caso contrario
	 * @throws SQLException
	 */
	private static boolean butacaNoDisponible(Connection con, int idEquipo, int idButaca, LocalDate inicio, LocalDate fin) throws SQLException {
		
		try (PreparedStatement pst = con.prepareStatement(QUERY_BUTACA_NO_DISPONIBLE)) {
			
			LocalDate hoy = LocalDate.now();
			
			pst.setInt(1, idButaca);
			pst.setString(2, fin.toString());
			pst.setString(3, inicio.toString());
			
			pst.setInt(4, idEquipo);
			pst.setInt(5, idButaca);
			pst.setString(6, hoy.toString());
			pst.setString(7, fin.toString());

			try (ResultSet rs = pst.executeQuery()) {
				
				return rs.next();
				
			}
		}
	}

	/**
	 * Insertar la compra del abono en las ventas
	 * @param con
	 * @param fecha
	 * @param concepto
	 * @param total
	 * @return devuelve el id de la venta
	 * @throws SQLException
	 */
	private static int insertarVenta(Connection con, LocalDateTime fecha, String concepto, double total)
			throws SQLException {
		try (PreparedStatement pst = con.prepareStatement(QUERY_INSERT_VENTA, Statement.RETURN_GENERATED_KEYS)) {
			
			pst.setString(1, fecha.format(FORMATO_FECHA_HORA));
			pst.setString(2, concepto);
			pst.setDouble(3, total);
			pst.executeUpdate();

			try (ResultSet keys = pst.getGeneratedKeys()) {
				if (keys.next()) {
					return keys.getInt(1);
				}
			}
		}
		throw new SQLException("No se pudo obtener el id de la venta");
	}

}
