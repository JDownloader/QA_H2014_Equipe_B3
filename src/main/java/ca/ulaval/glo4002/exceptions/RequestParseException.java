package ca.ulaval.glo4002.exceptions;

public class RequestParseException extends Exception {

	private static final long serialVersionUID = 7868629704965363822L;

	public RequestParseException() {
		super();
	}
	
	public RequestParseException(String message) {
		super(message);
	}
}
