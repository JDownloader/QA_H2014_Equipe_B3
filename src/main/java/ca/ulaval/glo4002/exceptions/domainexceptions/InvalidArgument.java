package ca.ulaval.glo4002.exceptions.domainexceptions;


public class InvalidArgument extends DomainException {
	private static final long serialVersionUID = 1408594328944660822L;
	
	public InvalidArgument() {
		this.code = "INT001";
		this.message = "Erreur - informations manquantes ou invalides";
	}
}
