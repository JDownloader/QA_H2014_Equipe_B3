package ca.ulaval.glo4002.patient;

import java.util.List;

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
	private static Integer idMax = 0;

	@OneToMany(targetEntity = Prescription.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "PRES_ID")
	private List<Integer> presciptionId;

	public Patient() {
		incrementAutoId();
	}

	public int getId() {
		return id;
	}

	public void addPrescription(int idPrescription) {
		presciptionId.add(idPrescription);
	}

	private void incrementAutoId() {
		this.id = idMax;
		idMax++;
	}
}
