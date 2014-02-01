package ca.ulaval.glo4002.patient;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

@Entity(name = "Patient")
public class Patient {

	@Id
	@Column(name = "PATIENT_ID")
	private int id;

	@Transient
	private static Integer idMax = 0;

	@ElementCollection()
	@JoinColumn(name = "PRES_ID")
	private List<Integer> presciptionId = new ArrayList<Integer>();

	public Patient() {
		incrementAutoId();
	}

	public int getId() {
		return id;
	}

	public void addPrescription(Integer idPrescription) {
		presciptionId.add(idPrescription);
	}

	private void incrementAutoId() {
		this.id = idMax;
		idMax++;
	}
}
