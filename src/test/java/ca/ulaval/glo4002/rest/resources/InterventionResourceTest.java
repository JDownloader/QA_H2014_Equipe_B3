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
	}

	private void stubMethods() throws RequestParseException {
		when(createInterventionRequestParserFactoryMock.getParser(any(JSONObject.class))).thenReturn(createInterventionRequestParserMock);
		when(createSurgicalToolRequestParserFactoryMock.getParser(any(JSONObject.class))).thenReturn(createSurgicalToolRequestParserMock);
		when(modifySurgicalToolRequestParserFactoryMock.getParser(any(JSONObject.class))).thenReturn(modifySurgicalToolRequestParserMock);
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
		doThrow(new ServiceRequestException()).when(interventionServiceMock).createIntervention(createInterventionRequestParserMock);

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = interventionResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifyCreateInterventionReturnsBadRequestResponseWhenSpecifyingInvalidRequestString() throws Exception {
		doThrow(new RequestParseException()).when(createInterventionRequestParserFactoryMock).getParser(any(JSONObject.class));

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
	public void verifyCreateSurgicalToolCallsServiceMethodsCorrectly() throws Exception {
		interventionResource.createSurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER);
		verify(interventionServiceMock).createSurgicalTool(createSurgicalToolRequestParserMock);
	}

	@Test
	public void verifyCreateSurgicalToolReturnsCreatedResponse() throws ServiceRequestException {
		Response response = interventionResource.createSurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyCreateSurgicalToolReturnsBadRequestResponseWhenSpecifyingInvalidRequest() throws Exception {
		doThrow(new ServiceRequestException()).when(interventionServiceMock).createSurgicalTool(createSurgicalToolRequestParserMock);

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = interventionResource.createSurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifyCreateSurgicalToolReturnsBadRequestResponseWhenSpecifyingInvalidRequestString() throws Exception {
		doThrow(new RequestParseException()).when(createSurgicalToolRequestParserFactoryMock).getParser(any(JSONObject.class));

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = interventionResource.createSurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER);
		
		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifyCreateSurgicalToolReturnsInternalServerErrorResponseOnUnhandledException() throws Exception {
		doThrow(new Exception()).when(interventionServiceMock).createSurgicalTool(createSurgicalToolRequestParserMock);

		Response expectedResponse = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		Response receivedResponse = interventionResource.createSurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER);
		
		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

	@Test
	public void verifyModifySurgicalToolCallsServiceMethodsCorrectly() throws Exception {
		interventionResource.modifySurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER, SAMPLE_TYPE_CODE, SAMPLE_SERIAL_NUMBER);
		verify(interventionServiceMock).modifySurgicalTool(modifySurgicalToolRequestParserMock);
	}

	@Test
	public void verifyModifySurgicalToolReturnsOkResponse() throws ServiceRequestException {
		Response response = interventionResource.modifySurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER, SAMPLE_TYPE_CODE, SAMPLE_SERIAL_NUMBER);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyModifySurgicalToolReturnsBadRequestResponseWhenSpecifyingInvalidRequest() throws Exception {
		doThrow(new ServiceRequestException()).when(interventionServiceMock).modifySurgicalTool(modifySurgicalToolRequestParserMock);

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = interventionResource.modifySurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER, SAMPLE_TYPE_CODE,
				SAMPLE_SERIAL_NUMBER);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifyModifySurgicalToolReturnsBadRequestResponseWhenSpecifyingInvalidRequestString() throws Exception {
		doThrow(new RequestParseException()).when(modifySurgicalToolRequestParserFactoryMock).getParser(any(JSONObject.class));

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = interventionResource.modifySurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER, SAMPLE_TYPE_CODE,
				SAMPLE_SERIAL_NUMBER);
		
		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifyModifySurgicalToolReturnsInternalServerErrorResponseOnUnhandledException() throws Exception {
		doThrow(new Exception()).when(interventionServiceMock).modifySurgicalTool(modifySurgicalToolRequestParserMock);

		Response expectedResponse = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		Response receivedResponse = interventionResource.modifySurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER, SAMPLE_TYPE_CODE,
				SAMPLE_SERIAL_NUMBER);
		
		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
}
