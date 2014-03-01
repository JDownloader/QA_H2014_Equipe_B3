package ca.ulaval.glo4002.domain.patient;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.prescription.Prescription;

@Entity(name = "PATIENT")
public class Patient {

	@Id
	@Column(name = "PATIENT_ID", nullable = false)
	private int id = 0;

	@ElementCollection()
	@JoinColumn(name = "PATIENT")
	private List<Prescription> presciptions = new ArrayList<Prescription>();

	protected Patient() {
		
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
