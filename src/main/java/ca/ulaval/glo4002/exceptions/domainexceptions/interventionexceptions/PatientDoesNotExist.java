package ca.ulaval.glo4002.exceptions.domainexceptions.interventionexceptions;

import ca.ulaval.glo4002.exceptions.domainexceptions.DomainException;

public class PatientDoesNotExist extends DomainException {
	private static final long serialVersionUID = -6629525255338408139L;
	
	public PatientDoesNotExist() { 
		this.code = "INT002";
		this.message = "Erreur - Patient inexistant";
	}
}
