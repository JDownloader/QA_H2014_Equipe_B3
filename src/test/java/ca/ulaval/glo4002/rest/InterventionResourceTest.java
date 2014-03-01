package ca.ulaval.glo4002.rest;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.MarkExistingInstrumentRequest;
import ca.ulaval.glo4002.rest.requests.MarkNewInstrumentRequest;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.intervention.InterventionService;

@RunWith(MockitoJUnitRunner.class)
public class InterventionResourceTest {
	
	private Response expectedResponse;
	private Response receivedResponse;
	private String request;
	
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
		testServlet = new InterventionResource(service);
		service = mock(InterventionService.class);
		markExistingInstrumentRequest = mock(MarkExistingInstrumentRequest.class);
		markNewInstrumentRequest = mock(MarkNewInstrumentRequest.class);
	}
	
	@Test
	public void markNewInstrumentWhenValidRequest() {
		request = "{\"typecode\": \"IT72353\", \"statut\": \"UTILISE_PATIENT\", \"noserie\" : \"23562543-3635345\" }";
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.append("Location", "/intervention/$NO_INTERVENTION$/instruments/$TYPE_CODE$/$SERIE_OU_NOUNIQUE$");
		
		expectedResponse = Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
		receivedResponse = testServlet.markNewInstrument(request);
		
		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void doNotMarkNewInstrumentWhenInvalidRequest() {
		request = "{}";
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.append("Location", "/intervention/$NO_INTERVENTION$/instruments/$TYPE_CODE$/$SERIE_OU_NOUNIQUE$");
		
		expectedResponse = BadRequestJsonResponseBuilder.build("INT010", "Données invalides ou incomplètes");
		receivedResponse = testServlet.markNewInstrument(request);
		
		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void markExistingInstrumentWhenValidRequest() {
		request = "{\"statut\": \"UTILISE_PATIENT\", \"noserie\" : \"23562543-3635345\"}";
		expectedResponse = Response.status(Status.OK).build();
		receivedResponse = testServlet.markExistingInstrument(request);
		
		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void doNotMarkExistingInstrumentWhenInvalidRequest() {
		request = "{}";
		expectedResponse = BadRequestJsonResponseBuilder.build("INT010", "Données invalides ou incomplètes");
		receivedResponse = testServlet.markExistingInstrument(request);
		
		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void dontMarkInstrumentWithAlreadyUsedSerialNumber() {		
		assertTrue(false);
	}
	
	@Test
	public void dontMarkInstrumentWithNoAnonymousTypeWithoutSerialNumber() {
		assertTrue(false);
	}
	
	@Test
	public void dontMarkInstrumentWithNoSerialNumberIfNoAnonymousTypeAllowed() {
		assertTrue(false);
	}
}
