package giis.demo.model.mantenimiento;

import java.util.ArrayList;
import java.util.List;
import giis.demo.model.empleado.EmpleadoNoDeportivo;

/**
 * Representa una tarea de mantenimiento asignada a una instalación
 * en una fecha y rango horario determinados, junto con los jardineros que la ejecutan.
 */
public class TareaMantenimiento {

	private int idTarea;
	private int idInstalacion;
	private String nombreInstalacion;
	private String fecha; // Formato "yyyy-MM-dd"
	private String horaInicio; // Formato "HH:mm"
	private String horaFin; // Formato "HH:mm"
	private String descripcion;
	private List<EmpleadoNoDeportivo> jardinerosAsignados;

	public TareaMantenimiento() {
		this.jardinerosAsignados = new ArrayList<>();
	}

	public TareaMantenimiento(int idTarea, int idInstalacion, String fecha, String horaInicio, String horaFin,
			String descripcion) {
		this.idTarea = idTarea;
		this.idInstalacion = idInstalacion;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.descripcion = descripcion;
		this.jardinerosAsignados = new ArrayList<>();
	}

	public int getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(int idTarea) {
		this.idTarea = idTarea;
	}

	public int getIdInstalacion() {
		return idInstalacion;
	}

	public void setIdInstalacion(int idInstalacion) {
		this.idInstalacion = idInstalacion;
	}

	public String getNombreInstalacion() {
		return nombreInstalacion;
	}

	public void setNombreInstalacion(String nombreInstalacion) {
		this.nombreInstalacion = nombreInstalacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<EmpleadoNoDeportivo> getJardinerosAsignados() {
		return jardinerosAsignados;
	}

	public void setJardinerosAsignados(List<EmpleadoNoDeportivo> jardinerosAsignados) {
		this.jardinerosAsignados = jardinerosAsignados;
	}

	/**
	 * Devuelve un String legible con los nombres de todos los jardineros asignados
	 * para mostrarlo cómodamente en las tablas de la interfaz gráfica.
	 */
	public String getNombresJardinerosFormateados() {
		if (jardinerosAsignados == null || jardinerosAsignados.isEmpty()) {
			return "Sin asignar";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < jardinerosAsignados.size(); i++) {
			EmpleadoNoDeportivo emp = jardinerosAsignados.get(i);
			sb.append(emp.getNombre()).append(" ").append(emp.getApellido());
			if (i < jardinerosAsignados.size() - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
}