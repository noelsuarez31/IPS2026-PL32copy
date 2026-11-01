package giis.demo.model.accion;

import java.math.BigDecimal;

/**
 * Objeto de transferencia de datos (DTO) para la visualización tabular de las
 * acciones de un accionista en el portal.
 */
public class Accion {

	private int idAccion;
	private String fechaAdquisicion;
	private String origen;
	private BigDecimal precioPagado;
	private boolean enVenta;
	private BigDecimal precioVenta;

	public Accion() {
	}

	public Accion(int idAccion, String fechaAdquisicion, String origen, BigDecimal precioPagado, boolean enVenta,
			BigDecimal precioVenta) {
		this.idAccion = idAccion;
		this.fechaAdquisicion = fechaAdquisicion;
		this.origen = origen;
		this.precioPagado = precioPagado;
		this.enVenta = enVenta;
		this.precioVenta = precioVenta;
	}

	public int getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(int idAccion) {
		this.idAccion = idAccion;
	}

	public String getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	public void setFechaAdquisicion(String fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public BigDecimal getPrecioPagado() {
		return precioPagado;
	}

	public void setPrecioPagado(BigDecimal precioPagado) {
		this.precioPagado = precioPagado;
	}

	public boolean isEnVenta() {
		return enVenta;
	}

	public void setEnVenta(boolean enVenta) {
		this.enVenta = enVenta;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}
}
