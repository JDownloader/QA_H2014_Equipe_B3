package ca.ulaval.glo4002.server;

public class Medicament {
    private static int idMax = 0;

    private static final int NULL_DIN = 0;

    private int id;
    private int din;
    private String nom;

    public Medicament(int din, String nom) {
	incrementAutoId();

	this.din = din;
	this.nom = nom;
    }

    public Medicament(String nom) {
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
