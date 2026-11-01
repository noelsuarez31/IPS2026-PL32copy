package giis.demo.exceptions;

public class AbonoNoPermitidoException extends Exception{

	private static final long serialVersionUID = 1L;

	public AbonoNoPermitidoException(String mensaje) {
		super(mensaje);
	}
}
