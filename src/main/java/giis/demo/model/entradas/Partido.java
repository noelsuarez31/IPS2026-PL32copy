package giis.demo.model.entradas;

import java.time.LocalDate;
import java.time.LocalTime;

public class Partido {
	
	//Atributos de la BBDD
	
	private int id_partido;
	private LocalDate fecha;
	private LocalTime horaInicio;
	private LocalTime horaFin;
	private int id_local;
	private int id_visitante;
	private int id_instalacion;
	
	private boolean isClubDay;
	private double precioAbonado;
	
	
	//Atributos extras para facilitar la comprensión
	
	private String nameLocal;
	private String nameVisitante;	
	
	/**
	 * Constructor de la clase con parametros
	 * @param id_partido
	 * @param fecha
	 * @param id_local
	 * @param id_visitante
	 * @param nameLocal
	 * @param nameVisitante
	 */
	public Partido(int id_partido, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, int id_local, 
			int id_visitante, String nameLocal, String nameVisitante, int id_instalacion, boolean isClubDay, double precioAbonado) {
		
		this.id_partido = id_partido;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.id_local = id_local;
		this.id_visitante = id_visitante;
		this.nameLocal = nameLocal;
		this.nameVisitante = nameVisitante;
		this.id_instalacion = id_instalacion;
		
		this.isClubDay = isClubDay;
		this.precioAbonado = precioAbonado;
	}
	
	/**
	 * Devuelve el id de la instalacion
	 * @return el id de la instalacion dónde se juega el partido
	 */
	public int getId_instalacion() {
		
		return id_instalacion;
	
	}

	/**
	 * Devuelve el id del partido
	 * @return el id del partido
	 */
	public int getId_partido() {
		return id_partido;
	}
	
	/**
	 * Devuelve la fecha
	 * @return fecha partido
	 */
	public LocalDate getFecha() {
		return fecha;
	}
	
	/**
	 * Devuelve la hora de inicio del partido
	 * @return hora de inicio
	 */
	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	/**
	 * Devuelve la hora a la que acaba el partido
	 * @return hora final del partido
	 */
	public LocalTime getHoraFin() {
		return horaFin;
	}

	/**
	 * Devuelve el id del equipo local
	 * @return id del equipo local
	 */
	public int getId_local() {
		return id_local;
	}
	
	/**
	 * Devuelve el id del equipo visitante
	 * @return id del equipo visitante
	 */
	public int getId_visitante() {
		return id_visitante;
	}

	/**
	 * Devuelve el nombre del equipo local
	 * @return nombre del equipo local
	 */
	public String getNameLocal() {
		return nameLocal;
	}

	/**
	 * Devuelve el nombre del equipo visitante
	 * @return nombre del equipo visitante
	 */
	public String getNameVisitante() {
		return nameVisitante;
	}

	
	public boolean isClubDay() {
		return isClubDay;
	}

	public double getPrecioAbonado() {
		return precioAbonado;
	}

	/**
	 * toString de la clase
	 */
	@Override
	public String toString() {
		
		return "" + fecha + " - [" + nameLocal + " - " + nameVisitante + "] - [" + horaInicio + "-"
				+horaFin + "]";
	}

	
}
