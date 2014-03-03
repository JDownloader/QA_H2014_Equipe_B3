package ca.ulaval.glo4002.domain.prescription;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.staff.StaffMember;

public class PrescriptionBuilderTest {
	private static final Date SAMPLE_DATE = new Date();
	private static final int SAMPLE_NUMBER_OF_RENEWALS = 2;
	private static final String SAMPLE_DRUG_NAME = "drug_name";

	private Drug drugMock;
	private StaffMember staffMemberMock;

	private PrescriptionBuilder prescriptionBuilder;
	private PrescriptionBuilder prescriptionBuilderSpy;

	@Before
	public void init() {
		drugMock = mock(Drug.class);
		staffMemberMock = mock(StaffMember.class);

		prescriptionBuilder = new PrescriptionBuilder();
		prescriptionBuilderSpy = spy(prescriptionBuilder);
	}

	@Test
	public void buildsCorrectly() {
		doBuild();
	}

	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedStaffMember() {
		doReturn(prescriptionBuilderSpy).when(prescriptionBuilderSpy).staffMember(any(StaffMember.class));
		doBuild();
	}

	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedRenewalCount() {
		doReturn(prescriptionBuilderSpy).when(prescriptionBuilderSpy).allowedNumberOfRenewal(anyInt());
		doBuild();
	}

	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedDate() {
		doReturn(prescriptionBuilderSpy).when(prescriptionBuilderSpy).date(any(Date.class));
		doBuild();
	}

	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedDrugAndDrugName() {
		doReturn(prescriptionBuilderSpy).when(prescriptionBuilderSpy).drug(any(Drug.class));
		doBuild();
	}

	@Test(expected = IllegalStateException.class)
	public void disallowsDrugAndDrugNameBothSpecified() {
		prescriptionBuilderSpy.drugName(SAMPLE_DRUG_NAME);
		doBuild();
	}

	private void doBuild() {
		prescriptionBuilderSpy.drug(drugMock);
		prescriptionBuilderSpy.date(SAMPLE_DATE);
		prescriptionBuilderSpy.allowedNumberOfRenewal(SAMPLE_NUMBER_OF_RENEWALS);
		prescriptionBuilderSpy.staffMember(staffMemberMock);
		prescriptionBuilderSpy.build();
	}
}
