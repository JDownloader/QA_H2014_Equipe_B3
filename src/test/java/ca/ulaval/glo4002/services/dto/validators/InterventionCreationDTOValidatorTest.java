package ca.ulaval.glo4002.services.dto.validators;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.exceptions.domainexceptions.InvalidArgument;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;
import ca.ulaval.glo4002.utils.DateParser;

public class InterventionCreationDTOValidatorTest {
	private String SAMPLE_CODE = "";
	private String SAMPLE_MESSAGE = "";
	private InterventionCreationDTOValidator interventionCreationDTOValidator = new InterventionCreationDTOValidator();
	private InvalidArgument interventionValidationException = new InvalidArgument(SAMPLE_CODE,SAMPLE_MESSAGE);
	
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
	
	@Test(expected=InvalidArgument.class)
	public void validateDTOReturnsBadRequestResponseWhenDescriptionIsNull() {
		when(interventionCreationDTOMock.getDescription()).thenReturn(null);
		interventionCreationDTOMock.getDescription();
		interventionCreationDTOValidator.validate(interventionCreationDTOMock);
		assertEquals(InvalidArgument.class, interventionValidationException.getClass());
	}
	
	@Test(expected=InvalidArgument.class)
	public void validateDTOReturnsBadRequestResponseWhenDateIsNull() {
		when(interventionCreationDTOMock.getDate()).thenReturn(null);
		interventionCreationDTOMock.getDate();
		interventionCreationDTOValidator.validate(interventionCreationDTOMock);
		assertEquals(InvalidArgument.class, interventionValidationException.getCause().getClass());
		assertEquals("Parameter 'date' is required", interventionValidationException.getMessage());
	}
	
	@Test(expected=InvalidArgument.class)
	public void validateDTOReturnsBadRequestResponseWhenPatientNumberIsNull() {
		when(interventionCreationDTOMock.getPatientNumber()).thenReturn(null);
		interventionCreationDTOMock.getPatientNumber();
		interventionCreationDTOValidator.validate(interventionCreationDTOMock);
		assertEquals(InvalidArgument.class, interventionValidationException.getCause().getClass());
		assertEquals("Parameter 'patient' is required", interventionValidationException.getMessage());
	}
	
	@Test(expected=InvalidArgument.class)
	public void validateDTOReturnsBadRequestResponseWhenRoomIsNull() {
		when(interventionCreationDTOMock.getRoom()).thenReturn(null);
		interventionCreationDTOMock.getRoom();
		interventionCreationDTOValidator.validate(interventionCreationDTOMock);
		assertEquals(InvalidArgument.class, interventionValidationException.getCause().getClass());
		assertEquals("Parameter 'salle' is required", interventionValidationException.getMessage());
	}
	
	@Test(expected=InvalidArgument.class)
	public void vaidateDTOReturnsBadRequestResponseWhenSurgeonNumberIsNull() {
		when(interventionCreationDTOMock.getSurgeonNumber()).thenReturn(null);
		interventionCreationDTOMock.getSurgeonNumber();
		interventionCreationDTOValidator.validate(interventionCreationDTOMock);
		assertEquals(InvalidArgument.class, interventionValidationException.getCause().getClass());
		assertEquals("Parameter 'chirurgien' is required", interventionValidationException.getMessage());
	}
	
	@Test(expected=InvalidArgument.class)
	public void validateDTOReturnsBadRequestWhenTypeIsNull() {
		when(interventionCreationDTOMock.getType()).thenReturn(null);
		interventionCreationDTOMock.getType();
		interventionCreationDTOValidator.validate(interventionCreationDTOMock);
		assertEquals(InvalidArgument.class, interventionValidationException.getCause().getClass());
		assertEquals("Parameter 'type' is required", interventionValidationException.getMessage());
	}
	
	@Test(expected=InvalidArgument.class)
	public void validateDTOReturnsBadRequestResponseWhenPatientNumberIsNegative() {
		when(interventionCreationDTOMock.getPatientNumber()).thenReturn(INVALID_PATIENT_NUMBER);
		interventionCreationDTOMock.getPatientNumber();
		interventionCreationDTOValidator.validate(interventionCreationDTOMock);
		assertEquals(InvalidArgument.class, interventionValidationException.getCause().getClass());
		assertEquals("Parameter 'patient' must be greater than 0", interventionValidationException.getMessage());
	}
}