package ca.ulaval.glo4002.domain.drug;

public class DrugDoesntHaveDinExeption extends Exception {

	private static final long serialVersionUID = 3424074651042879554L;

	public DrugDoesntHaveDinExeption() {
		super();
	}

	public DrugDoesntHaveDinExeption(String message) {
		super(message);
	}
}
