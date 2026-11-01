package giis.demo.model.abonos;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import giis.demo.exceptions.AbonoNoPermitidoException;
import giis.demo.jdbc.JDBC;
import giis.demo.model.abonos.Abono.Categoria;
import giis.demo.model.entradas.enumerados.TipoSeccion;
import giis.demo.model.entradas.enumerados.TipoTribuna;
import giis.demo.model.equipo.Equipo;

public class VentaDeAbonos extends Venta{
	
	private static final int EDAD_MAX_INFANTIL = 17;
	private static final int EDAD_MAX_SUB26 = 25;
	private static final int EDAD_MIN_JUBILADO = 65;

	private JDBC jdbc;

	/**
	 * Constructor de la clase por defecto
	 */
	public VentaDeAbonos(){
		
		jdbc = new JDBC();

		
	}
	
	/**
	 * Devuelve el precio según una categoría
	 * 
	 * @param categoria
	 * @return el precio
	 */
	public double getPrecio(Categoria categoria) {
		
		switch (categoria) {
			
		case INFANTIL:
			return 100;
		case SUB_26:
			return 200;
		case JUBILADO:
			return 250;
		default:
			return 400;
		}
		
	}
	
	/**
	 * Calcula la categoria del abonado en funcion de su fecha de nacimiento
	 * @param fechaNacimiento
	 * @return
	 */
	public Categoria calcularCategoria(LocalDate fechaNacimiento) {
		
		LocalDate fechaActual = LocalDate.now();
		
		if(fechaNacimiento == null || fechaNacimiento.isAfter(fechaActual)) {
			throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula ni posterior a la fecha actual");
		}
		
		int edad = java.time.Period.between(fechaNacimiento, fechaActual).getYears();
		
		return getCategoria(edad);
		
	}
	
	/**
	 * Metodo privado que devuelve la categoria del abonado en funcion de su edad
	 * @param edad
	 * @return
	 */
	private Categoria getCategoria(int edad) {
		
		if(edad <= EDAD_MAX_INFANTIL) {
			return Categoria.INFANTIL;
		}else if(edad <= EDAD_MAX_SUB26) {
			return Categoria.SUB_26;
		}else if(edad >= EDAD_MIN_JUBILADO) {
			return Categoria.JUBILADO;
		}else {
			return Categoria.ADULTO;
		}
		
	}
	
	/**
	 * Obtiene los asientos ocupados para un equipo, una tribuna y seccion dadas
	 * 
	 * @param equipo
	 * @param tribuna
	 * @param tipoSeccion
	 * @return una lista con los asientos ocupados
	 * @throws SQLException
	 */
	public List<int[]> getAsientosOcupados(Equipo equipo, TipoTribuna tribuna, TipoSeccion tipoSeccion, LocalDate inicio, LocalDate fin) throws SQLException {
		
		return jdbc.getAsientosOcupados(equipo.getIdEquipo(), tribuna.name(), tipoSeccion.name(), inicio, fin);
		
	}
	
	/**
	 * Devuelve los equipos locales
	 * 
	 * @return una lista con los equipos locales
	 * @throws SQLException
	 */
	public Equipo[] getEquiposLocales() throws SQLException {

		return jdbc.getEquiposLocales();
		
	}

	/**
	 * Calcula la fecha de inicio del bono de temporada
	 * en función de cuando se compre
	 * @param hoy
	 * @return
	 */
	private LocalDate inicioTemporada(LocalDate hoy) {
		
		int anio;
		
		if(hoy.getMonthValue()>=7) {
			
			anio = hoy.getYear();
		} else {
			
			anio = hoy.getYear()-1;
		}
		
		return LocalDate.of(anio, 7, 1);
		
	}
	
	/**
	 * Comprueba si un usuario ya tiene comprado un aboo esta temporada
	 * @param dni
	 * @return true si lo tiene comprado, false en caso contrario
	 * @throws SQLException
	 */
	public boolean tieneAbonoEstaTemporada(String dni) throws SQLException {
		
		LocalDate fechaInicio = inicioTemporada(LocalDate.now());
		
		return jdbc.tieneAbonoEstaTemporada(dni, fechaInicio);
		
	}
	
	/**
	 * Obtiene la informacion de los abonos
	 * 
	 * @param dni
	 * @return el abono
	 * @throws SQLException
	 */
	public Abono getInfoAbono(String dni) throws SQLException {
		
		LocalDate fechaInicio = inicioTemporada(LocalDate.now());

		return jdbc.getInfoAbono(dni, fechaInicio);
		
	}
	
	/**
	 * Inserta el abonado en la BBDD
	 * 
	 * @param abonado
	 * @param abono
	 * @throws SQLException
	 */
	public void insertarAbonado(Abonado abonado, Abono abono) throws SQLException {
		
		jdbc.insertarAbonado(abonado, abono);
		
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
		
		jdbc.venderAbono(abonado, abono, idEquipo);
		
	}
	
	/**
	 * Cierra la conexión con la BBDD
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		
		jdbc.close();
		
	}
	
	
}
