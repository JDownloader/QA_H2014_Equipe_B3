package ca.ulaval.glo4002.rest;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.MarkExistingInstrumentRequest;
import ca.ulaval.glo4002.rest.requests.MarkNewInstrumentRequest;
import ca.ulaval.glo4002.services.intervention.InterventionService;

@RunWith(MockitoJUnitRunner.class)
public class InterventionResourceTest {

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
