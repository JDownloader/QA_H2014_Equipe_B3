package ca.ulaval.glo4002.domain.patient;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ca.ulaval.glo4002.domain.prescription.Prescription;
import ca.ulaval.glo4002.exceptions.ItemNotFoundException;

public class PatientTest {
	
	private static final int ID_OF_PATIENT = 3;
	private Patient patient;

	@Before
	public void setup() {
		patient = new Patient(ID_OF_PATIENT);
	}
	
	@Test
	public void returnsTheCorrectId() {
		assertEquals(ID_OF_PATIENT, patient.getId());
	}
	
	@Test
	public void addsPrescriptionCorrectly() throws ItemNotFoundException {
		Prescription prescriptionMock = Mockito.mock(Prescription.class);
		patient.addPrescription(prescriptionMock);
		assertTrue(patient.hasPrescription(prescriptionMock));
	}
}
