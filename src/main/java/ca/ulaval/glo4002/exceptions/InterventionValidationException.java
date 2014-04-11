package ca.ulaval.glo4002.exceptions;

public class InterventionValidationException extends Exception{

	private static final long serialVersionUID = 3458211491581297623L;
	
	public InterventionValidationException() {
		super();
	}

	public InterventionValidationException(String message) {
		super(message);
	}
}
