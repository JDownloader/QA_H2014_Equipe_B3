package ca.ulaval.glo4002.rest;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParserFactory;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.MarkExistingInstrumentRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.MarkNewInstrumentRequestParser;
import ca.ulaval.glo4002.services.intervention.InterventionService;
import ca.ulaval.glo4002.services.surgicaltool.SurgicalToolService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class InterventionResourceTest {

	private static final String SAMPLE_JSON_REQUEST = "{attrib: value}";

	private InterventionService interventionServiceMock;
	private CreateInterventionRequestParser createInterventionRequestMock;
	private CreateInterventionRequestParserFactory createInterventionRequestFactoryMock;
	private InterventionResource interventionResource;

	@Before
	public void setup() throws Exception {
		interventionServiceMock = mock(InterventionService.class);
		createInterventionRequestMock = mock(CreateInterventionRequestParser.class);
		createInterventionRequestFactoryMock = mock(CreateInterventionRequestParserFactory.class);
		when(createInterventionRequestFactoryMock.getParser(any(JSONObject.class))).thenReturn(createInterventionRequestMock);
		interventionResource = new InterventionResource(interventionServiceMock, createInterventionRequestFactoryMock);
	}

	@Test
	public void handlesPostRequestsCorrectly() throws ServiceRequestException {
		interventionResource.post(SAMPLE_JSON_REQUEST);
		verify(interventionServiceMock).createIntervention(createInterventionRequestMock);
	}

	@Test
	public void returnsCreatedResponse() throws ServiceRequestException {
		Response response = interventionResource.post(SAMPLE_JSON_REQUEST);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void returnsInvalidResponseWhenSpecifyingInvalidRequest() throws ServiceRequestException {
		doThrow(new ServiceRequestException()).when(interventionServiceMock).createIntervention(createInterventionRequestMock);

		Response response = interventionResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	//user story 3

	private Response expectedResponse;
	private Response receivedResponse;
	private String request;

	private String VALID_MARK_NEW_INSTRUMENT = "{\"typecode\": \"IT72353\", \"statut\": \"UTILISE_PATIENT\", \"noserie\" : \"23562543-3635345\"}";
	private String VALID_MARK_EXISTING_INSTRUMENT = "{\"statut\": \"UTILISE_PATIENT\", \"noserie\" : \"23562543-3635345\"}";

	@Mock
	private SurgicalToolService service;
	@Mock
	private InterventionService interventionService;
	@Mock
	private MarkNewInstrumentRequestParser markNewInstrumentRequest;
	@Mock
	private MarkExistingInstrumentRequestParser markExistingInstrumentRequest;
	@InjectMocks
	private InterventionResource testServlet;

	@Before
	public void init() {
		service = mock(SurgicalToolService.class);
		testServlet = new InterventionResource(interventionService,service);
		markExistingInstrumentRequest = mock(MarkExistingInstrumentRequestParser.class);
		markNewInstrumentRequest = mock(MarkNewInstrumentRequestParser.class);
	}

	@Test
	public void markNewInstrumentWhenValidRequest() {
		request = VALID_MARK_NEW_INSTRUMENT;
		expectedResponse = Response.status(Status.CREATED).build();

		receivedResponse = testServlet.markNewInstrument(request);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

	@Test
	public void doNotMarkNewInstrumentWhenInvalidRequest() {
		request = "{}";
		expectedResponse = Response.status(Status.BAD_REQUEST).build();

		receivedResponse = testServlet.markNewInstrument(request);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

	@Test
	public void markExistingInstrumentWhenValidRequest() {
		request = "{\"statut\": \"UTILISE_PATIENT\", \"noserie\" : \"23562543-3635345\"}";
		expectedResponse = Response.status(Status.BAD_REQUEST).build();

		receivedResponse = testServlet.markNewInstrument(request);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

	@Test
	public void doNotMarkExistingInstrumentWhenInvalidRequest() {
		request = "{}";
		expectedResponse = Response.status(Status.BAD_REQUEST).build();

		receivedResponse = testServlet.markNewInstrument(request);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

	private ServiceRequestException noSerialNumber = new ServiceRequestException(
			"INT011", "numéro de série déjà utilisé");

	@Test
	public void dontMarkInstrumentWithAlreadyUsedSerialNumber()
			throws ServiceRequestException {
		Mockito.doThrow(noSerialNumber).when(service)
				.markNewInstrument(Mockito.any(MarkNewInstrumentRequestParser.class));
		request = VALID_MARK_NEW_INSTRUMENT;
		expectedResponse = Response.status(Status.BAD_REQUEST).build();

		receivedResponse = testServlet.markNewInstrument(request);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

	private ServiceRequestException noAnonymousType = new ServiceRequestException(
			"INT012", "requiert numéro de série");

	@Test
	public void dontMarkInstrumentWithNoAnonymousTypeWithoutSerialNumber()
			throws ServiceRequestException {
		Mockito.doThrow(noAnonymousType).when(service)
				.markNewInstrument(Mockito.any(MarkNewInstrumentRequestParser.class));
		request = VALID_MARK_NEW_INSTRUMENT;
		expectedResponse = Response.status(Status.BAD_REQUEST).build();

		receivedResponse = testServlet.markNewInstrument(request);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

	private ServiceRequestException doesNotExist = new ServiceRequestException(
			"INT010", "données invalides ou incomplètes");

	@Test
	public void dontMarkExistingInstrumentIfDoesNotActuallyExistInDatabase()
			throws ServiceRequestException {
		Mockito.doThrow(doesNotExist)
				.when(service)
				.markExistingInstrument(
						Mockito.any(MarkExistingInstrumentRequestParser.class));
		request = VALID_MARK_EXISTING_INSTRUMENT;
		expectedResponse = Response.status(Status.BAD_REQUEST).build();

		receivedResponse = testServlet.markExistingInstrument(request);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
}
