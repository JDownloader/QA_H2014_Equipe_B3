package ca.ulaval.glo4002.drug;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "DRUG")
public class Drug {

	private static final int NULL_DIN = 0;

	@Id
	@Column(name = "DIN")
	private int din;

	@Column(name = "DRUG_NAME")
	private String name;

	// Deux constructeurs: un avec le din et un avec le nom
	public Drug(int din, String name) {

		this.din = din;
		this.name = name;
	}

	public Drug(String name) {
		this(NULL_DIN, name);
	}

	public int getDin() {
		return this.din;
	}

	public String getName() {
		return this.name;
	}
}
