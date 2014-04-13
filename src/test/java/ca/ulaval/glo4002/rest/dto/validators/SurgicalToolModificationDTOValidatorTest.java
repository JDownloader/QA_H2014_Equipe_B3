package ca.ulaval.glo4002.rest.dto.validators;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;
import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;
import ca.ulaval.glo4002.services.dto.validators.DTOValidationException;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolModificationDTOValidator;

public class SurgicalToolModificationDTOValidatorTest {

	private static final SurgicalToolStatus SAMPLE_STATUS_PARAMETER = SurgicalToolStatus.UNUSED;
	private static final String SAMPLE_SERIAL_NUMBER_PARAMETER = "23562543-3635345";
	
	SurgicalToolModificationDTO surgicalToolModificationDTO = new SurgicalToolModificationDTO();
	SurgicalToolModificationDTOValidator surgicalToolModificationDTOValidator;

	@Before
	public void init() throws Exception {
		surgicalToolModificationDTOValidator = new SurgicalToolModificationDTOValidator();

		surgicalToolModificationDTO.newStatus = SAMPLE_STATUS_PARAMETER;
		surgicalToolModificationDTO.newSerialNumber = SAMPLE_SERIAL_NUMBER_PARAMETER;
	}

	@Test
	public void validatingCompleteRequestDoesNotThrowAnException() {
		try {
			surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}
	
	@Test
	public void allowsUnspecifiedSerialNumber() {
		surgicalToolModificationDTO.newSerialNumber = null;

		try {
			surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}
	
	@Test(expected = DTOValidationException.class)
	public void disallowsEmptySerialNumber() {
		surgicalToolModificationDTO.newSerialNumber = "";

		surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTO);
	}

	@Test
	public void allowsUnspecifiedStatus() {
		surgicalToolModificationDTO.newStatus = null;

		try {
			surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsStatusAndSerialNumberBothUnspecified() {
		surgicalToolModificationDTO.newStatus = null;
		surgicalToolModificationDTO.newSerialNumber = null;

		surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTO);
	}
}
