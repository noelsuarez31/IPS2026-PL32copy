package giis.demo.model.equipo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventoOcupacion {

	private int idReferencia;
	private String origen; // "RESERVA", "JARDINERIA_ESPECIFICO", "JARDINERIA_PERIODICO", "OTRO"
	private LocalTime horaInicio;
	private LocalTime horaFin;
	private String tipo;
	private String detalle;
	private boolean sobreescribible;

	private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

	public EventoOcupacion(int idReferencia, String origen, LocalTime horaInicio, LocalTime horaFin, String tipo, String detalle, boolean sobreescribible) {
		this.idReferencia = idReferencia;
		this.origen = origen;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.tipo = tipo;
		this.detalle = detalle;
		this.sobreescribible = sobreescribible;
	}

	public EventoOcupacion(LocalTime horaInicio, LocalTime horaFin, String tipo, String detalle, boolean sobreescribible) {
		this(0, "OTRO", horaInicio, horaFin, tipo, detalle, sobreescribible);
	}

	public int getIdReferencia() {
		return idReferencia;
	}

	public String getOrigen() {
		return origen;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDetalle() {
		return detalle;
	}

	public boolean isSobreescribible() {
		return sobreescribible;
	}

	public Object[] toRow() {
		return new Object[] {
			horaInicio.format(FORMATO_HORA),
			horaFin.format(FORMATO_HORA),
			tipo,
			detalle,
			sobreescribible ? "Sí (Sobreescribible)" : "No (Colisión)"
		};
	}
}
