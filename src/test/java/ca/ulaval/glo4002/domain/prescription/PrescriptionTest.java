package ca.ulaval.glo4002.domain.prescription;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.staff.StaffMember;

public class PrescriptionTest {
	private static final Date SAMPLE_DATE_PARAMETER = new Date(3);
	private static final Integer SAMPLE_RENEWALS_PARAMETER = 2;
	private static final String SAMPLE_STAFF_MEMBER_PARAMETER = "3";
	private static final String SAMPLE_DRUG_NAME_PARAMETER = "drug_name";
	
	Prescription prescriptionWithDrug;
	Prescription anotherPrescriptionWithDrug;
	Prescription prescriptionWithDrugName;
	
	Drug drugMock;
	Drug anotherDrugMock;
	
	@Before
	public void init() {
		drugMock = mock(Drug.class);
		anotherDrugMock = mock(Drug.class);
		
		prescriptionWithDrug = new Prescription(drugMock, SAMPLE_RENEWALS_PARAMETER, 
				SAMPLE_DATE_PARAMETER, new StaffMember(SAMPLE_STAFF_MEMBER_PARAMETER));
		anotherPrescriptionWithDrug = new Prescription(anotherDrugMock, SAMPLE_RENEWALS_PARAMETER, 
				SAMPLE_DATE_PARAMETER, new StaffMember(SAMPLE_STAFF_MEMBER_PARAMETER));
		prescriptionWithDrugName = new Prescription(SAMPLE_DRUG_NAME_PARAMETER, SAMPLE_RENEWALS_PARAMETER, 
				SAMPLE_DATE_PARAMETER, new StaffMember(SAMPLE_STAFF_MEMBER_PARAMETER));
	}
	
	@Test
	public void returnsTrueWhenDrugInteractionIsDetected() {
		when(drugMock.isDrugInteractive(eq(anotherDrugMock))).thenReturn(true);
		assertTrue(prescriptionWithDrug.isPrescriptionInteractive(anotherPrescriptionWithDrug));
	}
	
	@Test
	public void returnsFalseWhenDrugInteractionIsNotDetected() {
		when(drugMock.isDrugInteractive(eq(anotherDrugMock))).thenReturn(false);
		assertFalse(prescriptionWithDrug.isPrescriptionInteractive(anotherPrescriptionWithDrug));
	}
	
	@Test
	public void returnsFalseWhenVerifyingInteractionOfPrescriptionWithDrugName() {
		when(drugMock.isDrugInteractive(eq(anotherDrugMock))).thenReturn(true);
		assertFalse(prescriptionWithDrugName.isPrescriptionInteractive(anotherPrescriptionWithDrug));
	}
}
