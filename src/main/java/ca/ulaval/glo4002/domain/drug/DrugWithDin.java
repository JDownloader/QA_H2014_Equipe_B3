package ca.ulaval.glo4002.domain.drug;

import javax.persistence.Entity;

@Entity
public class DrugWithDin extends Drug {

	protected DrugWithDin() {
		//Required for Hibernate.
	}
	
	public DrugWithDin(Din din, String name) {
		this.din = din;
		this.name = name;
	}
	
	public DrugWithDin(Din din, String name, String description) {
		this.din = din;
		this.name=name;
		this.description = description;
	}
}
