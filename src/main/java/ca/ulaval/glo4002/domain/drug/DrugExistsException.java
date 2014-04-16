package ca.ulaval.glo4002.domain.drug;

public class DrugExistsException extends RuntimeException {

	private static final long serialVersionUID = -571882379379142939L;

	public DrugExistsException(String message, RuntimeException e) {
		super(message, e);
	}
}
