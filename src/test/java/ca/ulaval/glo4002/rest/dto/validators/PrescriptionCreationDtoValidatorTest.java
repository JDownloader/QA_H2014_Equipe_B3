package ca.ulaval.glo4002.rest.dto.validators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.services.dto.PrescriptionCreationDto;
import ca.ulaval.glo4002.services.dto.validators.PrescriptionCreationDtoValidator;
import ca.ulaval.glo4002.utils.DateParser;

public class PrescriptionCreationDtoValidatorTest {
	private static final String SAMPLE_DATE_PARAMETER = "2001-07-04T12:08:56";
	private static final int SAMPLE_RENEWALS_PARAMETER = 2;
	private static final String SAMPLE_DRUG_NAME_PARAMETER = "drug_name";
	private static final int SAMPLE_STAFF_MEMBER_PARAMETER = 3;
	private static final String SAMPLE_DIN_PARAMETER = "098423";
	private static final int SAMPLE_PATIENT_NUMBER_PARAMETER = 3;
	private static final int MIN_RENEWALS_PARAMETER = 0;
	private static final int MIN_STAFF_MEMBER_PARAMETER = 0;

	PrescriptionCreationDto prescriptionCreationDtoMock;
	PrescriptionCreationDtoValidator prescriptionCreationDtoValidator;
	
	@Before
	public void init() throws Exception {
		prescriptionCreationDtoValidator = new PrescriptionCreationDtoValidator();
		prescriptionCreationDtoMock = mock(PrescriptionCreationDto.class);
		
		when(prescriptionCreationDtoMock.getStaffMember()).thenReturn(SAMPLE_STAFF_MEMBER_PARAMETER);
		when(prescriptionCreationDtoMock.getStaffMember()).thenReturn(SAMPLE_STAFF_MEMBER_PARAMETER);
		when(prescriptionCreationDtoMock.getDate()).thenReturn(DateParser.parseDate(SAMPLE_DATE_PARAMETER));
		when(prescriptionCreationDtoMock.getRenewals()).thenReturn(SAMPLE_RENEWALS_PARAMETER);
		when(prescriptionCreationDtoMock.getPatientNumber()).thenReturn(SAMPLE_PATIENT_NUMBER_PARAMETER);
		when(prescriptionCreationDtoMock.getDin()).thenReturn(SAMPLE_DIN_PARAMETER);
	}

	@Test
	public void validatingGoodRequestWithDrugNameDoesNotThrowAnException() throws Exception {
		when(prescriptionCreationDtoMock.getDin()).thenReturn(null);
		when(prescriptionCreationDtoMock.getDrugName()).thenReturn(SAMPLE_DRUG_NAME_PARAMETER);

		try {
			prescriptionCreationDtoValidator.validate(prescriptionCreationDtoMock);
		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}

	@Test
	public void validatingGoodRequestWithDinDoesNotThrowAnException() throws Exception {
		when(prescriptionCreationDtoMock.getDin()).thenReturn(SAMPLE_DIN_PARAMETER);
		when(prescriptionCreationDtoMock.getDrugName()).thenReturn(null);

		try {
			prescriptionCreationDtoValidator.validate(prescriptionCreationDtoMock);
		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsEmptyDrugName() throws Exception {
		when(prescriptionCreationDtoMock.getDin()).thenReturn(null);
		when(prescriptionCreationDtoMock.getDrugName()).thenReturn("");

		prescriptionCreationDtoValidator.validate(prescriptionCreationDtoMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsUnspecifiedDrugAndDrugNameParameters() throws Exception {
		when(prescriptionCreationDtoMock.getDin()).thenReturn(null);
		when(prescriptionCreationDtoMock.getDrugName()).thenReturn(null);

		prescriptionCreationDtoValidator.validate(prescriptionCreationDtoMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsDrugAndDrugNameParametersBothSpecified() throws Exception {
		when(prescriptionCreationDtoMock.getDin()).thenReturn(SAMPLE_DIN_PARAMETER);
		when(prescriptionCreationDtoMock.getDrugName()).thenReturn(SAMPLE_DRUG_NAME_PARAMETER);

		prescriptionCreationDtoValidator.validate(prescriptionCreationDtoMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsUnspecifiedStaffMemberParameter() throws Exception {
		when(prescriptionCreationDtoMock.getStaffMember()).thenReturn(null);
		prescriptionCreationDtoValidator.validate(prescriptionCreationDtoMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsUnspecifiedRenewalsParameter() throws Exception {
		when(prescriptionCreationDtoMock.getRenewals()).thenReturn(null);
		prescriptionCreationDtoValidator.validate(prescriptionCreationDtoMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsUnspecifiedDateParameter() throws Exception {
		when(prescriptionCreationDtoMock.getDate()).thenReturn(null);
		prescriptionCreationDtoValidator.validate(prescriptionCreationDtoMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsNegativeRenewalsParameter() throws Exception {
		when(prescriptionCreationDtoMock.getRenewals()).thenReturn(-1);
		prescriptionCreationDtoValidator.validate(prescriptionCreationDtoMock);
	}

	@Test
	public void allowsMinimumStaffMemberParameter() throws Exception {
		when(prescriptionCreationDtoMock.getStaffMember()).thenReturn(MIN_STAFF_MEMBER_PARAMETER);
		
		try {
			prescriptionCreationDtoValidator.validate(prescriptionCreationDtoMock);
		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}

	@Test
	public void allowsMinimumRenewalsParameter() throws Exception {
		when(prescriptionCreationDtoMock.getRenewals()).thenReturn(MIN_RENEWALS_PARAMETER);
		
		try {
			prescriptionCreationDtoValidator.validate(prescriptionCreationDtoMock);
		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}
}
