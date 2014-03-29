package ca.ulaval.glo4002.domain.drug;

import javax.persistence.Entity;

@Entity
public class DrugWithNoDin extends Drug {

	protected DrugWithNoDin() {
		// Required for Hibernate.
	}

	public DrugWithNoDin(String name) {
		this.name = name;
	}

	@Override
	public Din getDin() throws NoSuchFieldException {
		throw new NoSuchFieldException();
	}

}
