package ca.ulaval.glo4002.drug;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "DRUG")
public class Drug {
	private static int idMax = 0;

	private static final int NULL_DIN = 0;

	@Id
	@Column(name = "DRUG_ID", nullable = false)
	private int id;

	@Column(name = "DIN", nullable = false)
	private int din;

	@Column(name = "DRUG_NAME", nullable = false)
	private String nom;

	public Drug(int din, String nom) {
		incrementAutoId();

		this.din = din;
		this.nom = nom;
	}

	public Drug(String nom) {
		this(NULL_DIN, nom);
	}

	private void incrementAutoId() {
		id = idMax;
		idMax++;
	}

	public int getId() {
		return this.id;
	}

	public int getDin() {
		return this.din;
	}

	public String getNom() {
		return this.nom;
	}
}
