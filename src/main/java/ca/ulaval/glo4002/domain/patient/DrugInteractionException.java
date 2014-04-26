package ca.ulaval.glo4002.domain.patient;

public class DrugInteractionException extends RuntimeException {
	
	private static final long serialVersionUID = -5247596187546565509L;

	public DrugInteractionException() {
		super();
	}
	
	public DrugInteractionException(String message) {
		super(message);
	}
}
