package ca.ulaval.glo4002.domain.drug;

import javax.persistence.*;

import org.codehaus.jackson.annotate.*;

@Entity(name = "DRUG")
@Table(name = "DRUG", indexes = { @Index(name = "NAME_IDX", columnList = "NAME") })
public class Drug {
	@Id
	private Din din;
	@JsonProperty("nom")
	private String name;
	private String description;

	protected Drug() {
		// Required for Hibernate.
	}
	
	public Drug(Din din, String name, String description) {
		this.din = din;
		this.name = name;
		this.description = description;
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
