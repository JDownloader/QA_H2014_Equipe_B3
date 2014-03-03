package ca.ulaval.glo4002.domain.drug;

import javax.persistence.*;

@Entity(name = "DRUG")
@Table(name = "DRUG", indexes = { @Index(name = "NAME_IDX", columnList = "NAME") })
public abstract class Drug {
	@Id
	@Column(name = "DIN")
	protected Din din = null;

	@Column(name = "NAME")
	protected String name = "";

	@Column(name = "DESCRIPTION")
	protected String description = "";

	protected Drug() {
		// Required for Hibernate.
	}

	public Din getDin() throws DrugDoesNotContainDinException {
		return this.din;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

}
