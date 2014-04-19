package ca.ulaval.glo4002.services.dto.validators;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;
import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;

public class SurgicalToolCreationDTOValidatorTest {

	private static final String SAMPLE_TYPECODE_PARAMETER = "IT72353";
	private static final SurgicalToolStatus SAMPLE_STATUS_PARAMETER = SurgicalToolStatus.UNUSED;
	private static final String SAMPLE_SERIAL_NUMBER_PARAMETER = "23562543-3635345";

	private SurgicalToolCreationDTO surgicalToolCreationDTO = new SurgicalToolCreationDTO();
	private SurgicalToolCreationDTOValidator surgicalToolCreationDTOValidator = new SurgicalToolCreationDTOValidator();

	@Before
	public void init() throws Exception {
		surgicalToolCreationDTO.typeCode = SAMPLE_TYPECODE_PARAMETER;
		surgicalToolCreationDTO.status = SAMPLE_STATUS_PARAMETER;
		surgicalToolCreationDTO.serialNumber = SAMPLE_SERIAL_NUMBER_PARAMETER;
	}

	@Test
	public void validatingCompleteRequestDoesNotThrowAnException() {
		try {
			surgicalToolCreationDTOValidator.validate(surgicalToolCreationDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test
	public void allowsRequestWithNoSerialNumber() {
		surgicalToolCreationDTO.serialNumber = null;

		try {
			surgicalToolCreationDTOValidator.validate(surgicalToolCreationDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsEmptyTypeCode() {
		surgicalToolCreationDTO.typeCode = null;

		surgicalToolCreationDTOValidator.validate(surgicalToolCreationDTO);
	}
}
