package giis.demo.model.entradas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import giis.demo.jdbc.JDBC;
import giis.demo.model.abonos.Venta;
import giis.demo.model.entradas.enumerados.TipoSeccion;
import giis.demo.model.entradas.enumerados.TipoTribuna;
import giis.demo.exceptions.SinDisponibilidadException;

public class VentaDeEntradas extends Venta{

	public static final int MAX_ENTRADAS = 15;
	public static final int PRICE_ENTRADAS = 30;

	private Random random;

	private int idPartido;
	private List<Butaca> butacasSeleccionadas;
	private List<Butaca> butacasDisponibles;
	private JDBC jdbc;

	/**
	 * Constructor por defecto de la clase
	 */
	public VentaDeEntradas() {

		random = new Random();
		jdbc = new JDBC();
		butacasSeleccionadas = new ArrayList<Butaca>();

	}
	
	public void reiniciar() {
		
		butacasSeleccionadas.clear();
		butacasDisponibles.clear();
		
	}

	/**
	 * Obtiene las butacas disponibles y selecciona el numero de butacas indicadas
	 * si es posible en la tribuna y seccion dadas y para un partido en concreto
	 * ayudandose de un metodo auxiliar
	 * 
	 * @param partido
	 * @param tribuna
	 * @param seccion
	 * @param numEntradas
	 * @return las butacas seleccionadas según los criterios dados
	 * @throws SQLException
	 * @throws SinDisponibilidadException
	 */
	public List<Butaca> seleccionar(Partido partido, TipoTribuna tribuna, TipoSeccion seccion, int numEntradas)
			throws SQLException, SinDisponibilidadException {

		this.idPartido = partido.getId_partido();

		if (numEntradas > MAX_ENTRADAS || numEntradas <= 0) {
			throw new IllegalArgumentException("No se pueden comprar más de 15 entradas");
		}

		butacasDisponibles = getButcasLibres(partido, tribuna, seccion);

		butacasSeleccionadas = seleccionarAsientosContiguos(butacasDisponibles, numEntradas);

		return butacasSeleccionadas;

	}

	/**
	 * Obtiene las butacas disponibles y selecciona el numero de butacas indicadas
	 * si es posible en la tribuna y seccion dadas y para un partido en concreto
	 * ayudandose de un metodo auxiliar
	 * 
	 * @param partido
	 * @param tribuna
	 * @param seccion
	 * @param numEntradas
	 * @return las butacas seleccionadas según los criterios dados
	 * @throws SQLException
	 * @throws SinDisponibilidadException
	 */
	public List<Butaca> getButcasLibres(Partido partido, TipoTribuna tribuna, TipoSeccion seccion) throws SQLException {

		this.idPartido = partido.getId_partido();
		butacasDisponibles = jdbc.getFreeButacas(partido.getId_partido(), tribuna, seccion);

		return butacasDisponibles;

	}

	/**
	 * Devuelve el id del partido en el que se realiza la venta
	 * 
	 * @return el id del partido en el que se realiza la venta
	 */
	public int getIdPartido() {
		return idPartido;
	}

	/**
	 * Devuelve las butacas reservadas asociadas a la venta
	 * 
	 * @return las butacas reservadas asociadas a la venta
	 */
	public List<Butaca> getButacasSeleccionadas() {
		return butacasSeleccionadas;
	}

	/**
	 * Selecciona las butacas contiguas indicadas en una fila aleatoria
	 * 
	 * @param butacasDisponibles
	 * @param numEntradas
	 * @return las butacas contiguas indicadas en una fila aleatoria
	 * @throws SinDisponibilidadException si no hay suficientes butacas
	 */
	private List<Butaca> seleccionarAsientosContiguos(List<Butaca> butacasDisponibles, int numEntradas)
			throws SinDisponibilidadException {

		List<Integer> posicionButacasDisponibles = new ArrayList<Integer>();
		List<Butaca> butacasReservadas = new ArrayList<Butaca>();

		int contadorAsientos = 0;

		for (int i = 0; i < butacasDisponibles.size(); i++) {

			if (i > 0 && isNextOcuppied(butacasDisponibles.get(i - 1), butacasDisponibles.get(i))) {
				contadorAsientos++;
			} else {
				contadorAsientos = 1;
			}

			if (contadorAsientos >= numEntradas) {
				posicionButacasDisponibles.add(i - numEntradas + 1);
			}

		}

		if (posicionButacasDisponibles.size() == 0) {
			throw new SinDisponibilidadException("No existe ninguna fila con " + numEntradas + " asientos contiguos");
		}

		int index = posicionButacasDisponibles.get(random.nextInt(posicionButacasDisponibles.size()));

		for (int i = 0; i < numEntradas; i++) {
			butacasReservadas.add(butacasDisponibles.get(index + i));
		}

		return butacasReservadas;
	}

	/**
	 * Método auxiliar para comprobar si dos columnas son contiguas
	 * 
	 * @param anterior
	 * @param actual
	 * @return true si las dos butacas son contiguas, false en caso contrario
	 */
	private boolean isNextOcuppied(Butaca anterior, Butaca actual) {

		return anterior.getFila() == actual.getFila() && actual.getAsiento() == anterior.getAsiento() + 1;

	}

	/**
	 * Los ditintos partidos disponibles
	 * 
	 * @return los partidos disponibles
	 * @throws SQLException
	 */
	public Partido[] getPartidos() throws SQLException {

		Partido[] partido = jdbc.getPartidos().toArray(new Partido[0]);
		return partido;

	}

	public void almacenar(int id_Partido, List<Butaca> butacas_Seleccionadas) throws SQLException {

		jdbc.almacenar(id_Partido, butacas_Seleccionadas);

	}

	public void close() throws SQLException {

		jdbc.close();

	}

	/**
	 * Comprueba si una butaca está ocupada o está libre
	 * 
	 * @param id_partido
	 * @param tipoTribuna
	 * @param tipoSeccion
	 * @param fila
	 * @param asiento
	 * @return true si una butaca está libre, false en caso contrario
	 */
	public boolean isOccupied(int id_partido, TipoTribuna tipoTribuna, TipoSeccion tipoSeccion, int fila, int asiento) {

		Butaca butaca = new Butaca(id_partido, tipoTribuna, tipoSeccion, fila, asiento);

		return !butacasDisponibles.contains(butaca);

	}

	/**
	 * Comprueba si el asiento está reservado
	 * 
	 * @param id_partido
	 * @param tipoTribuna
	 * @param tipoSeccion
	 * @param fila
	 * @param asiento
	 * @return true si el asiento está reservado pero no ha sido comprado aún, false en caso contrario
	 */
	public boolean isBooked(int id_partido, TipoTribuna tipoTribuna, TipoSeccion tipoSeccion, int fila, int asiento) {

		Butaca butaca = new Butaca(id_partido, tipoTribuna, tipoSeccion, fila, asiento);

		return butacasSeleccionadas.contains(butaca);

	}
	
	/**
	 * Dada una butaca seleccionada de forma manual, la añade a la lista
	 * de bustacas seleccionadas
	 * 
	 * @param partido
	 * @param tribuna
	 * @param seccion
	 * @param fila
	 * @param asiento
	 * 
	 * @throws SinDisponibilidadException
	 */
	public void seleccionarAsientoManual(Partido partido, TipoTribuna tribuna, TipoSeccion seccion,
	        int fila, int asiento) throws SinDisponibilidadException {

	    this.idPartido = partido.getId_partido();
	    
	    Butaca butacaSeleccionada = null;
	    
	    for(Butaca butaca : butacasDisponibles) {
	    	
	    	if(butaca.getFila() == fila && butaca.getAsiento()==asiento) {
	    		
	    		butacaSeleccionada = butaca;
	    		break;
	    		
	    	}
	    	
	    }
	    
	    if (butacaSeleccionada == null) {
	        throw new SinDisponibilidadException(
	                "El asiento seleccionado no está disponible");
	    }

	    butacasSeleccionadas = new ArrayList<>();
	    butacasSeleccionadas.add(butacaSeleccionada);
	    
	}
	
	/**
	 * Calcula el precio de un asiento para una sección y partido dados
	 * 
	 * @param idPrecio
	 * @param seccion
	 * @return el precio obtenido
	 * @throws SQLException
	 */
	public double getPrecio(int idPrecio, String seccion) throws SQLException {
		
		return jdbc.obtenerPrecio(idPrecio, seccion);
		
	}

}