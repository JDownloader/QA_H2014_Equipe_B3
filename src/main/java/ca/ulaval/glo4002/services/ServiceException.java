package ca.ulaval.glo4002.services;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -4693484254820695541L;

	private String internalCode = "";

	public ServiceException(String internalCode, String message) {
		super(message);
		this.internalCode = internalCode;
	}

	public ServiceException() {
		super();
	}

	public String getInternalCode() {
		return internalCode;
	}
}
