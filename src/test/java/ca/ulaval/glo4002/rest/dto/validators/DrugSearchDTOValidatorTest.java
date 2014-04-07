package ca.ulaval.glo4002.rest.dto.validators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.services.dto.DrugSearchDTO;
import ca.ulaval.glo4002.services.dto.validators.*;

public class DrugSearchDTOValidatorTest {
	private static final String SAMPLE_FOUR_LETTER_PARAMETER = "abcd";
	private static final String SAMPLE_THREE_LETTER_PARAMETER = "abc";
	private static final String SAMPLE_TWO_LETTER_PARAMETER = "ab ";

	DrugSearchDTO drugSearchDTOMock;
	DrugSearchDTOValidator drugSearchDTOValidator;
	
	@Before
	public void init() {
		drugSearchDTOValidator = new DrugSearchDTOValidator();
		drugSearchDTOMock = mock(DrugSearchDTO.class);
	}

	@Test
	public void allowsFourLetterDrugName() {
		when(drugSearchDTOMock.getName()).thenReturn(SAMPLE_FOUR_LETTER_PARAMETER);

		try {
			drugSearchDTOValidator.validate(drugSearchDTOMock);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test
	public void allowsThreeLetterDrugName() {
		when(drugSearchDTOMock.getName()).thenReturn(SAMPLE_THREE_LETTER_PARAMETER);

		try {
			drugSearchDTOValidator.validate(drugSearchDTOMock);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}
	
	@Test(expected = DrugSearchException.class)
	public void disallowsTwoLetterDrugName() throws Exception {
		when(drugSearchDTOMock.getName()).thenReturn(SAMPLE_TWO_LETTER_PARAMETER);

		drugSearchDTOValidator.validate(drugSearchDTOMock);
	}

	@Test(expected = DrugSearchException.class)
	public void disallowsEmptyDrugName(){
		when(drugSearchDTOMock.getName()).thenReturn("");

		drugSearchDTOValidator.validate(drugSearchDTOMock);
	}

	@Test(expected = DrugSearchException.class)
	public void disallowsUnspecifiedDrugNameParameter() {
		drugSearchDTOValidator.validate(drugSearchDTOMock);
	}
}
