package ca.ulaval.glo4002.exceptions;

public class SurgicalToolRequiresSerialNumberException extends RuntimeException {

	private static final long serialVersionUID = -6908768509239245962L;

	public SurgicalToolRequiresSerialNumberException() {
		super();
	}

	public SurgicalToolRequiresSerialNumberException(String message) {
		super(message);
	}
}
