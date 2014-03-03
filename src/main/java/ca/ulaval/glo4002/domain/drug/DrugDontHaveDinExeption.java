package ca.ulaval.glo4002.domain.drug;

public class DrugDontHaveDinExeption extends Exception {

	private static final long serialVersionUID = 3424074651042879554L;

	public DrugDontHaveDinExeption() {
		super();
	}

	public DrugDontHaveDinExeption(String message) {
		super(message);
	}
}
