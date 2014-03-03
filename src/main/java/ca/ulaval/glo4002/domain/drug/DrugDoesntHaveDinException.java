package ca.ulaval.glo4002.domain.drug;

public class DrugDoesntHaveDinException extends Exception {

	private static final long serialVersionUID = 3424074651042879554L;

	public DrugDoesntHaveDinException() {
		super();
	}

	public DrugDoesntHaveDinException(String message) {
		super(message);
	}
}
