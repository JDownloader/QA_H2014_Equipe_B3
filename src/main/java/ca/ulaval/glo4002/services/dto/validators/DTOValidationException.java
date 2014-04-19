package ca.ulaval.glo4002.services.dto.validators;

public class DTOValidationException extends RuntimeException {

	private static final long serialVersionUID = -8241484806743744792L;

	public DTOValidationException() {
		super();
	}
	
	public DTOValidationException(String message) {
		super(message);
	}
}
