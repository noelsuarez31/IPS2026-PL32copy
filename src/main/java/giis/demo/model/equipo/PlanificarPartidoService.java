package giis.demo.model.equipo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import giis.demo.jdbc.JDBC;
import giis.demo.model.entradas.enumerados.TipoSeccion;
import giis.demo.model.reserva.Instalacion;

public class PlanificarPartidoService {
	private JDBC jdbc = new JDBC();

	/**
	 * Obtiene los equipos locales de la organización (es_propio = 1).
	 */
	public List<Equipo> obtenerEquiposLocales() {
		try {
			return jdbc.getEquiposPropios();
		} catch (SQLException e) {
			throw new RuntimeException("Error al cargar los equipos locales: " + e.getMessage(), e);
		}
	}

	/**
	 * Obtiene los equipos visitantes externos (es_propio = 0).
	 */
	public List<Equipo> obtenerEquiposVisitantes() {
		try {
			return jdbc.getEquiposVisitantes();
		} catch (SQLException e) {
			throw new RuntimeException("Error al cargar los equipos visitantes: " + e.getMessage(), e);
		}
	}

	/**
	 * Obtiene las instalaciones disponibles para disputar el encuentro.
	 */
	public List<Instalacion> obtenerInstalacionesDisponibles() {
		try {
			return jdbc.getAvailableInstalations();
		} catch (SQLException e) {
			throw new RuntimeException("Error al cargar las instalaciones: " + e.getMessage(), e);
		}
	}

	/**
	 * Comprueba si el horario propuesto colisiona con otro partido en la misma
	 * instalación. Si hay colisión no sobreescribible, lanza excepción.
	 */
	public void comprobarColisionesPartido(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
			int idInstalacion) {
		if (!horaFin.isAfter(horaInicio)) {
			throw new IllegalArgumentException("La hora de fin debe ser posterior a la de inicio.");
		}
		
		try {
			jdbc.comprobarColisionesPartido(fecha, horaInicio, horaFin, idInstalacion);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
    /**
     * Obtiene todos los eventos de ocupación de una instalación en una fecha dada.
     */
    public List<EventoOcupacion> obtenerEventosOcupacion(int idInstalacion, LocalDate fecha) {
        try {
            return jdbc.obtenerEventosOcupacionPartido(idInstalacion, fecha);
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar la ocupación de la instalación: " + e.getMessage(), e);
        }
    }

	/**
	 * Obtiene los eventos sobreescribibles coincidentes (reservas, jardinería,
	 * entrevistas, franjas).
	 */
	public List<EventoOcupacion> obtenerSobreescribiblesCoincidentes(LocalDate fecha, LocalTime horaInicio,
			LocalTime horaFin, int idInstalacion) {
		try {
			return jdbc.obtenerSobreescribiblesPartidoSolapados(idInstalacion, fecha, horaInicio, horaFin);
		} catch (SQLException e) {
			throw new RuntimeException("Error al consultar solapamientos: " + e.getMessage(), e);
		}
	}

	/**
	 * Da de alta el partido, guarda los precios por sección y cancela las
	 * actividades coincidentes dentro de la misma transacción en la base de datos.
	 */
	public void planificarPartido(int idLocal, int idVisitante, int idInstalacion, LocalDate fecha,
			LocalTime horaInicio, LocalTime horaFin, boolean esDiaClub, double precioAbonado,
			Map<TipoSeccion, Double> preciosSeccion, List<EventoOcupacion> cancelables) {
		if (idLocal == idVisitante) {
            throw new IllegalArgumentException("El equipo local y el visitante no pueden ser el mismo.");
        }
		
		try {
			jdbc.guardarPartidoCancelandoCoincidentes(idLocal, idVisitante, idInstalacion, fecha, horaInicio, horaFin,
					esDiaClub, precioAbonado, preciosSeccion, cancelables);
		} catch (SQLException e) {
			throw new RuntimeException("Error al registrar el partido en la base de datos: " + e.getMessage(), e);
		}
	}
}
