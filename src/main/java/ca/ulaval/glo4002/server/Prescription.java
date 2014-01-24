package ca.ulaval.glo4002.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import erreurs.FormatDeDateNonValide;

public class Prescription {
	// TODO refactor idMax dans l<archive
	private static int idMax = 0;
	private int id;
	private Medicament medicament;
	private int renouvellement = -1;
	private Date date;
	private Staff intervenant;
	private Patient patient;
	private boolean isValid = false;

	public Prescription(Medicament medicament, Patient patient, Staff intervenant) {
		this.id = idMax;
		this.medicament = medicament;
		this.patient = patient;
		this.intervenant = intervenant;
		idMax++;
	}

	public int getId() {
		return id;
	}

	public boolean getValid() {
		return isValid;
	}

	public void setRenouvellement(int renouvellement) {
		this.renouvellement = renouvellement;
		estValide();
	}

	public void setDate(String date) throws FormatDeDateNonValide, ParseException {
		if (verifieDate(date)) {
			this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} else {
			throw new FormatDeDateNonValide();
		}
		estValide();
	}

	private boolean verifieDate(String laDate) {
		String regexDeValidationDeDate = "((?:2|1)\\d{3}(?:-|\\/)(?:(?:0[1-9])|(?:1[0-2]))(?:-|\\/)(?:(?:0[1-9])|(?:[1-2][0-9])|(?:3[0-1]))(?:T|\\s)(?:(?:[0-1][0-9])|(?:2[0-3])):(?:[0-5][0-9]):(?:[0-5][0-9]))";
		System.out.println(laDate);
		if (laDate.matches(regexDeValidationDeDate))
			return true;
		else
			return false;
	}

	private void estValide() {
		if ((renouvellement >= 0) && (date != null)) {
			this.isValid = true;
		}
	}

}
