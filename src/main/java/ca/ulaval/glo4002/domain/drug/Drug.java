package ca.ulaval.glo4002.domain.drug;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "DRUG")
public class Drug {
	@Id
	@Column(name = "DIN")
	private Din din = null;

	@Column(name = "DRUG_NAME")
	private String name = null;
	
	protected Drug() {
		//Required for Hibernate.
	}

	public Drug(DrugBuilder builder) {
		this.din = builder.din;
		this.name = builder.name;
	}
	public Din getDin() {
		return this.din;
	}

	public String getName() {
		return this.name;
	}
}
