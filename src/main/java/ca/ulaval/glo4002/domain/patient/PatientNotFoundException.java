package ca.ulaval.glo4002.domain.patient;

public class PatientNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 5500330764535025554L;

	public PatientNotFoundException(String message) {
		super(message);
	}
}
