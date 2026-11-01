package giis.demo.model.accion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

import giis.demo.jdbc.JDBC;
import giis.demo.util.ApplicationException;

public class GestionAcciones {

	private JDBC jdbc = new JDBC();

	/**
	 * Devuelve la lista completa de todos los accionistas registrados en el club.
	 * Utilizado para poblar el ComboBox de selección rápida de DNI en la vista.
	 * @return lista de accionistas
	 * @throws SQLException si ocurre un error en la consulta
	 */
	public List<Accionista> obtenerAccionistas() throws SQLException {
		return jdbc.getAccionistas();
	}

	/**
	 * Comprueba que el DNI indicado corresponda a un accionista registrado en el club.
	 * Si el DNI está vacío o no pertenece a ningún accionista, lanza ApplicationException.
	 * @param dni el DNI a verificar
	 * @return el objeto Accionista encontrado
	 * @throws SQLException si ocurre un error de acceso a la base de datos
	 * @throws ApplicationException si el DNI es inválido o no existe en la base de datos
	 */
	public Accionista validarYObtenerAccionista(String dni) throws SQLException {
		if (dni == null || dni.trim().isEmpty()) {
			throw new ApplicationException("Debe introducir o seleccionar un DNI.");
		}

		String dniLimpio = dni.trim();
		Accionista accionista = jdbc.getAccionistaByDni(dniLimpio);

		if (accionista == null) {
			throw new ApplicationException("El DNI introducido (" + dniLimpio + ") no corresponde a ningún accionista del club.");
		}

		return accionista;
	}

	/**
	 * Obtiene el resumen de participación de un accionista: total de acciones en su propiedad,
	 * total de acciones emitidas por el club y porcentaje de participación relativo.
	 * @param dni el DNI del accionista
	 * @return DTO con las cifras calculadas
	 * @throws SQLException si ocurre un error de base de datos
	 */
	public ResumenParticipacion obtenerResumenParticipacion(String dni) throws SQLException {
		List<Accion> accionesPropias = jdbc.getAccionesPropietario(dni);
		int totalPropias = accionesPropias.size();
		int totalClub = jdbc.getTotalAccionesEmitidas();

		double porcentaje = 0.0;
		if (totalClub > 0) {
			porcentaje = ((double) totalPropias * 100.0) / (double) totalClub;
			BigDecimal bd = BigDecimal.valueOf(porcentaje).setScale(2, RoundingMode.HALF_UP);
			porcentaje = bd.doubleValue();
		}

		return new ResumenParticipacion(totalPropias, totalClub, porcentaje);
	}

	/**
	 * Obtiene el listado de acciones en propiedad de un accionista ordenadas por fecha de adquisición.
	 * @param dni el DNI del accionista
	 * @return lista de acciones en propiedad
	 * @throws SQLException si ocurre un error de base de datos
	 */
	public List<Accion> obtenerAccionesAccionista(String dni) throws SQLException {
		return jdbc.getAccionesPropietario(dni);
	}
}
