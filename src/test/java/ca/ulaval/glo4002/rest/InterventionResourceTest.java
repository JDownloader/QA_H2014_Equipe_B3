package ca.ulaval.glo4002.rest;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.CreateInterventionRequest;
import ca.ulaval.glo4002.rest.requests.CreateInterventionRequestFactory;
import ca.ulaval.glo4002.services.intervention.InterventionService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.rest.requests.MarkExistingInstrumentRequest;
import ca.ulaval.glo4002.rest.requests.MarkNewInstrumentRequest;


@RunWith(MockitoJUnitRunner.class)
public class InterventionResourceTest {

	private static final String SAMPLE_JSON_REQUEST = "{attrib: value}";

	private InterventionService interventionServiceMock;
	private CreateInterventionRequest createInterventionRequestMock;
	private CreateInterventionRequestFactory createInterventionRequestFactoryMock;
	private InterventionResource interventionResource;

	@Before
	public void setup() throws Exception {
		interventionServiceMock = mock(InterventionService.class);
		createInterventionRequestMock = mock(CreateInterventionRequest.class);
		createInterventionRequestFactoryMock = mock(CreateInterventionRequestFactory.class);
		when(createInterventionRequestFactoryMock.createCreateInterventionRequest(any(JSONObject.class))).thenReturn(createInterventionRequestMock);
		interventionResource = new InterventionResource(interventionServiceMock, createInterventionRequestFactoryMock);
	}

	@Test
	public void handlesPostRequestsCorrectly() throws BadRequestException {
		interventionResource.post(SAMPLE_JSON_REQUEST);
		verify(interventionServiceMock).createIntervention(createInterventionRequestMock);
	}

	@Test
	public void returnsCreatedResponse() throws BadRequestException {
		Response response = interventionResource.post(SAMPLE_JSON_REQUEST);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void returnsInvalidResponseWhenSpecifyingInvalidRequest() throws BadRequestException {
		doThrow(new BadRequestException()).when(interventionServiceMock).createIntervention(createInterventionRequestMock);

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
	private InterventionService service;
	@Mock
	private MarkNewInstrumentRequest markNewInstrumentRequest;
	@Mock
	private MarkExistingInstrumentRequest markExistingInstrumentRequest;
	@InjectMocks
	private InterventionResource testServlet;

	@Before
	public void init() {
		service = mock(InterventionService.class);
		testServlet = new InterventionResource(service);
		markExistingInstrumentRequest = mock(MarkExistingInstrumentRequest.class);
		markNewInstrumentRequest = mock(MarkNewInstrumentRequest.class);
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

	private BadRequestException noSerialNumber = new BadRequestException(
			"INT011", "numéro de série déjà utilisé");

	@Test
	public void dontMarkInstrumentWithAlreadyUsedSerialNumber()
			throws BadRequestException {
		Mockito.doThrow(noSerialNumber).when(service)
				.markNewInstrument(Mockito.any(MarkNewInstrumentRequest.class));
		request = VALID_MARK_NEW_INSTRUMENT;
		expectedResponse = Response.status(Status.BAD_REQUEST).build();

		receivedResponse = testServlet.markNewInstrument(request);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

	private BadRequestException noAnonymousType = new BadRequestException(
			"INT012", "requiert numéro de série");

	@Test
	public void dontMarkInstrumentWithNoAnonymousTypeWithoutSerialNumber()
			throws BadRequestException {
		Mockito.doThrow(noAnonymousType).when(service)
				.markNewInstrument(Mockito.any(MarkNewInstrumentRequest.class));
		request = VALID_MARK_NEW_INSTRUMENT;
		expectedResponse = Response.status(Status.BAD_REQUEST).build();

		receivedResponse = testServlet.markNewInstrument(request);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

	private BadRequestException doesNotExist = new BadRequestException(
			"INT010", "données invalides ou incomplètes");

	@Test
	public void dontMarkExistingInstrumentIfDoesNotActuallyExistInDatabase()
			throws BadRequestException {
		Mockito.doThrow(doesNotExist)
				.when(service)
				.markExistingInstrument(
						Mockito.any(MarkExistingInstrumentRequest.class));
		request = VALID_MARK_EXISTING_INSTRUMENT;
		expectedResponse = Response.status(Status.BAD_REQUEST).build();

		receivedResponse = testServlet.markExistingInstrument(request);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
}
