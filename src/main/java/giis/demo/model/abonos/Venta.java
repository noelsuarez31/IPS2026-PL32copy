package giis.demo.model.abonos;

import giis.demo.model.entradas.enumerados.TipoSeccion;
import giis.demo.model.entradas.enumerados.TipoTribuna;

public abstract class Venta {


	/**
	 * Enums de tribuna
	 * 
	 * @return los distintos valores que puede tomar la tribuna
	 */
	public TipoTribuna[] getTipoTribuna() {

		return TipoTribuna.values();
	}

	/**
	 * Enums de seccion
	 * 
	 * @return los distintos valores que puede tomar la seccion
	 */
	public TipoSeccion[] getTipoSeccion() {

		return TipoSeccion.values();
	}
	
}
