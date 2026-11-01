package giis.demo.model.accion;

/**
 * Representa a un accionista del club con sus datos identificativos.
 */
public class Accionista {

	private String dni;
	private String nombre;
	private String apellidos;
	private String email;

	public Accionista() {
	}

	public Accionista(String dni, String nombre, String apellidos, String email) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombreCompleto() {
		return nombre + " " + apellidos;
	}

	@Override
	public String toString() {
		return dni + " - " + getNombreCompleto();
	}
}
