package ca.ulaval.glo4002.exceptions.domainexceptions.interventionexceptions;

import ca.ulaval.glo4002.exceptions.domainexceptions.DomainException;

public class PatientDoesNotExist extends DomainException {
	private static final long serialVersionUID = -6629525255338408139L;
	
	private String code = "INT002";
	private String message = "Erreur - Patient inexistant";
	
	public PatientDoesNotExist() { }
}
