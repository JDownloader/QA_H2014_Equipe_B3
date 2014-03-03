package ca.ulaval.glo4002.domain.drug;

import javax.persistence.Entity;

@Entity
public class DrugWithoutDin extends Drug {

	public DrugWithoutDin(String name) {
		this.name=name;
	}
	
	@Override
	public Din getDin() throws DrugDoesntHaveDinException {
		throw new DrugDoesntHaveDinException();
	}

}
