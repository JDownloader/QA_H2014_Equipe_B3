package ca.ulaval.glo4002.domain.surgicaltool;

public class SurgicalToolNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 7143798799655127089L;

	public SurgicalToolNotFoundException(String message) {
		super(message);
	}
	
	public SurgicalToolNotFoundException(String message, RuntimeException e) {
		super(message, e);
	}
}
