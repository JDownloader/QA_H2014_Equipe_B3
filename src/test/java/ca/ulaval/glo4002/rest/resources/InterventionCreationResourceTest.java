package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.services.InterventionService;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionCreationResourceTest {
	private String SAMPLE_EXCEPTION_CODE = "";
	private String SAMPLE_EXCEPTION_MESSAGE = "";

	private InterventionCreationResource interventionCreationResource;
	private ServiceRequestException domainException = new ServiceRequestException(SAMPLE_EXCEPTION_CODE, SAMPLE_EXCEPTION_MESSAGE);
	
	private InterventionCreationDTO interventionCreationDTOMock;
	private InterventionService interventionServiceMock;
	
	@Before
	public void initialize(){
		interventionCreationDTOMock = mock(InterventionCreationDTO.class);
		interventionServiceMock = mock(InterventionService.class);
		interventionCreationResource = new InterventionCreationResource(interventionServiceMock);
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
