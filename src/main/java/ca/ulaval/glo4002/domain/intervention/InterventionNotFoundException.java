package ca.ulaval.glo4002.domain.intervention;

public class InterventionNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -4626854299512690381L;
	
	public InterventionNotFoundException(String message) {
		super(message);
	}
}
