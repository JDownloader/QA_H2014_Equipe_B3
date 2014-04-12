package ca.ulaval.glo4002.services.dto.validators;

import java.text.ParseException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.exceptions.InterventionValidationException;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;
import ca.ulaval.glo4002.utils.DateParser;

public class InterventionCreationDTOValidatorTest {
	
	private InterventionCreationDTOValidator interventionCreationDTOValidator = new InterventionCreationDTOValidator();
	private InterventionValidationException interventionValidationException = new InterventionValidationException();
	
	private InterventionCreationDTO interventionCreationDTOMock;
	private static final Integer INVALID_PATIENT_NUMBER = -1;
	private static final String VALID_DESCRIPTION = "description";
	private static final String VALID_DATE = "2001-07-04T12:08:56";
	private static final Integer VALID_PATIENT_NUMBER = 1;
	private static final String VALID_ROOM = "blocB";
	private static final Integer VALID_SURGEON_NUMBER = 1;
	private static final String VALID_TYPE = "OEIL"; 
	
	@Before
	public void createDTO() throws ParseException{
		interventionCreationDTOMock = mock(InterventionCreationDTO.class);
		when(interventionCreationDTOMock.getDescription()).thenReturn(VALID_DESCRIPTION);
		when(interventionCreationDTOMock.getDate()).thenReturn(DateParser.parseDate(VALID_DATE));
		when(interventionCreationDTOMock.getPatientNumber()).thenReturn(VALID_PATIENT_NUMBER);
		when(interventionCreationDTOMock.getRoom()).thenReturn(VALID_ROOM);
		when(interventionCreationDTOMock.getSurgeonNumber()).thenReturn(VALID_SURGEON_NUMBER);
		when(interventionCreationDTOMock.getType()).thenReturn(VALID_TYPE);
	}
	
	@Test(expected=InterventionValidationException.class)
	public void validateDTOReturnsBadRequestResponseWhenDescriptionIsNull() throws InterventionValidationException{
		when(interventionCreationDTOMock.getDescription()).thenReturn(null);
		interventionCreationDTOMock.getDescription();
		interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(InterventionValidationException.class, interventionValidationException.getCause().getClass());
		assertEquals("Parameter 'description' is required", interventionValidationException.getMessage());
	}
	
	@Test(expected=InterventionValidationException.class)
	public void validateDTOReturnsBadRequestResponseWhenDateIsNull() throws InterventionValidationException{
		when(interventionCreationDTOMock.getDate()).thenReturn(null);
		interventionCreationDTOMock.getDate();
		interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(InterventionValidationException.class, interventionValidationException.getCause().getClass());
		assertEquals("Parameter 'date' is required", interventionValidationException.getMessage());
	}
	
	@Test(expected=InterventionValidationException.class)
	public void validateDTOReturnsBadRequestResponseWhenPatientNumberIsNull() throws InterventionValidationException{
		when(interventionCreationDTOMock.getPatientNumber()).thenReturn(null);
		interventionCreationDTOMock.getPatientNumber();
		interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(InterventionValidationException.class, interventionValidationException.getCause().getClass());
		assertEquals("Parameter 'patient' is required", interventionValidationException.getMessage());
	}
	
	@Test(expected=InterventionValidationException.class)
	public void validateDTOReturnsBadRequestResponseWhenRoomIsNull() throws InterventionValidationException{
		when(interventionCreationDTOMock.getRoom()).thenReturn(null);
		interventionCreationDTOMock.getRoom();
		interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(InterventionValidationException.class, interventionValidationException.getCause().getClass());
		assertEquals("Parameter 'salle' is required", interventionValidationException.getMessage());
	}
	
	@Test(expected=InterventionValidationException.class)
	public void vaidateDTOReturnsBadRequestResponseWhenSurgeonNumberIsNull() throws InterventionValidationException{
		when(interventionCreationDTOMock.getSurgeonNumber()).thenReturn(null);
		interventionCreationDTOMock.getSurgeonNumber();
		interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(InterventionValidationException.class, interventionValidationException.getCause().getClass());
		assertEquals("Parameter 'chirurgien' is required", interventionValidationException.getMessage());
	}
	
	@Test(expected=InterventionValidationException.class)
	public void validateDTOReturnsBadRequestWhenTypeIsNull() throws InterventionValidationException{
		when(interventionCreationDTOMock.getType()).thenReturn(null);
		interventionCreationDTOMock.getType();
		interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(InterventionValidationException.class, interventionValidationException.getCause().getClass());
		assertEquals("Parameter 'type' is required", interventionValidationException.getMessage());
	}
	
	@Test(expected=InterventionValidationException.class)
	public void validateDTOReturnsBadRequestResponseWhenPatientNumberIsNegative() throws InterventionValidationException{
		when(interventionCreationDTOMock.getPatientNumber()).thenReturn(INVALID_PATIENT_NUMBER);
		interventionCreationDTOMock.getPatientNumber();
		interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(InterventionValidationException.class, interventionValidationException.getCause().getClass());
		assertEquals("Parameter 'patient' must be greater than 0", interventionValidationException.getMessage());
	}
}