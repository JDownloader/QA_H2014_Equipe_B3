package ca.ulaval.glo4002.rest.dto.validators;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolModificationDTOValidator;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolModificationException;

public class SurgicalToolModificationDTOValidatorTest {

	private static final String SAMPLE_STATUT_PARAMETER = "INUTILISE";
	private static final String SAMPLE_NOSERIE_PARAMETER = "23562543-3635345";
	SurgicalToolModificationDTO surgicalToolModificationDTOMock;
	SurgicalToolModificationDTOValidator surgicalToolModificationDTOValidator;

	@Before
	public void init() throws Exception {
		surgicalToolModificationDTOValidator = new SurgicalToolModificationDTOValidator();
		surgicalToolModificationDTOMock = mock(SurgicalToolModificationDTO.class);

		when(surgicalToolModificationDTOMock.getNewStatus()).thenReturn(SAMPLE_STATUT_PARAMETER);
		when(surgicalToolModificationDTOMock.getNewSerialNumber()).thenReturn(SAMPLE_NOSERIE_PARAMETER);

	}

	@Test
	public void validatingRequestWithoutSerialNumberDoesNotThrowAnException() {

		when(surgicalToolModificationDTOMock.getNewSerialNumber()).thenReturn(null);

		try {
			surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTOMock);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test
	public void validatingRequestWithoutStatusDoesNotThrowAnException() {

		when(surgicalToolModificationDTOMock.getNewStatus()).thenReturn(null);

		try {
			surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTOMock);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test(expected = SurgicalToolModificationException.class)
	public void validatingRequestWithoutStatusAndSerialNumberThrowsAnException() {

		when(surgicalToolModificationDTOMock.getNewStatus()).thenReturn(null);
		when(surgicalToolModificationDTOMock.getNewSerialNumber()).thenReturn(null);

		surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTOMock);

	}

	@Test
	public void validatingRequestWithAllParametersDoesNotThrowAnException() {

		try {
			surgicalToolModificationDTOValidator.validate(surgicalToolModificationDTOMock);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}

	}

}
