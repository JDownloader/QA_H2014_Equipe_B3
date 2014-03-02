package ca.ulaval.glo4002.domain.drug;

import javax.persistence.*;

@Entity(name = "DRUG")
@Table(name="DRUG", indexes = {@Index(name="NAME_IDX", columnList="NAME")})
public class Drug {
	@Id
	@Column(name = "DIN")
	private Din din = null;

	@Column(name = "NAME")
	private String name = "";
	
	@Column(name = "DESCRIPTION")
	private String description = "";
	
	protected Drug() {
		//Required for Hibernate.
	}

	public Drug(DrugBuilder builder) {
		this.din = builder.din;
		this.name = builder.name;
		this.description = builder.description;
	}
	
	public Din getDin() {
		return this.din;
	}

	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
}
