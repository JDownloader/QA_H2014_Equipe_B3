package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParserFactory;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.*;
import ca.ulaval.glo4002.services.assemblers.SurgicalToolAssembler;
import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;
import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolCreationDTOValidator;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolModificationDTOValidator;
import ca.ulaval.glo4002.services.intervention.InterventionService;

@RunWith(MockitoJUnitRunner.class)
public class InterventionResourceTest {

	private static final String SAMPLE_JSON_REQUEST = "{attrib: value}";
	private static final int SAMPLE_INTERVENTION_NUMBER = 3;
	private static final String SAMPLE_SERIAL_NUMBER = "684518TF";
	private static final String SAMPLE_TYPE_CODE = "56465T";

	private InterventionService interventionServiceMock;
	private CreateInterventionRequestParser createInterventionRequestParserMock;
	private CreateInterventionRequestParserFactory createInterventionRequestParserFactoryMock;
	private SurgicalToolCreationRequestParser createSurgicalToolRequestParserMock;
	private CreateSurgicalToolRequestParserFactory createSurgicalToolRequestParserFactoryMock;
	private SurgicalToolModificationRequestParser modifySurgicalToolRequestParserMock;
	private SurgicalToolModificationRequestParserFactory modifySurgicalToolRequestParserFactoryMock;
	private InterventionResource interventionResource;

	private SurgicalToolCreationDTO surgicalToolCreationDTOMock;
	private SurgicalToolModificationDTO surgicalToolModificationDTOMock;
	private SurgicalToolCreationDTOValidator surgicalToolCreationDTOValidatorMock;
	private SurgicalToolModificationDTOValidator surgicalToolModificationDTOValidatorMock;
	private SurgicalToolAssembler surgicalToolAssemblerMock;

	@Before
	public void init() throws Exception {
		createMocks();
		stubMethods();
		buildInterventionResource();
	}

	private void createMocks() {
		interventionServiceMock = mock(InterventionService.class);
		createInterventionRequestParserMock = mock(CreateInterventionRequestParser.class);
		createInterventionRequestParserFactoryMock = mock(CreateInterventionRequestParserFactory.class);
		createSurgicalToolRequestParserMock = mock(SurgicalToolCreationRequestParser.class);
		createSurgicalToolRequestParserFactoryMock = mock(CreateSurgicalToolRequestParserFactory.class);
		modifySurgicalToolRequestParserMock = mock(SurgicalToolModificationRequestParser.class);
		modifySurgicalToolRequestParserFactoryMock = mock(SurgicalToolModificationRequestParserFactory.class);

		surgicalToolCreationDTOMock = mock(SurgicalToolCreationDTO.class);
		surgicalToolModificationDTOMock = mock(SurgicalToolModificationDTO.class);
		surgicalToolCreationDTOValidatorMock = mock(SurgicalToolCreationDTOValidator.class);
		surgicalToolModificationDTOValidatorMock = mock(SurgicalToolModificationDTOValidator.class);
		surgicalToolAssemblerMock = mock(SurgicalToolAssembler.class);
	}

	private void stubMethods() throws RequestParseException {
		when(createInterventionRequestParserFactoryMock.getParser(any(JSONObject.class))).thenReturn(
				createInterventionRequestParserMock);
		when(createSurgicalToolRequestParserFactoryMock.getParser(any(JSONObject.class))).thenReturn(
				createSurgicalToolRequestParserMock);
		when(modifySurgicalToolRequestParserFactoryMock.getParser(any(JSONObject.class))).thenReturn(
				modifySurgicalToolRequestParserMock);
	}

	private void buildInterventionResource() {
		InterventionResourceBuilder interventionResourceBuilder = new InterventionResourceBuilder()
				.createInterventionRequestParserFactory(createInterventionRequestParserFactoryMock)
				.modifySurgicalToolRequestParserFactory(modifySurgicalToolRequestParserFactoryMock)
				.createSurgicalToolRequestParserFactory(createSurgicalToolRequestParserFactoryMock)
				.service(interventionServiceMock);
		interventionResource = interventionResourceBuilder.build();
	}

	@Test
	public void verifyCreateInterventionCallsServiceMethodsCorrectly() throws Exception {
		interventionResource.post(SAMPLE_JSON_REQUEST);
		verify(interventionServiceMock).createIntervention(createInterventionRequestParserMock);
	}

	@Test
	public void verifyCreateInterventionReturnsCreatedResponse() throws ServiceRequestException {
		Response response = interventionResource.post(SAMPLE_JSON_REQUEST);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyCreateInterventionReturnsInvalidResponseWhenSpecifyingInvalidRequest() throws Exception {
		doThrow(new ServiceRequestException()).when(interventionServiceMock).createIntervention(
				createInterventionRequestParserMock);

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = interventionResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

	@Test
	public void verifyCreateInterventionReturnsBadRequestResponseWhenSpecifyingInvalidRequestString() throws Exception {
		doThrow(new RequestParseException()).when(createInterventionRequestParserFactoryMock).getParser(
				any(JSONObject.class));

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = interventionResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

	@Test
	public void verifyCreateInterventionReturnsInternalServerErrorResponseOnUnhandledException() throws Exception {
		doThrow(new Exception()).when(interventionServiceMock).createIntervention(createInterventionRequestParserMock);

		Response expectedResponse = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		Response receivedResponse = interventionResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

	@Test
	public void verifyCreateSurgicalToolSetsInterventionNumberOnTheSurgicalToolCreationDTO() throws Exception {
		interventionResource.createSurgicalTool(surgicalToolCreationDTOMock, SAMPLE_INTERVENTION_NUMBER);
		verify(surgicalToolCreationDTOMock).setInterventionNumber(SAMPLE_INTERVENTION_NUMBER);
	}

	@Test
	public void verifyCreateSurgicalToolCallsServiceMethodsCorrectly() throws Exception {
		interventionResource.createSurgicalTool(surgicalToolCreationDTOMock, SAMPLE_INTERVENTION_NUMBER);
		verify(interventionServiceMock).createSurgicalTool(eq(surgicalToolCreationDTOMock),
				any(SurgicalToolCreationDTOValidator.class), any(SurgicalToolAssembler.class));
	}

	@Test
	public void verifySurgicalToolCreationReturnsCreatedResponse() throws Exception {
		Response response = interventionResource.createSurgicalTool(surgicalToolCreationDTOMock,
				SAMPLE_INTERVENTION_NUMBER);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyPrescriptionCreationReturnsBadRequestResponseOnServiceRequestException() throws Exception {
		doThrow(new ServiceRequestException()).when(interventionServiceMock).createSurgicalTool(
				eq(surgicalToolCreationDTOMock), any(SurgicalToolCreationDTOValidator.class),
				any(SurgicalToolAssembler.class));

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = interventionResource.createSurgicalTool(surgicalToolCreationDTOMock,
				SAMPLE_INTERVENTION_NUMBER);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

	@Test
	public void verifyModifySurgicalToolSetsInterventionNumberOnTheSurgicalToolCreationDTO() throws Exception {
		interventionResource.modifySurgicalTool(surgicalToolModificationDTOMock, SAMPLE_INTERVENTION_NUMBER,
				SAMPLE_TYPE_CODE, SAMPLE_SERIAL_NUMBER);
		verify(surgicalToolModificationDTOMock).setInterventionNumber(SAMPLE_INTERVENTION_NUMBER);
	}

	@Test
	public void verifyModifySurgicalToolCallsServiceMethodsCorrectly() throws Exception {
		interventionResource.modifySurgicalTool(surgicalToolModificationDTOMock, SAMPLE_INTERVENTION_NUMBER,
				SAMPLE_TYPE_CODE, SAMPLE_SERIAL_NUMBER);
		verify(interventionServiceMock).modifySurgicalTool(eq(surgicalToolModificationDTOMock),
				any(SurgicalToolModificationDTOValidator.class));
	}

	
	@Test
	public void verifySurgicalToolModificationReturnsCreatedResponse() throws Exception {
		Response response = interventionResource.modifySurgicalTool(surgicalToolModificationDTOMock,
				SAMPLE_INTERVENTION_NUMBER, SAMPLE_TYPE_CODE, SAMPLE_SERIAL_NUMBER);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyModifyCreationReturnsBadRequestResponseOnServiceRequestException() throws Exception {
		doThrow(new ServiceRequestException()).when(interventionServiceMock).modifySurgicalTool(
				eq(surgicalToolModificationDTOMock), any(SurgicalToolModificationDTOValidator.class));

		Response expectedResponse = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		Response receivedResponse = interventionResource.modifySurgicalTool(surgicalToolModificationDTOMock,
				SAMPLE_INTERVENTION_NUMBER, SAMPLE_TYPE_CODE, SAMPLE_SERIAL_NUMBER);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
}
