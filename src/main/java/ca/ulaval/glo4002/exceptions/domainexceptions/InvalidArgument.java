package ca.ulaval.glo4002.exceptions.domainexceptions;


public class InvalidArgument extends DomainException {
	private static final long serialVersionUID = 1408594328944660822L;
	
	public InvalidArgument(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
