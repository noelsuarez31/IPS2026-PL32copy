package giis.demo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import giis.demo.exceptions.AbonoNoPermitidoException;
import giis.demo.jdbc.abonados.AbonadoJdbc;
import giis.demo.jdbc.abonados.AbonoJdbc;
import giis.demo.jdbc.empleado.BaseEmpleadoJdbc;
import giis.demo.jdbc.empleado.EmpleadoDeportivoJdbc;
import giis.demo.jdbc.empleado.EmpleadoNoDeportivoJdbc;
import giis.demo.jdbc.entradas.ButacaJdbc;
import giis.demo.jdbc.entradas.PartidoJdbc;
import giis.demo.jdbc.entradas.RegistrarVentaJdbc;
import giis.demo.jdbc.entrevistas.EntrevistaJdbc;
import giis.demo.jdbc.entrevistas.FranjaJdbc;
import giis.demo.jdbc.equipo.CategoriaEquipoJdbc;
import giis.demo.jdbc.equipo.EquipoJdbc;
import giis.demo.jdbc.historial.HistorialJdbc;
import giis.demo.jdbc.horarios.HorarioEspecificoJdbc;
import giis.demo.jdbc.horarios.HorarioPeriodicoJdbc;
import giis.demo.jdbc.mantenimiento.TareaMantenimientoJdbc;
import giis.demo.jdbc.reservas.ComprobarDisponibilidadJdbc;
import giis.demo.jdbc.reservas.InstalacionJdbc;
import giis.demo.jdbc.reservas.ReservaJdbc;
import giis.demo.jdbc.reservas.UsuarioJdbc;
import giis.demo.jdbc.tienda.ProductoJdbc;
import giis.demo.jdbc.tienda.RegistrarVentaMerchandising;
import giis.demo.model.abonos.Abonado;
import giis.demo.model.abonos.Abono;
import giis.demo.model.empleado.BaseEmpleado;
import giis.demo.model.empleado.EmpleadoDeportivo;
import giis.demo.model.empleado.EmpleadoNoDeportivo;
import giis.demo.model.entradas.Butaca;
import giis.demo.model.entradas.Partido;
import giis.demo.model.entradas.enumerados.TipoSeccion;
import giis.demo.model.entradas.enumerados.TipoTribuna;
import giis.demo.model.entrevistas.FranjaHoraria;
import giis.demo.model.equipo.CategoriaEquipo;
import giis.demo.model.equipo.Equipo;
import giis.demo.model.historial.Ingreso;
import giis.demo.model.historial.LineaDetalle;
import giis.demo.model.horarios.HorarioEspecifico;
import giis.demo.model.horarios.HorarioPeriodico;
import giis.demo.model.mantenimiento.TareaMantenimiento;
import giis.demo.model.reserva.Instalacion;
import giis.demo.model.reserva.Intervalo;
import giis.demo.model.reserva.Reserva;
import giis.demo.model.reserva.Usuario;
import giis.demo.model.tienda.Producto;
import giis.demo.model.equipo.EventoOcupacion;

public class JDBC {

	// public static final String DRIVER_GROUP2 = "org.sqlite.JDBC";
	public static final String URL_GROUP2 = "jdbc:sqlite:DemoDB.db";

	private Connection con;

	/**
	 * Constructor de la clase, inicializa la conexion
	 */
	public JDBC() {

		try {

			con = DriverManager.getConnection(URL_GROUP2);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Obtiene las butacas libres disponibles
	 * 
	 * @param idPartido
	 * @param tribuna
	 * @param seccion
	 * @return las butcas libres
	 * @throws SQLException
	 */
	public List<Butaca> getFreeButacas(int idPartido, TipoTribuna tribuna, TipoSeccion seccion) throws SQLException {

		return ButacaJdbc.getFreeButacas(con, idPartido, tribuna, seccion);

	}

	/**
	 * Obtienes los distintos partidos disponibles
	 * 
	 * @return los partidos disponibles
	 * @throws SQLException
	 */
	public List<Partido> getPartidos() throws SQLException {

		return PartidoJdbc.getPartidos(con);

	}

	/**
	 * Almacena las entradas y la venta
	 * 
	 * @param idPartido
	 * @param butacasSeleccionadas
	 * @throws SQLException
	 */
	public void almacenar(int idPartido, List<Butaca> butacasSeleccionadas) throws SQLException {

		RegistrarVentaJdbc.registrar(con, idPartido, butacasSeleccionadas);

	}

	/**
	 * Obtiene todas las butacas en una tribuna y sección
	 * 
	 * @param idPartido
	 * @param tribuna
	 * @param seccion
	 * @return butacas de una tribuna y seccion
	 * @throws SQLException
	 */
	public List<Butaca> getAllButacas(int idPartido, TipoTribuna tribuna, TipoSeccion seccion) throws SQLException {

		return ButacaJdbc.getAllButacas(con, idPartido, tribuna, seccion);

	}

	/**
	 * Cierra la conexion
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {

		if (con != null && !con.isClosed()) {

			con.close();
		}

	}

	/**
	 * Devuelve el objeto categoria con todos los atributos a partir del nombre de
	 * la categoria
	 * 
	 * @param nombreCategoriaSeleccionada
	 * @return
	 * @throws SQLException
	 */
	public CategoriaEquipo obtenerObjetoCategoria(String nombreCategoriaSeleccionada) throws SQLException {
		return CategoriaEquipoJdbc.obtenerObjetoCategoria(con, nombreCategoriaSeleccionada);
	}

	/**
	 * Devuelve una lista con empleados deportivos cuya posicion es
	 * TECNICO_ADICIONAL
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<EmpleadoDeportivo> obtenerRestoTecnicos() throws SQLException {
		return EmpleadoDeportivoJdbc.obtenerRestoTecnicos(con);
	}

	/**
	 * Devuelve una lista con el nombre de las categorias para un tipo de equipo
	 * correspondiente
	 * 
	 * @param tipo
	 * @return
	 * @throws SQLException
	 */
	public List<String> obtenerNombreDeCategoriasPorTipo(String tipo) throws SQLException {
		return CategoriaEquipoJdbc.obtenerNombreDeCategoriasPorTipo(con, tipo);
	}

	/**
	 * Devuelve una lista con los empleados deportivos cuya posicion es JUGADOR y no
	 * tienen equipo asignado
	 * 
	 * @param categoria
	 * @return
	 * @throws SQLException
	 */
	public List<EmpleadoDeportivo> obtenerJugadoresDisponibles(CategoriaEquipo categoria) throws SQLException {
		return EmpleadoDeportivoJdbc.obtenerJugadoresDisponibles(con, categoria);
	}

	/**
	 * Devuelve una lista de empleados deportivos con posicion ENTRENADOR que no
	 * tienen equipo asignado
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<EmpleadoDeportivo> obtenerEntrenadoresDisponibles() throws SQLException {
		return EmpleadoDeportivoJdbc.obtenerEntrenadoresDisponibles(con);
	}

	/**
	 * Almacena el equipo creado en la BBDD. Validando que los jugadores,
	 * entrenadores y tecnicos adicionales no tengan un equipo asignado ya. Les
	 * asigna el id del nuevo equipo.
	 * 
	 * @param equipo
	 * @param jugadores
	 * @param entrenadores
	 * @param tecnicosAdicionales
	 * @throws SQLException
	 */
	public void almacenarEquipo(Equipo equipo, List<EmpleadoDeportivo> jugadores, List<EmpleadoDeportivo> entrenadores,
			List<EmpleadoDeportivo> tecnicosAdicionales) throws SQLException {
		EquipoJdbc.almacenarEquipo(con, equipo, jugadores, entrenadores, tecnicosAdicionales);
	}

	/**
	 * Inserta el horario en la tabla
	 * 
	 * @param horario
	 * @throws SQLException
	 */
	public void añadirHorarioPeriodico(HorarioPeriodico horario) throws SQLException {
		HorarioPeriodicoJdbc.añadirHorario(con, horario);
	}

	/**
	 * Devuelve los horarios de un empleado
	 * 
	 * @param idEmpleado
	 * @throws SQLException
	 */
	public List<HorarioPeriodico> getHorariosPeriodicosEmpleado(String dniEmpleado) throws SQLException {
		return HorarioPeriodicoJdbc.getHorariosEmpleado(con, dniEmpleado);
	}

	public List<EmpleadoNoDeportivo> getEmpleadosNoDeportivos() throws SQLException {
		return EmpleadoNoDeportivoJdbc.getEmpleadosNoDeportivos(con);
	}

	public void añadirHorarioEspecifico(HorarioEspecifico horario) throws SQLException {
		HorarioEspecificoJdbc.añadirHorarioEspecifico(con, horario);
	}

	public boolean tieneHorariosPeriodicos(String dniEmpleado) throws SQLException {
		return HorarioEspecificoJdbc.tieneHorariosPeriodicos(con, dniEmpleado);
	}

	public List<HorarioEspecifico> getHorariosEspecificosFecha(String dniEmpleado, String fecha) throws SQLException {
		return HorarioEspecificoJdbc.getHorariosFecha(con, dniEmpleado, fecha);
	}

	public List<HorarioEspecifico> getHorariosEspecificosRango(String dniEmpleado, String fechaInicio, String fechaFin)
			throws SQLException {
		return HorarioEspecificoJdbc.getHorariosRango(con, dniEmpleado, fechaInicio, fechaFin);
	}

	public List<HorarioEspecifico> getHorariosEspecificosEmpleado(String dniEmpleado) throws SQLException {
		return HorarioEspecificoJdbc.getHorariosEmpleado(con, dniEmpleado);
	}

	/**
	 * Devuelve las instalaciones disponibles a reservar
	 * 
	 * @return instalaciones disponibles para que un usuario externo realice la
	 *         reserva
	 * @throws SQLException
	 */
	public List<Instalacion> getAvailableInstalations() throws SQLException {

		return InstalacionJdbc.getAvailableInstalations(con);

	}

	/**
	 * Devuelve el id del usuario con el nombre y la tarjeta dados. Si no existe, se
	 * crea
	 * 
	 * @param name
	 * @param card
	 * @return el id del usuario en la BBDD
	 * @throws SQLException
	 */
	public void obtenerOCrearUsuario(Usuario user) throws SQLException {

		UsuarioJdbc.obtenerOCrear(con, user);
	}

	/**
	 * Da de alta un empleado (datos comunes + datos de su tipo) en una unica
	 * transaccion
	 * 
	 * @param empleado EmpleadoDeportivo o EmpleadoNoDeportivo
	 * @throws SQLException
	 */
	public void almacenarEmpleado(BaseEmpleado empleado) throws SQLException {
		boolean autoCommitOriginal = con.getAutoCommit();
		try {
			con.setAutoCommit(false);
			BaseEmpleadoJdbc.insertarBaseEmpleado(con, empleado);
			if (empleado instanceof EmpleadoDeportivo) {
				EmpleadoDeportivoJdbc.insertarEmpleadoDeportivo(con, (EmpleadoDeportivo) empleado);
			} else {
				EmpleadoNoDeportivoJdbc.insertarEmpleadoNoDeportivo(con, (EmpleadoNoDeportivo) empleado);
			}
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			con.setAutoCommit(autoCommitOriginal);
		}
	}

	public boolean existeDniEmpleado(String dni) throws SQLException {
		return BaseEmpleadoJdbc.existeDni(con, dni);
	}

	public boolean existeTelefonoEmpleado(String telefono) throws SQLException {
		return BaseEmpleadoJdbc.existeTelefono(con, telefono);
	}

	public Connection getCon() {
		return con;
	}

	/**
	 * Devuelve una lista de los jugadores que pertenecen al equipo que tiene como
	 * id el pasado por parametro
	 * 
	 * @param idEquipoProfesional
	 * @return
	 * @throws SQLException
	 */
	public List<EmpleadoDeportivo> cargarJugadoresEquipoProfesional(int idEquipoProfesional) throws SQLException {
		return FranjaJdbc.cargarJugadoresEquipoProfesional(con, idEquipoProfesional);
	}

	/**
	 * Devuelve una lista de dnis que pertenecen a entrenadores de equipos
	 * profesionales
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<String> obtenerDnisEntrenadoresDeEquiposProfesionales() throws SQLException {
		return FranjaJdbc.obtenerDnisEntrenadoresDeEquiposProfesionales(con);
	}

	/**
	 * Almacena la franja segun los datos pasados por parametro
	 * 
	 * @param nuevaFranja
	 * @throws SQLException
	 */
	public void almacenarFranja(FranjaHoraria nuevaFranja) throws SQLException {
		FranjaJdbc.almacenarFranja(con, nuevaFranja);
	}

	/**
	 * Devuelve true si el jugador tiene asignada una entrevista para esa fecha
	 * 
	 * @param jugador
	 * @param fechaFranja
	 * @return
	 * @throws SQLException
	 */
	public boolean jugadorTieneAsignadaEntrevista(EmpleadoDeportivo jugador, LocalDate fechaFranja)
			throws SQLException {
		return FranjaJdbc.jugadorTieneAsignadaEntrevista(con, jugador, fechaFranja);
	}

	/**
	 * Devuelve el id del equipo al que entrena el entrenador con dni el que se pasa
	 * por parametro
	 * 
	 * @param dniEntrenador
	 * @return
	 * @throws SQLException
	 */
	public int getIdEquipo(String dniEntrenador) throws SQLException {
		return FranjaJdbc.getIdEquipo(con, dniEntrenador);
	}

	/**
	 * Devuelve true si el equipo al que pertenece el jugador tiene asignado un
	 * entrenamiento/partido que tenga conflicto con la fecha y horas pasadas por
	 * parametro
	 * 
	 * @param jugador
	 * @param fechaFranja
	 * @param horaInicio
	 * @param horaFin
	 * @return
	 * @throws SQLException
	 */
	public String equipoTienePartidoOEntrenamiento(EmpleadoDeportivo jugador, LocalDate fechaFranja,
			LocalTime horaInicio, LocalTime horaFin) throws SQLException {
		return FranjaJdbc.equipoTienePartidoOEntrenamiento(con, jugador, fechaFranja, horaInicio, horaFin);
	}

	/**
	 * Devuelve true si el jugador pasado por parametro ya tiene una franja para esa
	 * fecha y hora o tiene otra que tenga conflicto
	 * 
	 * @param jugador
	 * @param fechaFranja
	 * @param horaInicio
	 * @param horaFin
	 * @return
	 * @throws SQLException
	 */
	public boolean yaHayEsaFranjaParaEseJugador(EmpleadoDeportivo jugador, LocalDate fechaFranja, LocalTime horaInicio,
			LocalTime horaFin) throws SQLException {
		return FranjaJdbc.yaHayEsaFranjaParaEseJugador(con, jugador, fechaFranja, horaInicio, horaFin);
	}

	/**
	 * Obtiene el catálogo de productos disponibles en la base de datos
	 * 
	 * @return lista de productos
	 * @throws SQLException
	 */
	public List<Producto> getProductosTienda() throws SQLException {
		return ProductoJdbc.getProductos(con);
	}

	/**
	 * Almacena la venta de merchandising de forma transaccional
	 * 
	 * @param total       Importe total de la cesta
	 * @param lineasCesta Mapa con los productos y sus cantidades
	 * @throws SQLException
	 */
	public void almacenarVentaMerchandising(double total, Map<Producto, Integer> lineasCesta) throws SQLException {
		RegistrarVentaMerchandising.registrar(con, total, lineasCesta);
	}

	/**
	 * Realiza la reserva de la instalacion
	 * 
	 * @param reserva
	 * @return true si se ha añadido la reserva a la BBDD, false en caso contrario
	 * @throws SQLException
	 */
	public boolean registrarReserva(Reserva reserva) throws SQLException {

		return ReservaJdbc.registrarReserva(con, reserva) == 1;

	}

	/**
	 * Comprueba si para una fecha y hora de inicio y fin dadas, una instalación
	 * concreta está libre
	 * 
	 * @param id_instalacion
	 * @param fecha
	 * @param horaInicio
	 * @param horaFin
	 * @return true si está libre, false en caso contrario
	 * @throws SQLException
	 */
	public boolean comprobarDisponibilidad(int id_instalacion, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin)
			throws SQLException {

		return ComprobarDisponibilidadJdbc.estaDisponible(con, id_instalacion, fecha, horaInicio, horaFin);

	}

	/**
	 * Obtiene las franjas ocupadas de una instalacion en una fecha indicando el
	 * motivo
	 * 
	 * @param idInstalacion
	 * @param fecha
	 * @return lista de los intervalos
	 * @throws SQLException
	 */
	public List<Intervalo> obtenerOcupaciones(int idInstalacion, LocalDate fecha) throws SQLException {

		return ComprobarDisponibilidadJdbc.obtenerIntervalosOcupados(con, idInstalacion, fecha);

	}

	/**
	 * Obtiene los intervalos disponibles
	 * 
	 * @param idInstalacion
	 * @param fecha
	 * @return lista de los intervalos
	 * @throws SQLException
	 */
	public List<Intervalo> obtenerIntervalosDisponibels(int idInstalacion, LocalDate fecha) throws SQLException {

		return ComprobarDisponibilidadJdbc.obtenerIntervalosDisponibles(con, idInstalacion, fecha);

	}

	/**
	 * Crea la entrevista y elimina las franjas del jugador para ese mismo dia
	 * 
	 * @param jugadorSeleccionado
	 * @param franjaSeleccionada
	 * @param nombreMedioCom
	 * @throws SQLException
	 */
	public void crearEntrevistaYEliminarFranjasCoincidentes(EmpleadoDeportivo jugadorSeleccionado,
			FranjaHoraria franjaSeleccionada, String nombreMedioCom) throws SQLException {
		EntrevistaJdbc.crearEntrevistaYEliminarFranjasCoincidentes(con, jugadorSeleccionado, franjaSeleccionada,
				nombreMedioCom);
	}

	/**
	 * Devuelve una lista de Franjas Horarias para el jugador y fecha seleccionados
	 * 
	 * @param jugadorSeleccionado
	 * @param fechaSeleccionada
	 * @return
	 * @throws SQLException
	 */
	public List<FranjaHoraria> cargarFranjas(EmpleadoDeportivo jugadorSeleccionado, LocalDate fechaSeleccionada)
			throws SQLException {
		return EntrevistaJdbc.cargarFranjas(con, jugadorSeleccionado, fechaSeleccionada);
	}

	/**
	 * Devuelve una lista de los jugadores que tienen franjas creadas sin
	 * entrevistas asociadas
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<EmpleadoDeportivo> getJugadoresConFranjas() throws SQLException {
		return EntrevistaJdbc.getJugadoresConFranjas(con);
	}

	/**
	 * Devuelve los dias del mes y año seleccionados que el jugador tiene franjas
	 * 
	 * @param jugadorSeleccionado
	 * @param mes
	 * @param año
	 * @return
	 * @throws SQLException
	 */
	public List<LocalDate> getDiasConFranjaYSinEntrevista(EmpleadoDeportivo jugadorSeleccionado, int mes, int año)
			throws SQLException {
		return EntrevistaJdbc.getDiasConFranjaYSinEntrevista(con, jugadorSeleccionado, mes, año);
	}

	/**
	 * Devuelve los ingresos (ventas y reservas) producidos entre dos fechas, ambas
	 * incluidas
	 * 
	 * @throws SQLException
	 */
	public List<Ingreso> getIngresos(LocalDate desde, LocalDate hasta) throws SQLException {
		return HistorialJdbc.getIngresos(con, desde, hasta);
	}

	/**
	 * Devuelve las lineas (producto, unidades, precio unitario) de una venta de
	 * merchandising
	 * 
	 * @throws SQLException
	 */
	public List<LineaDetalle> getDetalleVentaMerchandising(int idVenta) throws SQLException {
		return HistorialJdbc.getDetalleVentaMerchandising(con, idVenta);
	}

	public boolean existeTelefonoEnOtroEmpleado(String telefono, String dniPropio) throws SQLException {
		return BaseEmpleadoJdbc.existeTelefonoEnOtroEmpleado(con, telefono, dniPropio);
	}

	/**
	 * Devuelve todos los empleados (deportivos y no deportivos)
	 * 
	 * @throws SQLException
	 */
	public List<BaseEmpleado> getEmpleados() throws SQLException {
		return BaseEmpleadoJdbc.getEmpleados(con);
	}

	/**
	 * Devuelve la categoria del equipo indicado (con sus limites de edad)
	 * 
	 * @throws SQLException
	 */
	public CategoriaEquipo obtenerCategoriaDeEquipo(int idEquipo) throws SQLException {
		return CategoriaEquipoJdbc.obtenerCategoriaDeEquipo(con, idEquipo);
	}

	/**
	 * Modifica los datos de un empleado en una unica transaccion
	 * 
	 * @param dniOriginal dni que tenia el empleado antes de la modificacion
	 * @param empleado    datos nuevos
	 * @throws SQLException
	 */
	public void modificarEmpleado(String dniOriginal, BaseEmpleado empleado) throws SQLException {
		boolean autoCommitOriginal = con.getAutoCommit();
		try {
			con.setAutoCommit(false);
			BaseEmpleadoJdbc.actualizarEmpleado(con, dniOriginal, empleado);
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			con.setAutoCommit(autoCommitOriginal);
		}
	}

	/**
	 * Elimina un empleado y sus datos dependientes en una unica transaccion
	 * 
	 * @throws SQLException
	 */
	public void eliminarEmpleado(String dni) throws SQLException {
		boolean autoCommitOriginal = con.getAutoCommit();
		try {
			con.setAutoCommit(false);
			BaseEmpleadoJdbc.eliminarEmpleado(con, dni);
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			con.setAutoCommit(autoCommitOriginal);
		}
	}
	

	/**
	 * Obtiene todos los equipos locales disponibles
	 * 
	 * @return una lista con los equipos loclaes
	 * @throws SQLException
	 */
	public Equipo[] getEquiposLocales() throws SQLException {

		List<Equipo> equipos = EquipoJdbc.getEquiposPropios(con);

		return equipos.toArray(new Equipo[0]);
	}

	/**
	 * Obtiene los asientos ocupados
	 * 
	 * @param idEquipo
	 * @param nameTribuna
	 * @param nameSeccion
	 * @param now
	 * @return uan lista con los asientos ocupados
	 * @throws SQLException
	 */
	public List<int[]> getAsientosOcupados(int idEquipo, String nameTribuna, String nameSeccion, LocalDate inicio, LocalDate fin)
			throws SQLException {

		return AbonoJdbc.getAsientosOcupados(con, idEquipo, nameTribuna, nameSeccion, inicio, fin);
	}

	/**
	 * Obtiene la informacion del abono segun dni y una fecha
	 * 
	 * @param dni
	 * @param fechaInicio
	 * @return el Abono si existe
	 * @throws SQLException
	 */
	public Abono getInfoAbono(String dni, LocalDate fechaInicio) throws SQLException {

		return AbonoJdbc.getInfoAbono(con, dni, fechaInicio);

	}

	/**
	 * Comprueba si un dni tiene un abono para la temporada indicada
	 * 
	 * @param dni
	 * @param fechaInicio
	 * @return true si tiene, false en caso contrario
	 * @throws SQLException
	 */
	public boolean tieneAbonoEstaTemporada(String dni, LocalDate fechaInicio) throws SQLException {

		return AbonadoJdbc.tieneAbonoTemporada(con, dni, fechaInicio);
	}
	
	/**
	 * Inserta el abonado en la BBDD
	 * 
	 * @param abonado
	 * @param abono
	 * @throws SQLException
	 */
	public void insertarAbonado(Abonado abonado, Abono abono) throws SQLException {
		
		AbonadoJdbc.insertarAbonado(con, abonado);
		
	}
	
	/**
	 * Inserta el abono en la BBDD
	 * 
	 * @param abonado
	 * @param abono
	 * @param idEquipo
	 * @throws SQLException
	 * @throws AbonoNoPermitidoException
	 */
	public void venderAbono(Abonado abonado, Abono abono, int idEquipo) throws SQLException, AbonoNoPermitidoException {
		
		AbonoJdbc.venderAbono(con, abonado, abono, idEquipo);
		
	}

	/**
	 * Devuelve la lista de entrenamientos y partidos que tiene el equipo,
	 * de id el pasado por parametro, asociados
	 * @param idEquipo
	 * @param fecha
	 * @return
	 * @throws SQLException
	 */
	public List<String> obtenerPartidosYEntrenamientos(int idEquipo, LocalDate fecha) throws SQLException {
		return FranjaJdbc.obtenerPartidosYEntrenamientos(con, idEquipo, fecha);
	}
	
	/**
	 * Devuelve la lista de empleados que tienen puesto de JARDINERIA
	 */
	public List<EmpleadoNoDeportivo> getJardineros() throws SQLException {
		return TareaMantenimientoJdbc.getJardineros(con);
	}

	/**
	 * Comprueba si un jardinero tiene otra tarea de mantenimiento solapada en esa fecha y horario
	 */
	public boolean tieneTareaSolapadaJardinero(String dniJardinero, String fecha, String horaInicio, String horaFin)
			throws SQLException {
		return TareaMantenimientoJdbc.tieneTareaSolapada(con, dniJardinero, fecha, horaInicio, horaFin);
	}

	/**
	 * Registra una tarea de mantenimiento y vincula a los jardineros asignados de forma transaccional
	 */
	public void registrarTareaMantenimiento(TareaMantenimiento tarea, List<String> dnisJardineros) throws SQLException {
		TareaMantenimientoJdbc.registrarTareaMantenimiento(con, tarea, dnisJardineros);
	}

	/**
	 * Obtiene las tareas de mantenimiento de una instalación en una fecha determinada
	 */
	public List<TareaMantenimiento> getTareasInstalacionFecha(int idInstalacion, String fecha) throws SQLException {
		return TareaMantenimientoJdbc.getTareasInstalacionFecha(con, idInstalacion, fecha);
	}

    /**
     * Devuelve la lista de equipos externos rivales (visitantes)
     */
    public List<Equipo> getEquiposVisitantes() throws SQLException {
        return EquipoJdbc.getEquiposExternos(con);
    }
    
    /**
     * Devuelve la lista de equipos propios de la organización (locales)
     */
    public List<Equipo> getEquiposPropios() throws SQLException {
        return EquipoJdbc.getEquiposPropios(con);
    }
    
    /**
	 * Comprueba colisiones de horario para un partido en la instalación y fecha.
	 */
	public void comprobarColisionesPartido(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, int idInstalacion)
			throws SQLException {
		EquipoJdbc.comprobarColisionesPartido(con, fecha, horaInicio, horaFin, idInstalacion);
	}
	/**
	 * Obtiene los eventos sobreescribibles de menor prioridad que se solapan con el horario del partido.
	 */
	public List<EventoOcupacion> obtenerSobreescribiblesPartidoSolapados(int idInstalacion,
			LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) throws SQLException {
		return EquipoJdbc.obtenerSobreescribiblesPartidoSolapados(con, idInstalacion, fecha, horaInicio, horaFin);
	}
	/**
	 * Registra el partido, sus precios de sección y cancela atómicamente los eventos sobreescritos.
	 */
	public void guardarPartidoCancelandoCoincidentes(int idLocal, int idVisitante, int idInstalacion, LocalDate fecha,
			LocalTime horaInicio, LocalTime horaFin, boolean esDiaClub, double precioAbonado,
			Map<TipoSeccion, Double> preciosSeccion, List<EventoOcupacion> cancelables)
			throws SQLException {
		EquipoJdbc.guardarPartidoCancelandoCoincidentes(con, idLocal, idVisitante, idInstalacion, fecha, horaInicio,
				horaFin, esDiaClub, precioAbonado, preciosSeccion, cancelables);
	}
	/**
	 * Obtiene todos los eventos de ocupación para la fecha e instalación (para rellenar la tabla de la UI).
	 */
	public List<EventoOcupacion> obtenerEventosOcupacionPartido(int idInstalacion, LocalDate fecha)
			throws SQLException {
		return EquipoJdbc.obtenerEventosOcupacionPartido(con, idInstalacion, fecha);
	}
}
