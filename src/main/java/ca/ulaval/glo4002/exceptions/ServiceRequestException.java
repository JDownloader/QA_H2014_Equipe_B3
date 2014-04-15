package ca.ulaval.glo4002.exceptions;

//TODO : fix redundant naming. We already knows it's an exception. Might as well name it something like "ServiceFailure".
public class ServiceRequestException extends RuntimeException {

	private static final long serialVersionUID = -4693484254820695541L;

	private String internalCode = "";

	public ServiceRequestException(String internalCode, String message) {
		super(message);
		this.internalCode = internalCode;
	}

	public ServiceRequestException(String internalCode) {
		super();
		this.internalCode = internalCode;
	}

	public ServiceRequestException() {
		super();
	}

	public String getInternalCode() {
		return internalCode;
	}
}
