package ca.ulaval.glo4002.domain.patient;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.prescription.Prescription;

public class PatientTest {

	private static final Integer SAMPLE_PATIENT_ID = 3;
	private Patient patient;

	@Before
	public void init() {
		patient = new Patient(SAMPLE_PATIENT_ID);
	}

	@Test
	public void addsPrescriptionCorrectly() {
		Prescription prescriptionMock = mock(Prescription.class);
		patient.addPrescription(prescriptionMock);
		assertTrue(patient.hasPrescription(prescriptionMock));
	}
}
