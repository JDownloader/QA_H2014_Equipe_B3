package ca.ulaval.glo4002.drug;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "DRUG")
public class Drug {
	// private static int idMax = 0;

	private static final int NULL_DIN = 0;

	/*
	 * @Column(name = "DRUG_ID", nullable = false) private int id;
	 */

	@Id
	@Column(name = "DIN")
	private int din;

	@Column(name = "DRUG_NAME")
	private String name;

	// Deux constructeurs: un avec le din et un avec le nom
	public Drug(int din, String name) {
		// incrementAutoId();

		this.din = din;
		this.name = name;
	}

	public Drug(String name) {
		this(NULL_DIN, name);
	}

	/*
	 * private void incrementAutoId() { id = idMax; idMax++; }
	 * 
	 * public int getId() { return this.id; }
	 */

	public int getDin() {
		return this.din;
	}

	public String getName() {
		return this.name;
	}
}
