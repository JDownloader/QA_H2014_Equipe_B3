package ca.ulaval.glo4002.domain.patient;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.domain.prescription.Prescription;
import ca.ulaval.glo4002.exceptions.ItemNotFoundException;

public class PatientTest {
	
	private static final int SAMPLE_PATIENT_ID = 3;
	private Patient patient;

	@Before
	public void init() {
		patient = new Patient(SAMPLE_PATIENT_ID);
	}
	
	@Test
	public void returnsTheCorrectId() {
		assertEquals(SAMPLE_PATIENT_ID, patient.getId());
	}
	
	@Test
	public void addsPrescriptionCorrectly() throws ItemNotFoundException {
		Prescription prescriptionMock = mock(Prescription.class);
		patient.addPrescription(prescriptionMock);
		assertTrue(patient.hasPrescription(prescriptionMock));
	}
}
