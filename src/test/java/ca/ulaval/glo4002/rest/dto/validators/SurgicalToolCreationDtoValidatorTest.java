package ca.ulaval.glo4002.rest.dto.validators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.*;

public class SurgicalToolCreationDtoValidatorTest {

	private static final String SAMPLE_TYPECODE_PARAMETER = "IT72353";
	private static final String SAMPLE_STATUT_PARAMETER = "INUTILISE";
	private static final String SAMPLE_NOSERIE_PARAMETER = "23562543-3635345";

	SurgicalToolCreationDTO SurgicalToolCreationDTOMock;
	SurgicalToolCreationDTOValidator SurgicalToolCreationDTOValidator;

	@Before
	public void init() throws Exception {
		SurgicalToolCreationDTOValidator = new SurgicalToolCreationDTOValidator();
		SurgicalToolCreationDTOMock = mock(SurgicalToolCreationDTO.class);

		when(SurgicalToolCreationDTOMock.getTypeCode()).thenReturn(SAMPLE_TYPECODE_PARAMETER);
		when(SurgicalToolCreationDTOMock.getStatut()).thenReturn(SAMPLE_STATUT_PARAMETER);
		when(SurgicalToolCreationDTOMock.getNoSerie()).thenReturn(SAMPLE_NOSERIE_PARAMETER);

	}

	@Test(expected = SurgicalToolCreationException.class)
	public void disallowsEmptyTypecode() {
		when(SurgicalToolCreationDTOMock.getTypeCode()).thenReturn(null);
		
		SurgicalToolCreationDTOValidator.validate(SurgicalToolCreationDTOMock);
	}

	@Test
	public void validatingRequestWithoutSerialNumberDoesNotThrowAnException() {

		when(SurgicalToolCreationDTOMock.getNoSerie()).thenReturn(null);

		try {
			SurgicalToolCreationDTOValidator.validate(SurgicalToolCreationDTOMock);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test
	public void validatingRequestWithAllParametersDoesNotThrowAnException() {

		when(SurgicalToolCreationDTOMock.getNoSerie()).thenReturn(null);

		try {
			SurgicalToolCreationDTOValidator.validate(SurgicalToolCreationDTOMock);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}
}
