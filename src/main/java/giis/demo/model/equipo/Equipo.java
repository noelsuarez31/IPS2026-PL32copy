package giis.demo.model.equipo;

public class Equipo {
	public final static int NUM_MIN_JUGADORES = 7;
	public final static int NUM_MIN_ENTRENADORES = 2;

	private int id_equipo;
	private String nombre;
	private String tipoEquipo; //en bbdd: 'Profesional' o 'En formacion'
	private CategoriaEquipo categoriaEquipo; 
	// en bbdd: si es profesional: 'Primer equipo' o 'Filial'
	// si es en formacion: 'Juvenil', ...
	private boolean isPropio;
	
	
	public Equipo(String nombre, String tipo, CategoriaEquipo categoria) {
		this.nombre = nombre;
		this.tipoEquipo = tipo;
		this.categoriaEquipo = categoria;
	}
	
	public void setIsPropio(boolean isPropio) {
		this.isPropio = isPropio;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getTipoEquipo() {
		return this.tipoEquipo;
	}
	
	public boolean getIsPropio() {
		return this.isPropio;
	}
	
	public CategoriaEquipo getCategoriaEquipo() {
		return this.categoriaEquipo;
	}

	public void setIdEquipo(int idGenerado) {
		this.id_equipo = idGenerado;
	}

	public int getIdEquipo() {
		return this.id_equipo;
	}

	@Override
	public String toString() {
		return "Equipo [ " + nombre + ", " + categoriaEquipo + " ]";
	}
	
	
}
