package ca.ulaval.glo4002.domain.patient;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.prescription.Prescription;

@RunWith(MockitoJUnitRunner.class)
public class PatientTest {

	private static final Integer SAMPLE_PATIENT_ID = 3;
	private Patient patient;
	Prescription prescriptionMock;
	Prescription anotherPrescriptionMock;

	@Before
	public void init() {
		patient = new Patient(SAMPLE_PATIENT_ID);
		prescriptionMock = mock(Prescription.class);
		anotherPrescriptionMock = mock(Prescription.class);
	}

	@Test
	public void addsPrescriptionCorrectly() {
		patient.addPrescription(prescriptionMock);
		assertTrue(patient.hasPrescription(prescriptionMock));
	}
	
	@Test(expected = DrugInteractionException.class)
	public void throwsDrugInteractionExceptionWhenDrugInteractionIsDetected() {
		patient.addPrescription(prescriptionMock);
		when(prescriptionMock.isPrescriptionInteractive(eq(anotherPrescriptionMock))).thenReturn(true);
		
		patient.addPrescription(anotherPrescriptionMock);
	}
	
	@Test
	public void addsPrescriptionCorrectlyWhenDrugInteractionIsNotDetected() {
		patient.addPrescription(prescriptionMock);
		when(prescriptionMock.isPrescriptionInteractive(eq(anotherPrescriptionMock))).thenReturn(false);
		
		patient.addPrescription(anotherPrescriptionMock);
		
		assertTrue(patient.hasPrescription(anotherPrescriptionMock));
	}
}
