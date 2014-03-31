package ca.ulaval.glo4002.contexts;

public class BadFileFormatException extends Exception {

	private static final long serialVersionUID = -2951411004604895487L;

	public BadFileFormatException() {
		super();
	}

	public BadFileFormatException(String message) {
		super(message);
	}
}
