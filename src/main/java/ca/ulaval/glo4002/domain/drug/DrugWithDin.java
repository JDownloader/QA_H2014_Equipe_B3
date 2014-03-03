package ca.ulaval.glo4002.domain.drug;

import javax.persistence.*;

@Entity(name = "DRUG")
@Table(name="DRUG", indexes = {@Index(name="NAME_IDX", columnList="NAME")})
public class DrugWithDin implements Drug {
	@Id
	@Column(name = "DIN")
	private Din din = null;

	@Column(name = "NAME")
	private String name = "";

	@Column(name = "DESCRIPTION")
	private String description = "";

	public DrugWithDin(Din din, String name) {
		this.din = din;
		this.name = name;
	}
	
	public DrugWithDin(Din din, String name, String description) {
		this.din = din;
		this.name=name;
		this.description = description;
	}

	@Override
	public Din getDin() {
		return this.din;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
}
