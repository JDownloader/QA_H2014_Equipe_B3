package ca.ulaval.glo4002.domain.prescription;

import java.util.Date;

import org.junit.Test;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.staff.StaffMember;

public class PrescriptionBuilderTest {
	private Drug drugMock;
	private StaffMember staffMemberMock;
	
	private static final Date SAMPLE_DATE = new Date();
	private static final int SAMPLE_NUMBER_OF_RENEWALS = 2;
	
	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedPrescriber() {
		new PrescriptionBuilder().drug(drugMock).date(SAMPLE_DATE).allowedRenewalCount(SAMPLE_NUMBER_OF_RENEWALS).build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedRenewalCount() {
		new PrescriptionBuilder().drug(drugMock).date(SAMPLE_DATE).prescriber(staffMemberMock).build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedDate() {
		new PrescriptionBuilder().drug(drugMock).prescriber(staffMemberMock).allowedRenewalCount(SAMPLE_NUMBER_OF_RENEWALS).build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedDrugAndDrugName() {
		new PrescriptionBuilder().date(SAMPLE_DATE).prescriber(staffMemberMock).allowedRenewalCount(SAMPLE_NUMBER_OF_RENEWALS).build();
	}
}
