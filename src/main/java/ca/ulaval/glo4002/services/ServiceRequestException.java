package ca.ulaval.glo4002.services;

public class ServiceRequestException extends RuntimeException {

	private static final long serialVersionUID = -4693484254820695541L;

	private String internalCode = "";

	public ServiceRequestException(String internalCode, String message) {
		super(message);
		this.internalCode = internalCode;
	}

	public ServiceRequestException() {
		super();
	}

	public String getInternalCode() {
		return internalCode;
	}
}
