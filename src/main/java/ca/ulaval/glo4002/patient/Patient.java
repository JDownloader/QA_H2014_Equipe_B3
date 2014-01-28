package ca.ulaval.glo4002.patient;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ca.ulaval.glo4002.prescription.Prescription;

@Entity(name = "Patient")
public class Patient {

	@Id
	@Column(name = "PATIENT_ID")
	private int id;

	@Transient
	private static int idMax = 0;

	@OneToMany(targetEntity = Prescription.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "PRES_ID")
	private Prescription prescriptionId;

	/*
	 * Il n'est pas possible de persister une liste, elle est "Transient" pour
	 * dire qu'elle ne serait pas ajouter
	 * 
	 * Antoine
	 */
	@Transient
	private ArrayList<Integer> presciptionId;

	public Patient() {
		incrementAutoId();
	}

	public int getId() {
		return id;
	}

	/*
	 * Si l'on garde l'ArrayList, la methode peut toujours servir, sinon est
	 * elle pourait etre modifier pour mettre a jouer la BD
	 * 
	 * Antoine
	 */
	public void addPrescription(int idPrescription) {
		presciptionId.add(idPrescription);
	}

	private void incrementAutoId() {
		this.id = idMax;
		idMax++;
	}
}
