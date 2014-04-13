package ca.ulaval.glo4002.rest.dto.validators;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.services.dto.DrugSearchDTO;
import ca.ulaval.glo4002.services.dto.validators.*;

public class DrugSearchDTOValidatorTest {
	private static final String SAMPLE_FOUR_LETTER_PARAMETER = "abcd";
	private static final String SAMPLE_THREE_LETTER_PARAMETER = "abc";
	private static final String SAMPLE_TWO_LETTER_PARAMETER = "ab ";

	DrugSearchDTO drugSearchDTO = new DrugSearchDTO();
	DrugSearchDTOValidator drugSearchDTOValidator;
	
	@Before
	public void init() {
		drugSearchDTOValidator = new DrugSearchDTOValidator();
	}

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
	
	@Test(expected = DrugSearchException.class)
	public void disallowsTwoLetterDrugName() throws Exception {
		drugSearchDTO.name = SAMPLE_TWO_LETTER_PARAMETER;

		drugSearchDTOValidator.validate(drugSearchDTO);
	}

	@Test(expected = DrugSearchException.class)
	public void disallowsEmptyDrugName(){
		drugSearchDTO.name = "";

		drugSearchDTOValidator.validate(drugSearchDTO);
	}

	@Test(expected = DrugSearchException.class)
	public void disallowsUnspecifiedDrugNameParameter() {
		drugSearchDTOValidator.validate(drugSearchDTO);
	}
}
