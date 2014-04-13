package ca.ulaval.glo4002.domain.surgicaltool;

public class SurgicalToolRequiresSerialNumberException extends RuntimeException {
	private static final long serialVersionUID = -6445748524540980720L;
	
	public SurgicalToolRequiresSerialNumberException(String message) {
		super(message);
	}
}
