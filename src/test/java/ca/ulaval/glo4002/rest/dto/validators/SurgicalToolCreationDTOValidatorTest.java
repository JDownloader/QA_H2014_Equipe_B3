package ca.ulaval.glo4002.rest.dto.validators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.intervention.InterventionType;
import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.*;

public class SurgicalToolCreationDTOValidatorTest {

	private static final String SAMPLE_TYPECODE_PARAMETER = "IT72353";
	private static final String SAMPLE_STATUT_PARAMETER = "INUTILISE";
	private static final String SAMPLE_NOSERIE_PARAMETER = "23562543-3635345";
	private static final String SAMPLE_ANONYMOUS_NOSERIE_PARAMETER = null;
	private static final InterventionType SAMPLE_INTERVENTIONTYPE_PARAMETER = InterventionType.AUTRE;
	private static final InterventionType SAMPLE_NONANONYMOUS_INTERVENTIONTYPE_PARAMETER = InterventionType.COEUR;

	SurgicalToolCreationDTO SurgicalToolCreationDTOMock;
	SurgicalToolCreationDTOValidator SurgicalToolCreationDTOValidator;

	@Before
	public void init() throws Exception {
		SurgicalToolCreationDTOValidator = new SurgicalToolCreationDTOValidator();
		SurgicalToolCreationDTOMock = mock(SurgicalToolCreationDTO.class);

		when(SurgicalToolCreationDTOMock.getTypeCode()).thenReturn(SAMPLE_TYPECODE_PARAMETER);
		when(SurgicalToolCreationDTOMock.getStatus()).thenReturn(SAMPLE_STATUT_PARAMETER);
		when(SurgicalToolCreationDTOMock.getSerialNumber()).thenReturn(SAMPLE_NOSERIE_PARAMETER);
		when(SurgicalToolCreationDTOMock.getInterventionType()).thenReturn(SAMPLE_INTERVENTIONTYPE_PARAMETER);

	}

	@Test(expected = SurgicalToolCreationException.class)
	public void disallowsEmptyTypecode() {
		when(SurgicalToolCreationDTOMock.getTypeCode()).thenReturn(null);

		SurgicalToolCreationDTOValidator.validate(SurgicalToolCreationDTOMock);
	}

	@Test
	public void validatingRequestWithoutSerialNumberDoesNotThrowAnException() {

		when(SurgicalToolCreationDTOMock.getSerialNumber()).thenReturn(null);

		try {
			SurgicalToolCreationDTOValidator.validate(SurgicalToolCreationDTOMock);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test
	public void validatingRequestWithAllParametersDoesNotThrowAnException() {

		try {
			SurgicalToolCreationDTOValidator.validate(SurgicalToolCreationDTOMock);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}

	}

}
