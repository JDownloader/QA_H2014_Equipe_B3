package ca.ulaval.glo4002.services.dto.validators;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;
import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;

@RunWith(MockitoJUnitRunner.class)
public class SurgicalToolModificationDTOValidatorTest {

	private static final String WHITE_SPACE = " ";
	private static final SurgicalToolStatus SAMPLE_STATUS_PARAMETER = SurgicalToolStatus.UNUSED;
	private static final String SAMPLE_SERIAL_NUMBER_PARAMETER = "23562543-3635345";
	private static final String SAMPLE_TYPE_CODE = "IT443";
	
	private SurgicalToolModificationDTO surgicalToolModificationDTO = new SurgicalToolModificationDTO();
	private SurgicalToolModificationDTOValidator surgicalToolModificationDTOValidator = new SurgicalToolModificationDTOValidator();

	@Before
	public void init() throws Exception {
		surgicalToolModificationDTO.newStatus = SAMPLE_STATUS_PARAMETER.getValue();
		surgicalToolModificationDTO.newSerialNumber = SAMPLE_SERIAL_NUMBER_PARAMETER;
	}

	@Test
	public void validRequestIsCorrectlyValidated() {
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
		surgicalToolModificationDTO.newSerialNumber = WHITE_SPACE;

		surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsUnspecifiedStatus() {
		surgicalToolModificationDTO.newStatus = null;

		surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsStatusAndSerialNumberBothNotSpecified() {
		surgicalToolModificationDTO.newStatus = null;
		surgicalToolModificationDTO.newSerialNumber = null;

		surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTO);
	}
	
	@Test(expected = DTOValidationException.class)
	public void disallowsSpecifiedTypeCode() {
		surgicalToolModificationDTO.newTypeCode = SAMPLE_TYPE_CODE;

		surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTO);
	}
}
