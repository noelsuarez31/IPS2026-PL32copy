package giis.demo.model.tienda;

/**
 * Filtros del catalogo de la tienda. Cada filtro se corresponde con un valor
 * de la columna Producto.tipo de la BBDD (TODOS no filtra)
 */
public enum FiltroProducto {
	TODOS("Todos", null),
	PARTE_ARRIBA("Parte de arriba", "ParteArriba"),
	PARTE_ABAJO("Parte de abajo", "ParteAbajo"),
	MERCHANDISING("Merchandising", "Accesorios");

	private final String descripcion;
	private final String tipoBBDD;

	private FiltroProducto(String descripcion, String tipoBBDD) {
		this.descripcion = descripcion;
		this.tipoBBDD = tipoBBDD;
	}

	/**
	 * @return true si el producto debe mostrarse con este filtro
	 */
	public boolean incluye(Producto producto) {
		return tipoBBDD == null || tipoBBDD.equals(producto.getTipo());
	}

	/**
	 * Texto legible del tipo de un producto (ej. "ParteArriba" -> "Parte de arriba")
	 */
	public static String descripcionTipo(String tipoBBDD) {
		for (FiltroProducto filtro : values()) {
			if (filtro.tipoBBDD != null && filtro.tipoBBDD.equals(tipoBBDD)) {
				return filtro.descripcion;
			}
		}
		return tipoBBDD;
	}

	@Override
	public String toString() {
		return descripcion;
	}
}
