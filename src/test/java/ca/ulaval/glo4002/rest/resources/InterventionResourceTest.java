package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParserFactory;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.CreateSurgicalToolRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.CreateSurgicalToolRequestParserFactory;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.ModifySurgicalToolRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.ModifySurgicalToolRequestParserFactory;
import ca.ulaval.glo4002.rest.resources.InterventionResource;
import ca.ulaval.glo4002.rest.resources.InterventionResourceBuilder;
import ca.ulaval.glo4002.services.intervention.InterventionService;

import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class InterventionResourceTest {

	private static final String SAMPLE_JSON_REQUEST = "{attrib: value}";
	private static final int SAMPLE_INTERVENTION_NUMBER = 3;
	private static final String SAMPLE_SERIAL_NUMBER = "684518TF";
	private static final String SAMPLE_TYPE_CODE = "56465T";

	private InterventionService interventionServiceMock;
	private CreateInterventionRequestParser createInterventionRequestParserMock;
	private CreateInterventionRequestParserFactory createInterventionRequestParserFactoryMock;
	private CreateSurgicalToolRequestParser createSurgicalToolRequestParserMock;
	private CreateSurgicalToolRequestParserFactory createSurgicalToolRequestParserFactoryMock;
	private ModifySurgicalToolRequestParser modifySurgicalToolRequestParserMock;
	private ModifySurgicalToolRequestParserFactory modifySurgicalToolRequestParserFactoryMock;
	private InterventionResource interventionResource;

	@Before
	public void setup() throws Exception {
		createMocks();
		stubMethods();
		buildInterventionResource();
	}

	private void createMocks() {
		interventionServiceMock = mock(InterventionService.class);
		createInterventionRequestParserMock = mock(CreateInterventionRequestParser.class);
		createInterventionRequestParserFactoryMock = mock(CreateInterventionRequestParserFactory.class);
		createSurgicalToolRequestParserMock = mock(CreateSurgicalToolRequestParser.class);
		createSurgicalToolRequestParserFactoryMock = mock(CreateSurgicalToolRequestParserFactory.class);
		modifySurgicalToolRequestParserMock = mock(ModifySurgicalToolRequestParser.class);
		modifySurgicalToolRequestParserFactoryMock = mock(ModifySurgicalToolRequestParserFactory.class);
	}
	
	private void stubMethods() throws RequestParseException {
		when(createInterventionRequestParserFactoryMock.getParser(any(JSONObject.class))).thenReturn(createInterventionRequestParserMock);
		when(createSurgicalToolRequestParserFactoryMock.getParser(any(JSONObject.class))).thenReturn(createSurgicalToolRequestParserMock);
		when(modifySurgicalToolRequestParserFactoryMock.getParser(any(JSONObject.class))).thenReturn(modifySurgicalToolRequestParserMock);
	}
	
	private void buildInterventionResource() {
		InterventionResourceBuilder interventionResourceBuilder = new InterventionResourceBuilder();
		interventionResourceBuilder.createInterventionRequestParserFactory(createInterventionRequestParserFactoryMock);
		interventionResourceBuilder.modifySurgicalToolRequestParserFactory(modifySurgicalToolRequestParserFactoryMock);
		interventionResourceBuilder.createSurgicalToolRequestParserFactory(createSurgicalToolRequestParserFactoryMock);
		interventionResourceBuilder.service(interventionServiceMock);
		interventionResource = interventionResourceBuilder.build();
	}

	@Test
	public void verifyCreateInterventionCallsServiceMethodsCorrectly() throws ServiceRequestException {
		interventionResource.post(SAMPLE_JSON_REQUEST);
		verify(interventionServiceMock).createIntervention(createInterventionRequestParserMock);
	}

	@Test
	public void verifyCreateInterventionReturnsCreatedResponse() throws ServiceRequestException {
		Response response = interventionResource.post(SAMPLE_JSON_REQUEST);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyCreateInterventionReturnsInvalidResponseWhenSpecifyingInvalidRequest() throws ServiceRequestException {
		doThrow(new ServiceRequestException()).when(interventionServiceMock).createIntervention(createInterventionRequestParserMock);

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = interventionResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifyCreateSurgicalToolCallsServiceMethodsCorrectly() throws ServiceRequestException {
		interventionResource.createSurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER);
		verify(interventionServiceMock).createSurgicalTool(createSurgicalToolRequestParserMock);
	}

	@Test
	public void verifyCreateSurgicalToolReturnsCreatedResponse() throws ServiceRequestException {
		Response response = interventionResource.createSurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyCreateSurgicalToolReturnsInvalidResponseWhenSpecifyingInvalidRequest() throws ServiceRequestException {
		doThrow(new ServiceRequestException()).when(interventionServiceMock).createSurgicalTool(createSurgicalToolRequestParserMock);

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = interventionResource.createSurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifyModifySurgicalToolCallsServiceMethodsCorrectly() throws ServiceRequestException {
		interventionResource.modifySurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER, SAMPLE_TYPE_CODE, SAMPLE_SERIAL_NUMBER);
		verify(interventionServiceMock).modifySurgicalTool(modifySurgicalToolRequestParserMock);
	}

	@Test
	public void verifyModifySurgicalToolReturnsOkResponse() throws ServiceRequestException {
		Response response = interventionResource.modifySurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER, SAMPLE_TYPE_CODE, SAMPLE_SERIAL_NUMBER);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyModifySurgicalToolReturnsInvalidResponseWhenSpecifyingInvalidRequest() throws ServiceRequestException {
		doThrow(new ServiceRequestException()).when(interventionServiceMock).modifySurgicalTool(modifySurgicalToolRequestParserMock);

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = interventionResource.modifySurgicalTool(SAMPLE_JSON_REQUEST, SAMPLE_INTERVENTION_NUMBER, SAMPLE_TYPE_CODE, SAMPLE_SERIAL_NUMBER);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
}
