package ca.ulaval.glo4002.services.dto.validators;

public class PrescriptionCreationException extends RuntimeException {
	
	private static final long serialVersionUID = -8078093108091537721L;

	public PrescriptionCreationException() {
		super();
	}
	
	public PrescriptionCreationException(String message) {
		super(message);
	}
}
