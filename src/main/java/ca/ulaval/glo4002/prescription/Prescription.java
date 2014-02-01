package ca.ulaval.glo4002.prescription;

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

import ca.ulaval.glo4002.drug.Drug;
import ca.ulaval.glo4002.exceptions.InvalidDateFormatException;
import ca.ulaval.glo4002.staff.StaffMember;

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
	private static int idMax = 1;

	@Id
	@Column(name = "PRES_ID", nullable = false)
	private Integer id;

	@ManyToOne()
	@ElementCollection(targetClass = Drug.class)
	@JoinColumn(name = "DRUG", nullable = false)
	private Drug drug;

	@Column(name = "RENEWAL", nullable = false)
	private int renewal = -1;

	@Column(name = "DATE", nullable = false)
	private Date date;

	@ManyToOne()
	@ElementCollection(targetClass = StaffMember.class)
	@JoinColumn(name = "STAFF_MEMBER", nullable = false)
	private StaffMember prescriber;

	@Transient
	private boolean isValid = false;

	public Prescription(Drug drug, StaffMember staff) {
		incrementAutoId();
		this.drug = drug;
		this.prescriber = staff;
	}

	public Integer getId() {
		return id;
	}

	public void setRenewal(int renewal) {
		this.renewal = renewal;
		calculateValid();
	}

	public void setDate(String date) throws InvalidDateFormatException,
			ParseException {
		// if (validate.validateDate(date)) {
		this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		// }
		calculateValid();
	}

	private void calculateValid() {
		if ((renewal >= 0) && (date != null)) {
			this.isValid = true;
		}
	}

	public boolean isValid() {
		return this.isValid;
	}

	/*
	 * public void addPrescription(Prescription prescription) {
	 * EM.getUserTransaction().begin();
	 * EM.getEntityManager().persist(prescription);
	 * EM.getUserTransaction().commit();
	 * 
	 * }
	 */

	private void incrementAutoId() {
		this.id = idMax;
		idMax++;
	}

}
