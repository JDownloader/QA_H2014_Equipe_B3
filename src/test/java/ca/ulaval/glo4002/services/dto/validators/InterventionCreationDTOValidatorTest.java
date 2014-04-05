package ca.ulaval.glo4002.services.dto.validators;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionCreationDTOValidatorTest {
	
	private InterventionCreationDTOValidator interventionCreationDTOValidator = new InterventionCreationDTOValidator();

	private InterventionCreationDTO interventionCreationDTOMock;
	private static final Integer INVALID_PATIENT_NUMBER = -1;
	private static final String VALID_DESCRIPTION = "description";
	private static final String VALID_DATE = "0000-00-00T24:01:00";
	private static final Integer VALID_PATIENT_NUMBER = 1;
	private static final String VALID_ROOM = "blocB";
	private static final Integer VALID_SURGEON_NUMBER = 1;
	private static final String VALID_TYPE = "OEIL"; 
	
	@Before
	public void createDTO(){
		interventionCreationDTOMock = mock(InterventionCreationDTO.class);
		when(interventionCreationDTOMock.getDescription()).thenReturn(VALID_DESCRIPTION);
		when(interventionCreationDTOMock.getDate()).thenReturn(VALID_DATE);
		when(interventionCreationDTOMock.getPatientNumber()).thenReturn(VALID_PATIENT_NUMBER);
		when(interventionCreationDTOMock.getRoom()).thenReturn(VALID_ROOM);
		when(interventionCreationDTOMock.getSurgeonNumber()).thenReturn(VALID_SURGEON_NUMBER);
		when(interventionCreationDTOMock.getType()).thenReturn(VALID_TYPE);
	}
	
	@Test
	public void validateDTOReturnsBadRequestResponseWhenDescriptionIsNull(){
		when(interventionCreationDTOMock.getDescription()).thenReturn(null);
		interventionCreationDTOMock.getDescription();
		Response response = interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(Response.status(Status.BAD_REQUEST).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void validateDTOReturnsBadRequestResponseWhenDateIsNull(){
		when(interventionCreationDTOMock.getDate()).thenReturn(null);
		interventionCreationDTOMock.getDate();
		Response response = interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(Response.status(Status.BAD_REQUEST).build().getStatus(), response.getStatus());
	}
	
	@Test 
	public void validateDTOReturnsBadRequestResponseWhenPatientNumberIsNull(){
		when(interventionCreationDTOMock.getPatientNumber()).thenReturn(null);
		interventionCreationDTOMock.getPatientNumber();
		Response response = interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(Response.status(Status.BAD_REQUEST).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void validateDTOReturnsBadRequestResponseWhenRoomIsNull(){
		when(interventionCreationDTOMock.getRoom()).thenReturn(null);
		interventionCreationDTOMock.getRoom();
		Response response = interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(Response.status(Status.BAD_REQUEST).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void vaidateDTOReturnsBadRequestResponseWhenSurgeonNumberIsNull(){
		when(interventionCreationDTOMock.getSurgeonNumber()).thenReturn(null);
		interventionCreationDTOMock.getSurgeonNumber();
		Response response = interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(Response.status(Status.BAD_REQUEST).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void validateDTOReturnsBadRequestWhenTypeIsNull(){
		when(interventionCreationDTOMock.getType()).thenReturn(null);
		interventionCreationDTOMock.getType();
		Response response = interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(Response.status(Status.BAD_REQUEST).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void validateDTOReturnsBadRequestResponseWhenPatientNumberIsNegative(){
		when(interventionCreationDTOMock.getPatientNumber()).thenReturn(INVALID_PATIENT_NUMBER);
		interventionCreationDTOMock.getPatientNumber();
		Response response = interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(Response.status(Status.BAD_REQUEST).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void validateDTOReturnsAcceptedResponseWhenDescriptionIsValid(){
		interventionCreationDTOMock.getDescription();
		Response response = interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(Response.status(Status.ACCEPTED).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void validateDTOReturnsAcceptedResponseWhenDateIsValid(){
		interventionCreationDTOMock.getDate();
		Response response = interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(Response.status(Status.ACCEPTED).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void validateDTOReturnsAcceptedResponseWhenPatientNumberIsValid(){
		interventionCreationDTOMock.getPatientNumber();
		Response response = interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(Response.status(Status.ACCEPTED).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void validateDTOReturnsAcceptedResponseWhenRoomIsValid(){
		interventionCreationDTOMock.getRoom();
		Response response = interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(Response.status(Status.ACCEPTED).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void validateDTOReturnsAcceptedResponseWhenSurgeonNumberIsValid(){
		interventionCreationDTOMock.getSurgeonNumber();
		Response response = interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(Response.status(Status.ACCEPTED).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void validateDTOReturnsAcceptedResponseWhenTypeIsValid(){
		interventionCreationDTOMock.getType();
		Response response = interventionCreationDTOValidator.validateDTO(interventionCreationDTOMock);
		assertEquals(Response.status(Status.ACCEPTED).build().getStatus(), response.getStatus());
	}
}
