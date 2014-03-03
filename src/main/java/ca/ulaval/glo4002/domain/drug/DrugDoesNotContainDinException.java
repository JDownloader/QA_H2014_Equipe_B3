package ca.ulaval.glo4002.domain.drug;

public class DrugDoesNotContainDinException extends Exception {

	private static final long serialVersionUID = 3424074651042879554L;

	public DrugDoesNotContainDinException() {
		super();
	}

	public DrugDoesNotContainDinException(String message) {
		super(message);
	}
}
