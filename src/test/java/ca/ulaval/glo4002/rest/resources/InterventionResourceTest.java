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
	public void handlesPostRequestsCorrectly() throws ServiceRequestException {
		interventionResource.post(SAMPLE_JSON_REQUEST);
		verify(interventionServiceMock).createIntervention(createInterventionRequestParserMock);
	}

	@Test
	public void returnsCreatedResponse() throws ServiceRequestException {
		Response response = interventionResource.post(SAMPLE_JSON_REQUEST);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void returnsInvalidResponseWhenSpecifyingInvalidRequest() throws ServiceRequestException {
		doThrow(new ServiceRequestException()).when(interventionServiceMock).createIntervention(createInterventionRequestParserMock);

		Response response = interventionResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	//user story 3

//	private Response expectedResponse;
//	private Response receivedResponse;
//	private String request;
//
//	private String VALID_MARK_NEW_INSTRUMENT = "{\"typecode\": \"IT72353\", \"statut\": \"UTILISE_PATIENT\", \"noserie\" : \"23562543-3635345\"}";
//	private String VALID_MARK_EXISTING_INSTRUMENT = "{\"statut\": \"UTILISE_PATIENT\", \"noserie\" : \"23562543-3635345\"}";

//	@Mock
//	private SurgicalToolService service;
//	@Mock
//	private InterventionService interventionService;
//	@Mock
//	private MarkNewInstrumentRequestParser markNewInstrumentRequest;
//	@Mock
//	private MarkExistingInstrumentRequestParser markExistingInstrumentRequest;
//	@InjectMocks
//	private InterventionResource testServlet;
//
//	@Before
//	public void init() {
//		service = mock(SurgicalToolService.class);
//		testServlet = new InterventionResource(interventionService,service);
//		markExistingInstrumentRequest = mock(MarkExistingInstrumentRequestParser.class);
//		markNewInstrumentRequest = mock(MarkNewInstrumentRequestParser.class);
//	}
//
//	@Test
//	public void markNewInstrumentWhenValidRequest() {
//		request = VALID_MARK_NEW_INSTRUMENT;
//		expectedResponse = Response.status(Status.CREATED).build();
//
//		receivedResponse = testServlet.createSurgicalTool(request);
//
//		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
//	}
//
//	@Test
//	public void doNotMarkNewInstrumentWhenInvalidRequest() {
//		request = "{}";
//		expectedResponse = Response.status(Status.BAD_REQUEST).build();
//
//		receivedResponse = testServlet.createSurgicalTool(request);
//
//		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
//	}
//
//	@Test
//	public void markExistingInstrumentWhenValidRequest() {
//		request = "{\"statut\": \"UTILISE_PATIENT\", \"noserie\" : \"23562543-3635345\"}";
//		expectedResponse = Response.status(Status.BAD_REQUEST).build();
//
//		receivedResponse = testServlet.createSurgicalTool(request);
//
//		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
//	}
//
//	@Test
//	public void doNotMarkExistingInstrumentWhenInvalidRequest() {
//		request = "{}";
//		expectedResponse = Response.status(Status.BAD_REQUEST).build();
//
//		receivedResponse = testServlet.createSurgicalTool(request);
//
//		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
//	}
//
//	private ServiceRequestException noSerialNumber = new ServiceRequestException(
//			"INT011", "numéro de série déjà utilisé");
//
//	@Test
//	public void dontMarkInstrumentWithAlreadyUsedSerialNumber()
//			throws ServiceRequestException {
//		Mockito.doThrow(noSerialNumber).when(service)
//				.createSurgicalTool(Mockito.any(MarkNewInstrumentRequestParser.class));
//		request = VALID_MARK_NEW_INSTRUMENT;
//		expectedResponse = Response.status(Status.BAD_REQUEST).build();
//
//		receivedResponse = testServlet.createSurgicalTool(request);
//
//		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
//	}
//
//	private ServiceRequestException noAnonymousType = new ServiceRequestException(
//			"INT012", "requiert numéro de série");
//
//	@Test
//	public void dontMarkInstrumentWithNoAnonymousTypeWithoutSerialNumber()
//			throws ServiceRequestException {
//		Mockito.doThrow(noAnonymousType).when(service)
//				.createSurgicalTool(Mockito.any(MarkNewInstrumentRequestParser.class));
//		request = VALID_MARK_NEW_INSTRUMENT;
//		expectedResponse = Response.status(Status.BAD_REQUEST).build();
//
//		receivedResponse = testServlet.createSurgicalTool(request);
//
//		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
//	}
//
//	private ServiceRequestException doesNotExist = new ServiceRequestException(
//			"INT010", "données invalides ou incomplètes");
//
//	@Test
//	public void dontMarkExistingInstrumentIfDoesNotActuallyExistInDatabase()
//			throws ServiceRequestException {
//		Mockito.doThrow(doesNotExist)
//				.when(service)
//				.modifySurgicalTool(
//						Mockito.any(MarkExistingInstrumentRequestParser.class));
//		request = VALID_MARK_EXISTING_INSTRUMENT;
//		expectedResponse = Response.status(Status.BAD_REQUEST).build();
//
//		receivedResponse = testServlet.modifySurgicalTool(request);
//
//		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
//	}
}
