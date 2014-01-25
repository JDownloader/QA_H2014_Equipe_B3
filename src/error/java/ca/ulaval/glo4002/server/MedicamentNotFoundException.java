package ca.ulaval.glo4002.server;

public class MedicamentNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	 
	public MedicamentNotFoundException() {
		super();
	}

	public MedicamentNotFoundException(String message) {
		super(message);
	}
}
