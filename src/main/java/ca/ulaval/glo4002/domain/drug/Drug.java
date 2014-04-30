package ca.ulaval.glo4002.domain.drug;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonProperty;

@Entity(name = "DRUG")
@Table(name = "DRUG", indexes = { @Index(name = "NAME_IDX", columnList = "NAME") })
public class Drug {
	@Id
	private Din din;
	@JsonProperty("nom")
	private String name;
	@JsonProperty("description")
	private String description;
	@ElementCollection
	private Set<Din> interactiveDins = new HashSet<Din>();

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
	
	public void setInteractiveDinList(Set<Din> interactiveDins) {
		this.interactiveDins = interactiveDins;
	}
	
	public boolean isDrugInteractive(Drug drug) {
		if (drug != null) {
			return this.interactiveDins.contains(drug.din) || drug.interactiveDins.contains(this.din);
		}
		return false;
	}
}
