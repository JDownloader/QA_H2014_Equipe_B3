package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.services.InterventionService;
import ca.ulaval.glo4002.services.assemblers.InterventionAssembler;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.InterventionCreationDTOValidator;

public class InterventionResourceTest {
	private InterventionResource interventionCreationResource;

	private InterventionCreationDTO interventionCreationDTOMock;
	private InterventionService interventionServiceMock;
	
	@Before
	public void initialize(){
		interventionCreationDTOMock = mock(InterventionCreationDTO.class);
		interventionServiceMock = mock(InterventionService.class);
		interventionCreationResource = new InterventionResource(interventionServiceMock);
	}
	
	@Test()
	public void verifySurgicalToolCreationReturnsCreatedResponse() throws Exception{
		Response response = interventionCreationResource.createIntervention(interventionCreationDTOMock);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	@Test()
	public void verifySurgicalToolCreationReturnsBadRequestResponseOnServiceRequestException() throws Exception{
		when(interventionServiceMock.createIntervention(eq(interventionCreationDTOMock), any(InterventionCreationDTOValidator.class), any(InterventionAssembler.class))).thenThrow(new ServiceRequestException());
		Response response = interventionCreationResource.createIntervention(interventionCreationDTOMock);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void verifyPrescriptionCreationReturnsBadRequestResponseOnServiceRequestException() throws Exception {
		doThrow(new ServiceRequestException()).when(interventionServiceMock).createIntervention(eq(interventionCreationDTOMock), any(InterventionCreationDTOValidator.class), any(InterventionAssembler.class));
		Response response = interventionCreationResource.createIntervention(interventionCreationDTOMock);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}
}
