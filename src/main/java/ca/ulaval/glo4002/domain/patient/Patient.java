package ca.ulaval.glo4002.domain.patient;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.prescription.Prescription;

@Entity(name = "PATIENT")
public class Patient {

	@Id
	private int id;

	@ElementCollection()
	private List<Prescription> prescriptions = new ArrayList<Prescription>();

	protected Patient() {
		// Required for Hibernate.
	}

	public Patient(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void addPrescription(Prescription prescription) {
		prescriptions.add(prescription);
	}

	public boolean hasPrescription(Prescription prescription) {
		return prescriptions.contains(prescription);
	}
}
