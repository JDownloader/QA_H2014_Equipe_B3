package ca.ulaval.glo4002.domain.prescription;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.staff.StaffMember;

public class PrescriptionTest {
	private Prescription prescription;
	private Drug drugMock;
	private StaffMember staffMemberMock;

	private static final Date SAMPLE_DATE = new Date();
	private static final int SAMPLE_NUMBER_OF_RENEWALS = 2;
	private static final String SAMPLE_DRUG_NAME = "drug_name";

	@Before
	public void init() {
		drugMock = mock(Drug.class);
		staffMemberMock = mock(StaffMember.class);

		PrescriptionBuilder prescriptionBuilder = new PrescriptionBuilder();
		prescriptionBuilder.drug(drugMock);
		prescription = buildCommonParameters(prescriptionBuilder).build();

	}

	private PrescriptionBuilder buildCommonParameters(PrescriptionBuilder prescriptionBuilder) {
		prescriptionBuilder.date(SAMPLE_DATE);
		prescriptionBuilder.staffMember(staffMemberMock);
		prescriptionBuilder.allowedNumberOfRenewal(SAMPLE_NUMBER_OF_RENEWALS);
		return prescriptionBuilder;
	}

	@Test
	public void comparesIdCorrectly() {
		int prescriptionId = prescription.getId();
		assertTrue(prescription.compareId(prescriptionId));
	}

	@Test
	public void returnsDrugCorrectly() {
		assertSame(drugMock, prescription.getDrug());
	}

	@Test
	public void returnsDrugNameCorrectly() {
		PrescriptionBuilder prescriptionBuilder = new PrescriptionBuilder().drugName(SAMPLE_DRUG_NAME);
		Prescription prescriptionWithDrugName = buildCommonParameters(prescriptionBuilder).build();

		assertEquals(SAMPLE_DRUG_NAME, prescriptionWithDrugName.getDrugName());
	}

	@Test
	public void returnsAllowedNumberOfRenewalsCorrectly() {
		int value = prescription.getAllowedNumberOfRenewal();
		assertEquals(SAMPLE_NUMBER_OF_RENEWALS, value);
	}

	@Test
	public void returnsDateCorrectly() {
		assertEquals(SAMPLE_DATE, prescription.getDate());
	}

	@Test
	public void returnsStaffMemberCorrectly() {
		assertSame(staffMemberMock, prescription.getStaffMember());
	}
}
