package ca.ulaval.glo4002.domain.prescription;

public class PrescriptionExistsException extends RuntimeException {

	private static final long serialVersionUID = -3964180840328615911L;

	public PrescriptionExistsException(String message, RuntimeException e) {
		super(message, e);
	}
}
