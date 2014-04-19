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

	private SurgicalToolCreationDTO SurgicalToolCreationDTO = new SurgicalToolCreationDTO();
	private SurgicalToolCreationDTOValidator SurgicalToolCreationDTOValidator = new SurgicalToolCreationDTOValidator();

	@Before
	public void init() throws Exception {
		SurgicalToolCreationDTO.typeCode = SAMPLE_TYPECODE_PARAMETER;
		SurgicalToolCreationDTO.status = SAMPLE_STATUS_PARAMETER;
		SurgicalToolCreationDTO.serialNumber = SAMPLE_SERIAL_NUMBER_PARAMETER;
	}

	@Test
	public void validatingCompleteRequestDoesNotThrowAnException() {
		try {
			SurgicalToolCreationDTOValidator.validate(SurgicalToolCreationDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}
	
	@Test
	public void allowsRequestWithNoSerialNumber() {
		SurgicalToolCreationDTO.serialNumber = null;

		try {
			SurgicalToolCreationDTOValidator.validate(SurgicalToolCreationDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}
	
	@Test(expected = DTOValidationException.class)
	public void disallowsEmptyTypeCode() {
		SurgicalToolCreationDTO.typeCode = null;

		SurgicalToolCreationDTOValidator.validate(SurgicalToolCreationDTO);
	}
}
