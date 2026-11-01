package giis.demo.model.horarios;

public class HorarioSemanal {

	private int diaSemanaNum; // 1 = Lunes, ..., 7 = Domingo
	private String nombreDia; // "Lunes", "Martes", etc.
	private String fecha;     // "YYYY-MM-DD"
	private String horaInicio; // "HH:mm"
	private String horaFin;   // "HH:mm"
	private String tipoHorario; // "Periódico" o "Específico"
	private long duracionMinutos;

	public HorarioSemanal(int diaSemanaNum, String nombreDia, String fecha, String horaInicio, String horaFin,
			String tipoHorario, long duracionMinutos) {
		this.diaSemanaNum = diaSemanaNum;
		this.nombreDia = nombreDia;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.tipoHorario = tipoHorario;
		this.duracionMinutos = duracionMinutos;
	}

	public int getDiaSemanaNum() {
		return diaSemanaNum;
	}
	
	public String getNombreDia() {
		return nombreDia;
	}

	public String getFecha() {
		return fecha;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public String getTipoHorario() {
		return tipoHorario;
	}

	public long getDuracionMinutos() {
		return duracionMinutos;
	}

	/**
	 * Devuelve la duración formateada en horas y minutos (ej. "4 h 30 min").
	 */
	public String getDuracionFormateada() {
		long horas = duracionMinutos / 60;
		long mins = duracionMinutos % 60;
		return String.format("%d h %02d min", horas, mins);
	}
}