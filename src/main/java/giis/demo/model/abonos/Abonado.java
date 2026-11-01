package giis.demo.model.abonos;

import java.time.LocalDate;

public class Abonado {
	
	private int id_abonado;
	private String dni;
	private String nombre;
	private String apellidos;
	private LocalDate fechaNacimiento;
	private String email;

	public Abonado(String dni, String nombre, String apellidos, LocalDate fechaNacimiento, String email) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}
	
	public int getIdAbonado() {
		
		return id_abonado;
	}

	public void setIdAbonado(int idAbonado) {

		this.id_abonado = idAbonado;
	}
	
}
