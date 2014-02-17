package ca.ulaval.glo4002.patient;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import ca.ulaval.glo4002.prescription.Prescription;

@Entity(name = "Patient")
public class Patient {

	@Id
	@Column(name = "PATIENT_ID", nullable = false)
	private int id = 0;

	@ElementCollection()
	@JoinColumn(name = "PATIENT")
	private List<Prescription> presciptions = new ArrayList<Prescription>();

	@SuppressWarnings("unused")
	private Patient() {
		
	}
	
	public Patient(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void addPrescription(Prescription prescription) {
		presciptions.add(prescription);
	}
}
