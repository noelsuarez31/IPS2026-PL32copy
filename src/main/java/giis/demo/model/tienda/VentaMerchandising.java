package giis.demo.model.tienda;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import giis.demo.jdbc.JDBC;

public class VentaMerchandising {
	private Cesta cesta;
	private List<Producto> catalogo;
	private JDBC jdbc;

	public VentaMerchandising() {
		this.cesta = new Cesta();
		this.catalogo = new ArrayList<>();
		this.jdbc = new JDBC();
	}

	public void cargarCatalogo() throws SQLException {
		this.catalogo = jdbc.getProductosTienda();
	}

	public void almacenarVenta() throws SQLException {
		jdbc.almacenarVentaMerchandising(cesta.getTotal(), cesta.getLineas());
	}
	
	public void close() throws SQLException {
		jdbc.close();
	}

	public List<Producto> getCatalogo() {
		return catalogo;
	}
	
	/**
	 * Devuelve los productos del catalogo que cumplen el filtro
	 */
	public List<Producto> getCatalogo(FiltroProducto filtro) {
		List<Producto> filtrados = new ArrayList<>();
		for (Producto p : catalogo) {
			if (filtro.incluye(p)) {
				filtrados.add(p);
			}
		}
		return filtrados;
	}

	public Cesta getCesta() {
		return cesta;
	}
	
	public Producto getProductoPorId(int id) {
		for (Producto p : catalogo) {
			if (p.getId_producto() == id) return p;
		}
		return null;
	}
}
