package ca.ulaval.glo4002.domain.patient;

public class PatientExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 2411650399227995793L;

	public PatientExistsException(String message, RuntimeException e) {
		super(message, e);
	}
}
