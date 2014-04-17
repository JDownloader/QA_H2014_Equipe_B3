package ca.ulaval.glo4002.domain.surgicaltool;

public class SurgicalToolExistsException extends RuntimeException {
	private static final long serialVersionUID = 2765409238503512751L;
	
	public SurgicalToolExistsException() {
		super();
	}
	
	public SurgicalToolExistsException(String message, RuntimeException e) {
		super(message, e);
	}
}
