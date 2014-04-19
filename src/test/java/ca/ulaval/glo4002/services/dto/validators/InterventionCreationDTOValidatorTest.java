package ca.ulaval.glo4002.services.dto.validators;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.intervention.InterventionStatus;
import ca.ulaval.glo4002.domain.intervention.InterventionType;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionCreationDTOValidatorTest {
	private static final String SAMPLE_DESCRIPTION_PARAMETER = "description";
	private static final Surgeon SAMPLE_SURGEON_PARAMETER = new Surgeon("1");
	private static final Date SAMPLE_DATE_PARAMETER = new Date(3);
	private static final String SAMPLE_ROOM_PARAMETER = "room";
	private static final InterventionType SAMPLE_TYPE_PARAMETER = InterventionType.HEART;
	private static final InterventionStatus SAMPLE_STATUS_PARAMETER = InterventionStatus.PLANNED;
	private static final Integer SAMPLE_PATIENT_NUMBER_PARAMETER = 3;

	private InterventionCreationDTO interventionCreationDTO = new InterventionCreationDTO();
	private InterventionCreationDTOValidator interventionCreationDTOValidator = new InterventionCreationDTOValidator();

	@Before
	public void init() {
		interventionCreationDTO.description = SAMPLE_DESCRIPTION_PARAMETER;
		interventionCreationDTO.surgeon = SAMPLE_SURGEON_PARAMETER;
		interventionCreationDTO.date = SAMPLE_DATE_PARAMETER;
		interventionCreationDTO.room = SAMPLE_ROOM_PARAMETER;
		interventionCreationDTO.type = SAMPLE_TYPE_PARAMETER;
		interventionCreationDTO.status = SAMPLE_STATUS_PARAMETER;
		interventionCreationDTO.patientNumber = SAMPLE_PATIENT_NUMBER_PARAMETER;
	}

	@Test
	public void validatingCompleteRequestDoesNotThrowAnException() {
		try {
			interventionCreationDTOValidator.validate(interventionCreationDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test
	public void allowsUnspecifiedStatus() {
		interventionCreationDTO.status = null;

		try {
			interventionCreationDTOValidator.validate(interventionCreationDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsUnspecifiedSurgeon() {
		interventionCreationDTO.surgeon = null;
		interventionCreationDTOValidator.validate(interventionCreationDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsUnspecifiedDate() {
		interventionCreationDTO.date = null;
		interventionCreationDTOValidator.validate(interventionCreationDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsUnspecifiedRoom() {
		interventionCreationDTO.room = null;
		interventionCreationDTOValidator.validate(interventionCreationDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsUnspecifiedType() {
		interventionCreationDTO.type = null;
		interventionCreationDTOValidator.validate(interventionCreationDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsUnspecifiedPatientNumber() {
		interventionCreationDTO.patientNumber = null;
		interventionCreationDTOValidator.validate(interventionCreationDTO);
	}

}
