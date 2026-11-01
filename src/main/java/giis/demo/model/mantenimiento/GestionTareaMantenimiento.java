package giis.demo.model.mantenimiento;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import giis.demo.jdbc.JDBC;
import giis.demo.model.empleado.EmpleadoNoDeportivo;
import giis.demo.model.empleado.Posicion;
import giis.demo.model.horarios.HorarioEspecifico;
import giis.demo.model.horarios.HorarioPeriodico;
import giis.demo.model.horarios.HorarioSemanal;
import giis.demo.model.reserva.Instalacion;
import giis.demo.model.reserva.Intervalo;
import giis.demo.util.ApplicationException;

public class GestionTareaMantenimiento {

	private JDBC jdbc = new JDBC();

	/**
	 * Devuelve la lista de instalaciones disponibles en la base de datos.
	 */
	public List<Instalacion> obtenerInstalaciones() throws SQLException {
		return jdbc.getAvailableInstalations();
	}

	/**
	 * Devuelve la lista de empleados no deportivos cuya posición es JARDINERIA.
	 */
	public List<EmpleadoNoDeportivo> obtenerJardineros() throws SQLException {
		return jdbc.getJardineros();
	}

	/**
	 * Obtiene las tareas de mantenimiento registradas para una instalación en una fecha concreta.
	 */
	public List<TareaMantenimiento> obtenerTareasInstalacionFecha(int idInstalacion, String fecha) throws SQLException {
		return jdbc.getTareasInstalacionFecha(idInstalacion, fecha);
	}

	/**
	 * Obtiene los intervalos de ocupación de una instalación en una fecha determinada
	 * (partidos, entrenamientos, reservas externas y tareas de mantenimiento).
	 */
	public List<Intervalo> obtenerOcupacionesInstalacion(int idInstalacion, String fechaStr) throws SQLException {
		if (fechaStr == null || fechaStr.isBlank() || fechaStr.equals("Ninguno")) {
			return new ArrayList<>();
		}
		return jdbc.obtenerOcupaciones(idInstalacion, LocalDate.parse(fechaStr));
	}

	/**
	 * Obtiene los turnos laborales efectivos de un jardinero para una fecha concreta.
	 * Aplica la regla híbrida consolidada en el Sprint 1: los horarios específicos
	 * sustituyen íntegramente a los horarios periódicos de ese día de la semana.
	 */
	public List<HorarioSemanal> obtenerTurnosLaboralesJardineroEnFecha(String dniJardinero, String fechaStr)
			throws SQLException {
		if (dniJardinero == null || dniJardinero.isBlank()) {
			throw new ApplicationException("Debe indicar el DNI del jardinero.");
		}
		if (fechaStr == null || fechaStr.isBlank() || fechaStr.equals("Ninguno")) {
			throw new ApplicationException("Debe seleccionar una fecha válida.");
		}

		LocalDate fecha = LocalDate.parse(fechaStr);
		int numDiaSemana = fecha.getDayOfWeek().getValue(); // 1 = Lunes, 7 = Domingo

		List<HorarioEspecifico> especificos = jdbc.getHorariosEspecificosFecha(dniJardinero, fechaStr);
		List<HorarioSemanal> turnosEfectivos = new ArrayList<>();

		if (!especificos.isEmpty()) {
			// Si tiene específicos para esa fecha, sustituyen por completo a los periódicos
			for (HorarioEspecifico he : especificos) {
				long mins = calcularMinutos(he.getHora_inicio(), he.getHora_fin());
				turnosEfectivos.add(new HorarioSemanal(
						numDiaSemana,
						obtenerNombreDia(numDiaSemana),
						fechaStr,
						he.getHora_inicio(),
						he.getHora_fin(),
						"Específico",
						mins
				));
			}
		} else {
			// Si no tiene específicos para esa fecha, se aplican los periódicos del día de la semana
			List<HorarioPeriodico> periodicos = jdbc.getHorariosPeriodicosEmpleado(dniJardinero);
			for (HorarioPeriodico hp : periodicos) {
				if (hp.getDia_semana() == numDiaSemana) {
					long mins = calcularMinutos(hp.getHora_inicio(), hp.getHora_fin());
					turnosEfectivos.add(new HorarioSemanal(
							numDiaSemana,
							obtenerNombreDia(numDiaSemana),
							fechaStr,
							hp.getHora_inicio(),
							hp.getHora_fin(),
							"Periódico",
							mins
					));
				}
			}
		}

		// Ordenación cronológica por hora de inicio
		Collections.sort(turnosEfectivos, new Comparator<HorarioSemanal>() {
			@Override
			public int compare(HorarioSemanal h1, HorarioSemanal h2) {
				LocalTime t1 = LocalTime.parse(h1.getHoraInicio());
				LocalTime t2 = LocalTime.parse(h2.getHoraInicio());
				return t1.compareTo(t2);
			}
		});

		return turnosEfectivos;
	}

	/**
	 * Valida que la tarea cumpla con todas las reglas de negocio de la HU y la registra en la BBDD:
	 * 1. Instalación y fecha válidas.
	 * 2. Horas de inicio y fin válidas (inicio < fin y dentro de 08:00 a 22:00).
	 * 3. Al menos un jardinero seleccionado y todos con puesto JARDINERIA.
	 * 4. Cada jardinero debe estar trabajando durante ese horario ese día (regla híbrida).
	 * 5. Ningún jardinero seleccionado debe tener otra tarea de mantenimiento solapada.
	 * 6. La instalación debe estar libre en esa franja horaria.
	 */
	public void validarYAsignarTarea(int idInstalacion, String fechaStr, String horaInicioStr, String horaFinStr,
			String descripcion, List<EmpleadoNoDeportivo> jardinerosSeleccionados) throws SQLException {

		// 1. Validación de instalación
		if (idInstalacion <= 0) {
			throw new ApplicationException("Debe seleccionar una instalación válida.");
		}

		// 2. Validación de fecha
		if (fechaStr == null || fechaStr.isBlank() || fechaStr.equals("Ninguno")) {
			throw new ApplicationException("Debe seleccionar una fecha para la tarea.");
		}

		// 3. Validación de horas
		if (horaInicioStr == null || horaInicioStr.isBlank() || horaFinStr == null || horaFinStr.isBlank()) {
			throw new ApplicationException("Debe indicar tanto la hora de inicio como la hora de fin.");
		}

		LocalTime inicioTarea = LocalTime.parse(horaInicioStr);
		LocalTime finTarea = LocalTime.parse(horaFinStr);

		if (!inicioTarea.isBefore(finTarea)) {
			throw new ApplicationException("La hora de inicio (" + horaInicioStr 
					+ ") debe ser anterior a la hora de fin (" + horaFinStr + ").");
		}

		if (inicioTarea.isBefore(LocalTime.of(8, 0)) || finTarea.isAfter(LocalTime.of(22, 0))) {
			throw new ApplicationException("El horario de la tarea debe estar comprendido dentro "
					+ "del horario de apertura de las instalaciones (08:00 a 22:00).");
		}

		// 4. Validación de jardineros seleccionados
		if (jardinerosSeleccionados == null || jardinerosSeleccionados.isEmpty()) {
			throw new ApplicationException("Debe seleccionar al menos un jardinero para realizar la tarea.");
		}

		for (EmpleadoNoDeportivo j : jardinerosSeleccionados) {
			if (j.getPosicion() != Posicion.JARDINERIA) {
				throw new ApplicationException("El empleado " + j.getNombre() + " " + j.getApellido() 
						+ " no es de tipo jardinero. Solo se pueden asignar empleados jardineros.");
			}
		}

		// 5. Validación de turno de trabajo de cada jardinero ese día
		for (EmpleadoNoDeportivo j : jardinerosSeleccionados) {
			List<HorarioSemanal> turnosJardinero = obtenerTurnosLaboralesJardineroEnFecha(j.getDni(), fechaStr);

			if (turnosJardinero.isEmpty()) {
				throw new ApplicationException("El jardinero " + j.getNombre() + " " + j.getApellido() 
						+ " (" + j.getDni() + ") no tiene turno de trabajo asignado para la fecha " + fechaStr + ".");
			}

			boolean cubiertoPorTurno = false;
			for (HorarioSemanal turno : turnosJardinero) {
				LocalTime inicioTurno = LocalTime.parse(turno.getHoraInicio());
				LocalTime finTurno = LocalTime.parse(turno.getHoraFin());

				if (!inicioTarea.isBefore(inicioTurno) && !finTarea.isAfter(finTurno)) {
					cubiertoPorTurno = true;
					break;
				}
			}

			if (!cubiertoPorTurno) {
				throw new ApplicationException("La tarea (" + horaInicioStr + " a " + horaFinStr 
						+ ") está fuera del horario de trabajo asignado al jardinero " 
						+ j.getNombre() + " " + j.getApellido() + " el día " + fechaStr + ".");
			}
		}

		// 6. Validación de solapamiento con otra tarea de mantenimiento del jardinero
		for (EmpleadoNoDeportivo j : jardinerosSeleccionados) {
			if (jdbc.tieneTareaSolapadaJardinero(j.getDni(), fechaStr, horaInicioStr, horaFinStr)) {
				throw new ApplicationException("El jardinero " + j.getNombre() + " " + j.getApellido() 
						+ " ya tiene asignada otra tarea de mantenimiento que se solapa con el horario (" 
						+ horaInicioStr + " - " + horaFinStr + ").");
			}
		}

		// 7. Validación de disponibilidad de la instalación (no solapar con reservas, partidos, etc.)
		List<Intervalo> ocupaciones = jdbc.obtenerOcupaciones(idInstalacion, LocalDate.parse(fechaStr));
		for (Intervalo ocupado : ocupaciones) {
			LocalTime inicioOcupado = ocupado.getHoraInicio();
			LocalTime finOcupado = ocupado.getHoraFin();

			if (inicioTarea.isBefore(finOcupado) && finTarea.isAfter(inicioOcupado)) {
				throw new ApplicationException("La instalación ya está ocupada en ese horario (" 
						+ inicioOcupado + " - " + finOcupado + ") por motivo: " + ocupado.getMotivo() + ".");
			}
		}

		// 8. Registro transaccional en la base de datos
		TareaMantenimiento nuevaTarea = new TareaMantenimiento(
				0,
				idInstalacion,
				fechaStr,
				horaInicioStr,
				horaFinStr,
				(descripcion == null || descripcion.isBlank()) ? "Mantenimiento" : descripcion.trim()
		);

		List<String> dnis = new ArrayList<>();
		for (EmpleadoNoDeportivo j : jardinerosSeleccionados) {
			dnis.add(j.getDni());
		}

		jdbc.registrarTareaMantenimiento(nuevaTarea, dnis);
	}

	/**
	 * Método auxiliar que transforma los textos de hora en tiempo real y calcula la diferencia en minutos.
	 */
	private long calcularMinutos(String horaInicio, String horaFin) {
		LocalTime inicio = LocalTime.parse(horaInicio);
		LocalTime fin = LocalTime.parse(horaFin);

		if (fin.isBefore(inicio)) {
			throw new ApplicationException("La hora de fin (" + horaFin 
					+ ") no puede ser anterior a la de inicio (" + horaInicio + ").");
		}

		return Duration.between(inicio, fin).toMinutes();
	}

	/**
	 * Devuelve el nombre del día en castellano a partir de su número (1 = Lunes, 7 = Domingo).
	 */
	private String obtenerNombreDia(int diaSemana) {
		String[] dias = { "", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" };
		if (diaSemana >= 1 && diaSemana <= 7) {
			return dias[diaSemana];
		}
		return "Desconocido";
	}
}
