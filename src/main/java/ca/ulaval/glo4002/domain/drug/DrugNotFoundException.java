package ca.ulaval.glo4002.domain.drug;

public class DrugNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 5500330764535025554L;
	
	public DrugNotFoundException() {
		super();
	}
	
	public DrugNotFoundException(String message) {
		super(message);
	}
}
