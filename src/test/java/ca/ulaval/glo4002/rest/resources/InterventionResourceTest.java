package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.intervention.InterventionNotFoundException;
import ca.ulaval.glo4002.domain.patient.PatientNotFoundException;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolExistsException;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolRequiresSerialNumberException;
import ca.ulaval.glo4002.services.InterventionService;
import ca.ulaval.glo4002.services.assemblers.InterventionAssembler;
import ca.ulaval.glo4002.services.assemblers.SurgicalToolAssembler;
import ca.ulaval.glo4002.services.dto.*;
import ca.ulaval.glo4002.services.dto.validators.*;

@RunWith(MockitoJUnitRunner.class)
public class InterventionResourceTest {
	private static final Integer SAMPLE_INTERVENTION_NUMBER_PARAMETER = 3;
	private static final String SAMPLE_SERIAL_NUMBER_PARAMETER = "23562543-3635345";
	private static final String SAMPLE_TYPECODE_PARAMETER = "IT72353";

	private InterventionService interventionServiceMock;
	private InterventionCreationDTO interventionCreationDTOMock;
	private SurgicalToolCreationDTO surgicalToolCreationDTOMock;
	private SurgicalToolModificationDTO surgicalToolModificationDTOMock;
	private InterventionResource interventionResource;

	@Before
	public void init() throws Exception {
		interventionServiceMock = mock(InterventionService.class);
		interventionCreationDTOMock = mock(InterventionCreationDTO.class);
		surgicalToolCreationDTOMock = mock(SurgicalToolCreationDTO.class);
		surgicalToolModificationDTOMock = mock(SurgicalToolModificationDTO.class);
		interventionResource = new InterventionResource(interventionServiceMock);
	}

	@Test
	public void verifyInterventionCreationCallsServiceMethodsCorrectly() throws Exception {
		createIntervention();
		verify(interventionServiceMock).createIntervention(eq(interventionCreationDTOMock), any(InterventionCreationDTOValidator.class),
				any(InterventionAssembler.class));
	}

	@Test()
	public void verifyInterventionCreationReturnsCreatedResponse() throws Exception {
		Response response = createIntervention();
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void verifyInterventionCreationReturnsBadRequestResponseOnDTOValidationException() throws Exception {
		doThrow(new DTOValidationException()).when(interventionServiceMock).createIntervention(eq(interventionCreationDTOMock), 
				any(InterventionCreationDTOValidator.class), any(InterventionAssembler.class));
		Response response = createIntervention();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyInterventionCreationReturnsBadRequestResponseOnPatientNotFoundException() throws Exception {
		doThrow(new PatientNotFoundException()).when(interventionServiceMock).createIntervention(eq(interventionCreationDTOMock), 
				any(InterventionCreationDTOValidator.class), any(InterventionAssembler.class));
		Response response = createIntervention();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifySurgicalToolCreationCallsServiceMethodsCorrectly() throws Exception {
		createSurgicalTool();
		verify(interventionServiceMock).createSurgicalTool(eq(surgicalToolCreationDTOMock), any(SurgicalToolCreationDTOValidator.class),
				any(SurgicalToolAssembler.class));
	}

	@Test
	public void verifySurgicalToolCreationReturnsCreatedResponse() throws Exception {
		Response response = createSurgicalTool();
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifySurgicalToolCreationReturnsBadRequestResponseOnDTOValidationException() throws Exception {
		doThrow(new DTOValidationException()).when(interventionServiceMock).createSurgicalTool(eq(surgicalToolCreationDTOMock),
				any(SurgicalToolCreationDTOValidator.class), any(SurgicalToolAssembler.class));
		Response receivedResponse = createSurgicalTool();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}

	@Test
	public void verifySurgicalToolCreationReturnsBadRequestResponseOnInterventionNotFoundException() throws Exception {
		doThrow(new InterventionNotFoundException()).when(interventionServiceMock).createSurgicalTool(eq(surgicalToolCreationDTOMock),
				any(SurgicalToolCreationDTOValidator.class), any(SurgicalToolAssembler.class));
		Response receivedResponse = createSurgicalTool();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}

	@Test
	public void verifySurgicalToolCreationReturnsBadRequestResponseOnSurgicalToolExistsException() throws Exception {
		doThrow(new SurgicalToolExistsException()).when(interventionServiceMock).createSurgicalTool(eq(surgicalToolCreationDTOMock),
				any(SurgicalToolCreationDTOValidator.class), any(SurgicalToolAssembler.class));
		Response receivedResponse = createSurgicalTool();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());

	}

	@Test
	public void verifySurgicalToolCreationReturnsBadRequestResponseOnSurgicalToolRequiresSerialNumberException() throws Exception {
		doThrow(new SurgicalToolRequiresSerialNumberException()).when(interventionServiceMock).createSurgicalTool(eq(surgicalToolCreationDTOMock),
				any(SurgicalToolCreationDTOValidator.class), any(SurgicalToolAssembler.class));
		Response receivedResponse = createSurgicalTool();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifySurgicalToolModificationCallsServiceMethodsCorrectly() throws Exception {
		modifySurgicalTool();
		verify(interventionServiceMock).modifySurgicalTool(eq(surgicalToolModificationDTOMock), any(SurgicalToolModificationDTOValidator.class));
	}

	@Test
	public void verifySurgicalToolModificationReturnsOkResponse() throws Exception {
		Response response = modifySurgicalTool();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void verifySurgicalToolModificationReturnsBadRequestResponseOnDTOValidationException() throws Exception {
		doThrow(new DTOValidationException()).when(interventionServiceMock).modifySurgicalTool(eq(surgicalToolModificationDTOMock),
				any(SurgicalToolModificationDTOValidator.class));
		Response receivedResponse = modifySurgicalTool();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}

	@Test
	public void verifySurgicalToolModificationReturnsBadRequestResponseOnInterventionNotFoundException() throws Exception {
		doThrow(new InterventionNotFoundException()).when(interventionServiceMock).modifySurgicalTool(eq(surgicalToolModificationDTOMock),
				any(SurgicalToolModificationDTOValidator.class));
		Response receivedResponse = modifySurgicalTool();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}

	@Test
	public void verifySurgicalToolModificationReturnsBadRequestResponseOnSurgicalToolExistsException() throws Exception {
		doThrow(new SurgicalToolExistsException()).when(interventionServiceMock).modifySurgicalTool(eq(surgicalToolModificationDTOMock),
				any(SurgicalToolModificationDTOValidator.class));
		Response receivedResponse = modifySurgicalTool();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}

	@Test
	public void verifySurgicalToolModificationReturnsBadRequestResponseOnSurgicalToolRequiresSerialNumberException() throws Exception {
		doThrow(new SurgicalToolRequiresSerialNumberException()).when(interventionServiceMock).modifySurgicalTool(eq(surgicalToolModificationDTOMock),
				any(SurgicalToolModificationDTOValidator.class));
		Response receivedResponse = modifySurgicalTool();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}

	private Response createIntervention() throws Exception {
		return interventionResource.createIntervention(interventionCreationDTOMock);
	}

	private Response createSurgicalTool() throws Exception {
		return interventionResource.createSurgicalTool(surgicalToolCreationDTOMock, SAMPLE_INTERVENTION_NUMBER_PARAMETER);
	}

	private Response modifySurgicalTool() throws Exception {
		return interventionResource.modifySurgicalTool(surgicalToolModificationDTOMock, SAMPLE_INTERVENTION_NUMBER_PARAMETER, SAMPLE_TYPECODE_PARAMETER,
				SAMPLE_SERIAL_NUMBER_PARAMETER);
	}
}
