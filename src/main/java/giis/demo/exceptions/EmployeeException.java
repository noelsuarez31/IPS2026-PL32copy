package giis.demo.exceptions;

public class EmployeeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EmployeeException(String msg) {
		super(msg);
	}
}
