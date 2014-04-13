package ca.ulaval.glo4002.services.dto.validators;

public class DTOValidationException extends RuntimeException {

	private static final long serialVersionUID = 8033406196386609639L;

	public DTOValidationException() {
		super();
	}

	public DTOValidationException(String message) {
		super(message);
	}
}
