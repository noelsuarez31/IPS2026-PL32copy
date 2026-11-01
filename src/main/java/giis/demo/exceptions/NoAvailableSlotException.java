package giis.demo.exceptions;

// SE COMPRUEBA
public class NoAvailableSlotException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoAvailableSlotException(String msg) {
		super(msg);
	}
}
