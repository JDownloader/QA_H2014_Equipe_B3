package ca.ulaval.glo4002.exceptions;

public class BadRequestException extends Exception {

	private static final long serialVersionUID = -4693484254820695541L;
	private String internalCode = "";
	
	public BadRequestException(String internalCode, String message) {
		super(message);
		this.internalCode = internalCode;
	}
	
	public BadRequestException(String internalCode) {
		super();
		this.internalCode = internalCode;
	}
	
	public BadRequestException() {
		super();
	}
	
	public String getInternalCode() {
		return internalCode;
	}
}
