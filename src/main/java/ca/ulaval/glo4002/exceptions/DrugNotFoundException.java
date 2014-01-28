package ca.ulaval.glo4002.exceptions;

public class DrugNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	 
	public DrugNotFoundException() {
		super();
	}

	public DrugNotFoundException(String message) {
		super(message);
	}
}
