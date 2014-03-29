package ca.ulaval.glo4002.rest.dto.validators;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.rest.dto.PrescriptionCreationDto;
import ca.ulaval.glo4002.utils.DateParser;

public class PrescriptionCreationDtoValidatorTest {
	private static final String SAMPLE_DATE_PARAMETER = "2001-07-04T12:08:56";
	private static final int SAMPLE_RENEWALS_PARAMETER = 2;
	private static final String SAMPLE_DRUG_NAME_PARAMETER = "drug_name";
	private static final int SAMPLE_STAFF_MEMBER_PARAMETER = 3;
	private static final int SAMPLE_DIN_PARAMETER = 3;
	private static final int SAMPLE_PATIENT_NUMBER_PARAMETER = 3;
	private static final int MIN_RENEWALS_PARAMETER = 0;
	private static final int MIN_STAFF_MEMBER_PARAMETER = 0;

	PrescriptionCreationDto prescriptionCreationDto;

	@Before
	public void init() throws Exception {
		prescriptionCreationDto = new PrescriptionCreationDto();
		
		prescriptionCreationDto.setStaffMember(SAMPLE_STAFF_MEMBER_PARAMETER);
		prescriptionCreationDto.setDate(DateParser.parseDate(SAMPLE_DATE_PARAMETER));
		prescriptionCreationDto.setRenewals(SAMPLE_RENEWALS_PARAMETER);
		prescriptionCreationDto.setPatientNumber(SAMPLE_PATIENT_NUMBER_PARAMETER);
		prescriptionCreationDto.setDin(SAMPLE_DIN_PARAMETER);
	}

	@Test
	public void validatingGoodRequestWithDrugNameDoesNotThrowAnException() throws Exception {
		prescriptionCreationDto.setDin(null);
		prescriptionCreationDto.setDrugName(SAMPLE_DRUG_NAME_PARAMETER);

		try {
			PrescriptionCreationDtoValidator.validate(prescriptionCreationDto);
		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}

	@Test
	public void validatingGoodRequestWithDinDoesNotThrowAnException() throws Exception {
		prescriptionCreationDto.setDin(SAMPLE_DIN_PARAMETER);
		prescriptionCreationDto.setDrugName(null);

		try {
			PrescriptionCreationDtoValidator.validate(prescriptionCreationDto);
		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsEmptyDrugName() throws Exception {
		prescriptionCreationDto.setDin(null);
		prescriptionCreationDto.setDrugName("");

		PrescriptionCreationDtoValidator.validate(prescriptionCreationDto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsUnspecifiedDrugAndDrugNameParameters() throws Exception {
		prescriptionCreationDto.setDin(null);
		prescriptionCreationDto.setDrugName(null);

		PrescriptionCreationDtoValidator.validate(prescriptionCreationDto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsDrugAndDrugNameParametersBothSpecified() throws Exception {
		prescriptionCreationDto.setDin(SAMPLE_DIN_PARAMETER);
		prescriptionCreationDto.setDrugName(SAMPLE_DRUG_NAME_PARAMETER);

		PrescriptionCreationDtoValidator.validate(prescriptionCreationDto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsUnspecifiedStaffMemberParameter() throws Exception {
		prescriptionCreationDto.setStaffMember(null);
		PrescriptionCreationDtoValidator.validate(prescriptionCreationDto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsUnspecifiedRenewalsParameter() throws Exception {
		prescriptionCreationDto.setRenewals(null);
		PrescriptionCreationDtoValidator.validate(prescriptionCreationDto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsUnspecifiedDateParameter() throws Exception {
		prescriptionCreationDto.setDate(null);
		PrescriptionCreationDtoValidator.validate(prescriptionCreationDto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void disallowsNegativeRenewalsParameter() throws Exception {
		prescriptionCreationDto.setRenewals(-1);
		PrescriptionCreationDtoValidator.validate(prescriptionCreationDto);
	}

	@Test
	public void allowsMinimumStaffMemberParameter() throws Exception {
		prescriptionCreationDto.setStaffMember(MIN_STAFF_MEMBER_PARAMETER);
		
		try {
			PrescriptionCreationDtoValidator.validate(prescriptionCreationDto);
		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}

	@Test
	public void allowsMinimumRenewalsParameter() throws Exception {
		prescriptionCreationDto.setRenewals(MIN_RENEWALS_PARAMETER);
		
		try {
			PrescriptionCreationDtoValidator.validate(prescriptionCreationDto);
		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}
}
