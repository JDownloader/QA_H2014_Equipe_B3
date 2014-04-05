package ca.ulaval.glo4002.exceptions.domainexceptions;

public abstract class DomainException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}

}
