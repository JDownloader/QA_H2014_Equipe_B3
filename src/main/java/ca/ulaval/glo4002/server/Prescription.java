package ca.ulaval.glo4002.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ca.ulaval.glo4002.error.FormatDeDateNonValide;
import ca.ulaval.glo4002.server.Medicament;
import ca.ulaval.glo4002.server.Staff;

/* CODE REVIEW 25/01/2014
 * - "renouvellement = -1": Magic number... Je définirais la constante suivante: UNSPECIFIED = -1
 * - Je pense que la validation du format de date devrait se faire au niveau du Servlet par une fonction réutilisable, parce que c'est surement pas la seule date qu'on va avoir à valider dans les user stories.
 * - Toujours dans le même sens, setDate devrait prendre une Date et non un String.
 * - Le terme isActive serait à mon avis plus approprié que isValid, étant donnée que cet attribut est utilisé pour savoir si la prescription est expirée ou pas... Techniquement parlant, l'état de prescription devrait toujours être valide (sans être nécessairement être actif).
 * 
 * - Olivier R
 */

public class Prescription {
	// TODO refactor idMax dans l<archive
	private static int idMax = 0;
	private int id;
	private Medicament medicament;
	private int renouvellement = -1;
	private Date date;
	private Staff intervenant;
	private boolean isValid = false;

	public Prescription(Medicament medicament, Staff intervenant) {
		incrementAutoId();
		this.medicament = medicament;
		this.intervenant = intervenant;
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

	private void incrementAutoId() {
		this.id = idMax;
		idMax++;
	}

}
