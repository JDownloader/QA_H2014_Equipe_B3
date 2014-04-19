package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolFactory;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;
import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolCreationDTOValidator;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolModificationDTOValidator;
import ca.ulaval.glo4002.services.intervention.InterventionService;

@RunWith(MockitoJUnitRunner.class)
public class InterventionResourceTest {
	private static final Integer SAMPLE_INTERVENTION_NUMBER_PARAMETER = 3;
	private static final String SAMPLE_SERIAL_NUMBER_PARAMETER = "23562543-3635345";
	private static final String SAMPLE_TYPECODE_PARAMETER = "IT72353";
	
	private InterventionService interventionServiceMock;
	private SurgicalToolCreationDTO surgicalToolCreationDTOMock;
	private SurgicalToolModificationDTO surgicalToolModificationDTOMock;
	private InterventionResource interventionResource;
	
	@Before
	public void init() throws Exception {
		interventionServiceMock = mock(InterventionService.class);
		surgicalToolCreationDTOMock = mock(SurgicalToolCreationDTO.class);
		surgicalToolModificationDTOMock = mock(SurgicalToolModificationDTO.class);
		interventionResource = new InterventionResource(interventionServiceMock);
	}

	@Test
	public void verifySurgicalToolCreationCallsServiceMethodsCorrectly() throws Exception {
		interventionResource.createSurgicalTool(surgicalToolCreationDTOMock, SAMPLE_INTERVENTION_NUMBER_PARAMETER);
		verify(interventionServiceMock).createSurgicalTool(eq(surgicalToolCreationDTOMock), any(SurgicalToolCreationDTOValidator.class), any(SurgicalToolFactory.class));
	}

	@Test
	public void verifySurgicalToolCreationReturnsCreatedResponse() throws Exception {
		Response response = interventionResource.createSurgicalTool(surgicalToolCreationDTOMock, SAMPLE_INTERVENTION_NUMBER_PARAMETER);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifySurgicalToolCreationReturnsBadRequestResponseOnServiceRequestException() throws Exception {
		doThrow(new ServiceRequestException()).when(interventionServiceMock).createSurgicalTool(eq(surgicalToolCreationDTOMock), any(SurgicalToolCreationDTOValidator.class), any(SurgicalToolFactory.class));
		Response receivedResponse = interventionResource.createSurgicalTool(surgicalToolCreationDTOMock, SAMPLE_INTERVENTION_NUMBER_PARAMETER);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifySurgicalToolModificationCallsServiceMethodsCorrectly() throws Exception {
		interventionResource.modifySurgicalTool(surgicalToolModificationDTOMock, SAMPLE_INTERVENTION_NUMBER_PARAMETER, SAMPLE_TYPECODE_PARAMETER, SAMPLE_SERIAL_NUMBER_PARAMETER);
		verify(interventionServiceMock).modifySurgicalTool(eq(surgicalToolModificationDTOMock), any(SurgicalToolModificationDTOValidator.class));
	}

	@Test
	public void verifySurgicalToolModificationReturnsOkResponse() throws Exception {
		Response response = interventionResource.modifySurgicalTool(surgicalToolModificationDTOMock, SAMPLE_INTERVENTION_NUMBER_PARAMETER, SAMPLE_TYPECODE_PARAMETER, SAMPLE_SERIAL_NUMBER_PARAMETER);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifySurgicalToolModificationReturnsBadRequestResponseOnServiceRequestException() throws Exception {
		doThrow(new ServiceRequestException()).when(interventionServiceMock).modifySurgicalTool(eq(surgicalToolModificationDTOMock), any(SurgicalToolModificationDTOValidator.class));
		Response receivedResponse = interventionResource.modifySurgicalTool(surgicalToolModificationDTOMock, SAMPLE_INTERVENTION_NUMBER_PARAMETER, SAMPLE_TYPECODE_PARAMETER, SAMPLE_SERIAL_NUMBER_PARAMETER);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}
}
