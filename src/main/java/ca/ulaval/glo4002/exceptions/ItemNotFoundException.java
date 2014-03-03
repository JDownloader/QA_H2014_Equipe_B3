package ca.ulaval.glo4002.exceptions;

public class ItemNotFoundException extends Exception {

	private static final long serialVersionUID = 4384219908709509673L;

	public ItemNotFoundException() {
		super();
	}

	public ItemNotFoundException(String message) {
		super(message);
	}
}
