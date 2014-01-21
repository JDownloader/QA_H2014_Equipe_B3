package ca.ulaval.glo4002.server;

import java.util.Date;

public class Prescription {
	// TODO refactor idMax dans l<archive
	private static int idMax = 0;
	private int id;
	private Medicament medicament;
	private int renouvellement;
	private Date date;
	private Intervenant intervenant;
	private Patient patient;
	private boolean isValid = false;

	public Prescription() {
		id = idMax;
		idMax++;
	}

	public int getId() {
		return id;
	}

	public boolean getValid() {
		return isValid;
	}

}
