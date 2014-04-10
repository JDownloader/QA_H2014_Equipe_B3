package ca.ulaval.glo4002.rest.dto.validators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.*;
import ca.ulaval.glo4002.utils.DateParser;

public class PrescriptionCreationDTOValidatorTest {
	private static final String SAMPLE_DATE_PARAMETER = "2001-07-04T12:08:56";
	private static final int SAMPLE_RENEWALS_PARAMETER = 2;
	private static final String SAMPLE_DRUG_NAME_PARAMETER = "drug_name";
	private static final int SAMPLE_STAFF_MEMBER_PARAMETER = 3;
	private static final Din SAMPLE_DIN_PARAMETER = new Din("098423");
	private static final int SAMPLE_PATIENT_NUMBER_PARAMETER = 3;
	private static final int MIN_RENEWALS_PARAMETER = 0;

	PrescriptionCreationDTO prescriptionCreationDTOMock;
	PrescriptionCreationDTOValidator prescriptionCreationDTOValidator;
	
	@Before
	public void init() throws ParseException {
		prescriptionCreationDTOValidator = new PrescriptionCreationDTOValidator();
		prescriptionCreationDTOMock = mock(PrescriptionCreationDTO.class);
		
		when(prescriptionCreationDTOMock.getStaffMember()).thenReturn(SAMPLE_STAFF_MEMBER_PARAMETER);
		when(prescriptionCreationDTOMock.getDate()).thenReturn(DateParser.parseDate(SAMPLE_DATE_PARAMETER));
		when(prescriptionCreationDTOMock.getRenewals()).thenReturn(SAMPLE_RENEWALS_PARAMETER);
		when(prescriptionCreationDTOMock.getPatientNumber()).thenReturn(SAMPLE_PATIENT_NUMBER_PARAMETER);
		when(prescriptionCreationDTOMock.getDin()).thenReturn(SAMPLE_DIN_PARAMETER);
	}

	@Test
	public void validatingGoodRequestWithDrugNameDoesNotThrowAnException() {
		when(prescriptionCreationDTOMock.getDin()).thenReturn(null);
		when(prescriptionCreationDTOMock.getDrugName()).thenReturn(SAMPLE_DRUG_NAME_PARAMETER);

		try {
			prescriptionCreationDTOValidator.validate(prescriptionCreationDTOMock);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test
	public void validatingGoodRequestWithDinDoesNotThrowAnException() {
		when(prescriptionCreationDTOMock.getDin()).thenReturn(SAMPLE_DIN_PARAMETER);
		when(prescriptionCreationDTOMock.getDrugName()).thenReturn(null);

		try {
			prescriptionCreationDTOValidator.validate(prescriptionCreationDTOMock);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test(expected = PrescriptionCreationException.class)
	public void disallowsEmptyDrugName() {
		when(prescriptionCreationDTOMock.getDin()).thenReturn(null);
		when(prescriptionCreationDTOMock.getDrugName()).thenReturn("");

		prescriptionCreationDTOValidator.validate(prescriptionCreationDTOMock);
	}

	@Test(expected = PrescriptionCreationException.class)
	public void disallowsUnspecifiedDrugAndDrugNameParameters() {
		when(prescriptionCreationDTOMock.getDin()).thenReturn(null);
		when(prescriptionCreationDTOMock.getDrugName()).thenReturn(null);

		prescriptionCreationDTOValidator.validate(prescriptionCreationDTOMock);
	}

	@Test(expected = PrescriptionCreationException.class)
	public void disallowsDrugAndDrugNameParametersBothSpecified() {
		when(prescriptionCreationDTOMock.getDin()).thenReturn(SAMPLE_DIN_PARAMETER);
		when(prescriptionCreationDTOMock.getDrugName()).thenReturn(SAMPLE_DRUG_NAME_PARAMETER);

		prescriptionCreationDTOValidator.validate(prescriptionCreationDTOMock);
	}

	@Test(expected = PrescriptionCreationException.class)
	public void disallowsUnspecifiedStaffMemberParameter() {
		when(prescriptionCreationDTOMock.getStaffMember()).thenReturn(null);
		prescriptionCreationDTOValidator.validate(prescriptionCreationDTOMock);
	}

	@Test(expected = PrescriptionCreationException.class)
	public void disallowsUnspecifiedRenewalsParameter() {
		when(prescriptionCreationDTOMock.getRenewals()).thenReturn(null);
		prescriptionCreationDTOValidator.validate(prescriptionCreationDTOMock);
	}

	@Test(expected = PrescriptionCreationException.class)
	public void disallowsUnspecifiedDateParameter() {
		when(prescriptionCreationDTOMock.getDate()).thenReturn(null);
		prescriptionCreationDTOValidator.validate(prescriptionCreationDTOMock);
	}

	@Test(expected = PrescriptionCreationException.class)
	public void disallowsNegativeRenewalsParameter() {
		when(prescriptionCreationDTOMock.getRenewals()).thenReturn(-1);
		prescriptionCreationDTOValidator.validate(prescriptionCreationDTOMock);
	}

	@Test
	public void allowsMinimumRenewalsParameter() {
		when(prescriptionCreationDTOMock.getRenewals()).thenReturn(MIN_RENEWALS_PARAMETER);
		
		try {
			prescriptionCreationDTOValidator.validate(prescriptionCreationDTOMock);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}
}
