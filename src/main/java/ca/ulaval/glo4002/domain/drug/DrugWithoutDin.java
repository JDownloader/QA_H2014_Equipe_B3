package ca.ulaval.glo4002.domain.drug;

import javax.persistence.Entity;

@Entity
public class DrugWithoutDin extends Drug {

	protected DrugWithoutDin() {
		// Required for Hibernate.
	}

	public DrugWithoutDin(String name) {
		this.name = name;
	}

	@Override
	public Din getDin() throws DrugDoesNotContainDinException {
		throw new DrugDoesNotContainDinException();
	}

}
