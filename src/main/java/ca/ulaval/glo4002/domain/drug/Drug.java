package ca.ulaval.glo4002.domain.drug;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Entity(name = "DRUG")
@Table(name = "DRUG", indexes = { @Index(name = "NAME_IDX", columnList = "NAME") })
public class Drug {
	@Id
	protected Din din;
	@JsonProperty("nom")
	protected String name;
	protected String description;

	protected Drug() {
		// Required for Hibernate.
	}
	
	public Drug(Din din, String name, String description) {
		this.din = din;
		this.name = name;
		this.description = description;
	}

	public Din getDin() throws NoSuchFieldException {
		return this.din;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

}
