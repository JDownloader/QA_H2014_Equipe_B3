package ca.ulaval.glo4002.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ca.ulaval.glo4002.error.FormatDeDateNonValide;

/* CODE REVIEW 25/01/2014
 * - "renouvellement = -1": Magic number... Je définirais la constante suivante: UNSPECIFIED = -1
 * - Je pense que la validation du format de date devrait se faire au niveau du Servlet par une fonction réutilisable, parce que c'est surement pas la seule date qu'on va avoir à valider dans les user stories.
 * - Toujours dans le même sens, setDate devrait prendre une Date et non un String.
 * - Le terme isActive serait à mon avis plus approprié que isValid, étant donnée que cet attribut est utilisé pour savoir si la prescription est expirée ou pas... Techniquement parlant, l'état de prescription devrait toujours être valide (sans être nécessairement être actif).
 * 
 * - Olivier R
 */

@Entity(name = "PRESCRIPTION")
public class Prescription {
	// TODO refactor idMax dans l<archive

	@Transient
	private static int idMax = 0;

	@Id
	@Column(name = "PRES_ID", nullable = false)
	private int id;

	@ManyToOne()
	@ElementCollection(targetClass = Medicament.class)
	@JoinColumn(name = "DRUG", nullable = false)
	private Medicament drug;

	@Column(name = "RENEWAL", nullable = false)
	private int renouvellement = -1;

	@Column(name = "DATE", nullable = false)
	private Date date;

	@ManyToOne()
	@ElementCollection(targetClass = StaffMember.class)
	@JoinColumn(name = "STAFF_MEMBER", nullable = false)
	private StaffMember prescriber;

	@Transient
	private boolean isValid = false;

	public Prescription(Medicament medicament, StaffMember intervenant) {
		incrementAutoId();
		this.drug = medicament;
		this.prescriber = intervenant;
	}

	public int getId() {
		return id;
	}

	public void setRenouvellement(int renouvellement) {
		this.renouvellement = renouvellement;
		calculateValid();
	}

	public void setDate(String date) throws FormatDeDateNonValide,
			ParseException {
		if (verifyDate(date)) {
			this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} else {
			throw new FormatDeDateNonValide();
		}
		calculateValid();
	}

	private boolean verifyDate(String laDate) {
		String regexDeValidationDeDate = "((?:2|1)\\d{3}(?:-|\\/)(?:(?:0[1-9])|(?:1[0-2]))(?:-|\\/)(?:(?:0[1-9])|(?:[1-2][0-9])|(?:3[0-1]))(?:T|\\s)(?:(?:[0-1][0-9])|(?:2[0-3])):(?:[0-5][0-9]):(?:[0-5][0-9]))";
		System.out.println(laDate);
		if (laDate.matches(regexDeValidationDeDate))
			return true;
		else
			return false;
	}

	private void calculateValid(){
		if ((renouvellement >= 0) && (date != null)) {
			this.isValid = true;
		}
	}
	
	public boolean isValid() {
		return this.isValid;
	}

	private void incrementAutoId() {
		this.id = idMax;
		idMax++;
	}

}
