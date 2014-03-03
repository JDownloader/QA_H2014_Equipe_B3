package ca.ulaval.glo4002.domain.drug;

import javax.persistence.Column;
import javax.persistence.Id;

public interface Drug {
	
	public Din getDin() throws DrugDoesntHaveDinExeption;
	public String getName();
	public String getDescription();
	
}
