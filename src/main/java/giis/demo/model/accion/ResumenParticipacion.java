package giis.demo.model.accion;

/**
 * Objeto de transferencia de datos (DTO) que encapsula el resumen de participación
 * de un accionista sobre el total de acciones emitidas por el club.
 */
public class ResumenParticipacion {

	private int totalAccionesPropias;
	private int totalAccionesClub;
	private double porcentajeParticipacion;

	public ResumenParticipacion() {
	}

	public ResumenParticipacion(int totalAccionesPropias, int totalAccionesClub, double porcentajeParticipacion) {
		this.totalAccionesPropias = totalAccionesPropias;
		this.totalAccionesClub = totalAccionesClub;
		this.porcentajeParticipacion = porcentajeParticipacion;
	}

	public int getTotalAccionesPropias() {
		return totalAccionesPropias;
	}

	public void setTotalAccionesPropias(int totalAccionesPropias) {
		this.totalAccionesPropias = totalAccionesPropias;
	}

	public int getTotalAccionesClub() {
		return totalAccionesClub;
	}

	public void setTotalAccionesClub(int totalAccionesClub) {
		this.totalAccionesClub = totalAccionesClub;
	}

	public double getPorcentajeParticipacion() {
		return porcentajeParticipacion;
	}

	public void setPorcentajeParticipacion(double porcentajeParticipacion) {
		this.porcentajeParticipacion = porcentajeParticipacion;
	}
}
