package ca.ulaval.glo4002.services.dto.validators;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.services.dto.DrugSearchDTO;

@RunWith(MockitoJUnitRunner.class)
public class DrugSearchDTOValidatorTest {
	private static final String EMPTY_STRING = "";
	private static final String SAMPLE_FOUR_LETTER_PARAMETER = "abcd";
	private static final String SAMPLE_THREE_LETTER_PARAMETER = "abc";
	private static final String SAMPLE_TWO_LETTER_PARAMETER = "ab ";

	private DrugSearchDTO drugSearchDTO = new DrugSearchDTO();
	private DrugSearchDTOValidator drugSearchDTOValidator = new DrugSearchDTOValidator();

	@Test
	public void allowsFourLetterDrugName() {
		drugSearchDTO.name = SAMPLE_FOUR_LETTER_PARAMETER;

		try {
			drugSearchDTOValidator.validate(drugSearchDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test
	public void allowsThreeLetterDrugName() {
		drugSearchDTO.name = SAMPLE_THREE_LETTER_PARAMETER;

		try {
			drugSearchDTOValidator.validate(drugSearchDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsTwoLetterDrugName() throws Exception {
		drugSearchDTO.name = SAMPLE_TWO_LETTER_PARAMETER;

		drugSearchDTOValidator.validate(drugSearchDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsEmptyDrugName() {
		drugSearchDTO.name = EMPTY_STRING;

		drugSearchDTOValidator.validate(drugSearchDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsUnspecifiedDrugNameParameter() {
		drugSearchDTOValidator.validate(drugSearchDTO);
	}
}
