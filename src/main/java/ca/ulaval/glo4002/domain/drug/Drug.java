package ca.ulaval.glo4002.domain.drug;

import java.util.HashSet;
import java.util.Set;

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
	@ElementCollection
	private Set<Integer> test = new HashSet<Integer>();
	@Transient
	private Set<Din> interactiveDins;

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
		return isDrugForwardInteractive(drug) && isDrugBackwardInteractive(drug);
	}
	
	private boolean isDrugForwardInteractive(Drug drug) {
		return interactiveDins.contains(drug);
	}
	
	private boolean isDrugBackwardInteractive(Drug drug) {
		for (Din interactiveDin : interactiveDins) {
			if (interactiveDin == drug.getDin()) {
				return true;
			}
		}
		
		return false;
	}
}
