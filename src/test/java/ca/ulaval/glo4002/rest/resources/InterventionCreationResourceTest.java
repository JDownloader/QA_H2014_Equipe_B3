package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.exceptions.domainexceptions.DomainException;
import ca.ulaval.glo4002.exceptions.domainexceptions.interventionexceptions.PatientDoesNotExist;
import ca.ulaval.glo4002.services.InterventionService;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionCreationResourceTest {

	private InterventionCreationResource interventionCreationResource;
	private DomainException domainException = new PatientDoesNotExist();
	
	private InterventionCreationDTO interventionCreationDTOMock;
	private InterventionService interventionServiceMock;
	
	private static final String VALID_DESCRIPTION = "description";
	private static final String VALID_DATE = "0000-00-00T24:01:00";
	private static final Integer VALID_PATIENT_NUMBER = 1;
	private static final String VALID_ROOM = "blocB";
	private static final Integer VALID_SURGEON_NUMBER = 1;
	private static final String VALID_TYPE = "OEIL"; 
	
	@Before
	public void initialize(){
		interventionCreationDTOMock = mock(InterventionCreationDTO.class);
		interventionServiceMock = mock(InterventionService.class);
		interventionCreationResource = new InterventionCreationResource(interventionServiceMock);
		when(interventionCreationDTOMock.getDescription()).thenReturn(VALID_DESCRIPTION);
		when(interventionCreationDTOMock.getDate()).thenReturn(VALID_DATE);
		when(interventionCreationDTOMock.getPatientNumber()).thenReturn(VALID_PATIENT_NUMBER);
		when(interventionCreationDTOMock.getRoom()).thenReturn(VALID_ROOM);
		when(interventionCreationDTOMock.getSurgeonNumber()).thenReturn(VALID_SURGEON_NUMBER);
		when(interventionCreationDTOMock.getType()).thenReturn(VALID_TYPE);
	}
	
	@Test()
	public void postReturnsCreatedSuccessResponseWhenServiceCreatesIntervention() throws ServiceRequestException{
		Response response = interventionCreationResource.post(interventionCreationDTOMock);
		verify(interventionServiceMock).createIntervention(interventionCreationDTOMock);
		assertEquals(Response.status(Status.CREATED).build().getStatus(), response.getStatus());
	}
	
	@Test()
	public void postReturnsBadRequestReponseWhenServiceDoesNotCreateIntervention() throws ServiceRequestException{
		when(interventionServiceMock.createIntervention(interventionCreationDTOMock)).thenThrow(domainException);
		Response response = interventionCreationResource.post(interventionCreationDTOMock);
		verify(interventionServiceMock).createIntervention(interventionCreationDTOMock);
		assertEquals(Response.status(Status.BAD_REQUEST).build().getStatus(), response.getStatus());
	}
}
