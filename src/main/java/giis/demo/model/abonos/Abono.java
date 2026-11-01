package giis.demo.model.abonos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Abono {

	public enum Categoria {
		INFANTIL, SUB_26, ADULTO, JUBILADO
	}
	
	private String nombreEquipo;
	private String tribuna;
	private String seccion;
	
	private int fila;
	private int asiento;
	
	private LocalDate fechaIncio;
	private LocalDate fechaFin;
	private LocalDateTime fechaRegistro;
	
	private Categoria categoria;
	private double precio;
	
	/**
	 * Constructor por defecto de la clase
	 * 
	 * @param nombreEquipo
	 * @param tribuna
	 * @param seccion
	 * @param fila
	 * @param asiento
	 * @param fechaIncio
	 * @param fechaFin
	 * @param fechaRegistro
	 * @param categoria
	 * @param precio
	 */
	public Abono(String nombreEquipo, String tribuna, String seccion, int fila, int asiento, LocalDate fechaIncio,
			LocalDate fechaFin, LocalDateTime fechaRegistro, Categoria categoria, double precio) {
	
		this.nombreEquipo = nombreEquipo;
		this.tribuna = tribuna;
		this.seccion = seccion;
		
		this.fila = fila;
		this.asiento = asiento;
		
		this.fechaIncio = fechaIncio;
		this.fechaFin = fechaFin;
		this.fechaRegistro = fechaRegistro;
		
		this.categoria = categoria;
		this.precio = precio;
	}

	/**
	 * @return the nombreEquipo
	 */
	public String getNombreEquipo() {
		return nombreEquipo;
	}

	/**
	 * @return the tribuna
	 */
	public String getTribuna() {
		return tribuna;
	}

	/**
	 * @return the seccion
	 */
	public String getSeccion() {
		return seccion;
	}

	/**
	 * @return the fila
	 */
	public int getFila() {
		return fila;
	}

	/**
	 * @return the asiento
	 */
	public int getAsiento() {
		return asiento;
	}

	/**
	 * @return the fechaIncio
	 */
	public LocalDate getFechaIncio() {
		return fechaIncio;
	}

	/**
	 * @return the fechaFin
	 */
	public LocalDate getFechaFin() {
		return fechaFin;
	}

	/**
	 * @return the fechaRegistro
	 */
	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @return the categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * @return the precio
	 */
	public double getPrecio() {
		return precio;
	}

	@Override
	public String toString() {
		
		return "Abono [nombreEquipo=" + nombreEquipo + ", tribuna=" + tribuna + ", seccion=" + seccion + ", fila="
				+ fila + ", asiento=" + asiento + ", fechaIncio=" + fechaIncio + ", fechaFin=" + fechaFin
				+ ", fechaRegistro=" + fechaRegistro + ", categoria=" + categoria + ", precio=" + precio + "]";
	}
	
	

}
