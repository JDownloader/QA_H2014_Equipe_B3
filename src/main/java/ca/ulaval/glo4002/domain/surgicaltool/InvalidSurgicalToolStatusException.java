package ca.ulaval.glo4002.domain.surgicaltool;

public class InvalidSurgicalToolStatusException extends RuntimeException {

	private static final long serialVersionUID = 7350706772667479157L;
	
	public InvalidSurgicalToolStatusException(String message) {
		super(message);
	}
}
