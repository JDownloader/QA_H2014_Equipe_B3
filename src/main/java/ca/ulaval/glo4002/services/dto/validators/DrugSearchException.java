package ca.ulaval.glo4002.services.dto.validators;

public class DrugSearchException extends RuntimeException {

	private static final long serialVersionUID = -8241484806743744792L;

	public DrugSearchException() {
		super();
	}
	
	public DrugSearchException(String message) {
		super(message);
	}
}
