package ca.ulaval.glo4002.exceptions;

public class InvalidDateFormatException extends Exception {

        private static final long serialVersionUID = -5344432952859944672L;

	public InvalidDateFormatException() {
		super();
	}

	public InvalidDateFormatException(String message) {
		super(message);
	}
}
