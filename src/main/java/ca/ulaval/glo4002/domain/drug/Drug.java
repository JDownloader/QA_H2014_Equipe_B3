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
	@JsonProperty("description")
	private String description;

	protected Drug() {
		// Required for Hibernate.
	}
	
	public Din getDin() {
		return din;
	}
	
	public Drug(Din din, String name, String description) {
		this.din = din;
		this.name = name;
		this.description = description;
	}
}
