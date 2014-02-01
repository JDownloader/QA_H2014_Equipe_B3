package ca.ulaval.glo4002.exceptions;

public class InterventionNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	 
	public InterventionNotFoundException() {
		super();
	}

	public InterventionNotFoundException(String message) {
		super(message);
	}
}
