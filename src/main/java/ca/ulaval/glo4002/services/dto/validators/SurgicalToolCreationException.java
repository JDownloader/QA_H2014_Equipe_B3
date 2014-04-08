package ca.ulaval.glo4002.services.dto.validators;

public class SurgicalToolCreationException extends RuntimeException {

	private static final long serialVersionUID = 8033406196386609639L;

	public SurgicalToolCreationException() {
		super();
	}

	public SurgicalToolCreationException(String message) {
		super(message);
	}
}
