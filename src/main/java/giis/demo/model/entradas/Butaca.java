package giis.demo.model.entradas;

import java.util.Objects;

import giis.demo.model.entradas.enumerados.TipoSeccion;
import giis.demo.model.entradas.enumerados.TipoTribuna;

public class Butaca {

	private int id_butaca;
	private TipoTribuna tribuna;
	private TipoSeccion seccion;
	private int fila;
	private int asiento;
	
	/**
	 * Constructor de la clase Butaca por defecto
	 * @param idbutaca
	 * @param tribuna
	 * @param seccion
	 * @param fila
	 * @param asiento
	 */
	public Butaca(int idbutaca, TipoTribuna tribuna, TipoSeccion seccion, int fila, int asiento) {
		
		this.id_butaca = idbutaca;
		this.tribuna = tribuna;
		this.seccion = seccion;
		this.fila = fila;
		this.asiento = asiento;
	}

	/**
	 * Devuelve el id de la butaca
	 * @return id de la butaca
	 */
	public int getId_butaca() {
		return id_butaca;
	}

	/**
	 * Devuelve el tipo de la Tribuna
	 * @return la tribuna seleccionada
	 */
	public TipoTribuna getTribuna() {
		return tribuna;
	}

	/**
	 * Devuelve el tipo de la sección
	 * @return la sección seleccionada
	 */
	public TipoSeccion getSeccion() {
		return seccion;
	}

	/**
	 * Devuelve la fila del asiento
	 * @return la fila del asiento
	 */
	public int getFila() {
		return fila;
	}
	
	/**
	 * Devuelve el asiento
	 * @return el asiento
	 */
	public int getAsiento() {
		return asiento;
	}	
	
	@Override
	public int hashCode() {
		
	    return Objects.hash(asiento, fila, seccion, tribuna);

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Butaca other = (Butaca) obj;
		return asiento == other.asiento && fila == other.fila
				&& seccion == other.seccion && tribuna == other.tribuna;
	}
}
