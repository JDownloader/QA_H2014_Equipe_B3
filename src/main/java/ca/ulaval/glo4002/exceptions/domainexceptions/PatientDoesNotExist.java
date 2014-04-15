package ca.ulaval.glo4002.exceptions.domainexceptions;


public class PatientDoesNotExist extends DomainException {
	private static final long serialVersionUID = -6629525255338408139L;
	
	public PatientDoesNotExist(String code, String message) { 
		this.code = code;
		this.message = message;
	}
}
