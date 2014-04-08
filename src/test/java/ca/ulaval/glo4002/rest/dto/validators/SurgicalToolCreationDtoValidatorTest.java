package ca.ulaval.glo4002.rest.dto.validators;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.*;

public class SurgicalToolCreationDtoValidatorTest {


	private static final String SAMPLE_TYPECODE_PARAMETER = "IT72353";
	private static final String SAMPLE_STATUT_PARAMETER = "INUTILISE";
	
	SurgicalToolCreationDTO SurgicalToolCreationDTOMock;
	SurgicalToolCreationDTOValidator SurgicalToolCreationDTOValidator;
	
	
	@Before
	public void init() throws Exception {
		SurgicalToolCreationDTOValidator = new SurgicalToolCreationDTOValidator();
		SurgicalToolCreationDTOMock = mock(SurgicalToolCreationDTO.class);

		when(SurgicalToolCreationDTOMock.getTypeCode()).thenReturn(SAMPLE_TYPECODE_PARAMETER);
		when(SurgicalToolCreationDTOMock.getTypeCode()).thenReturn(SAMPLE_STATUT_PARAMETER);

	}
	
	@Test(expected = SurgicalToolCreationException.class)
	public void disallowsEmptyTypecode(){
		when(SurgicalToolCreationDTOMock.getTypeCode()).thenReturn(null);
		SurgicalToolCreationDTOValidator.validate(SurgicalToolCreationDTOMock);
		
		//TODO: pas de assert donc la structure des tests en 3 parties n'est pas respectée?
	}
	
	
}
