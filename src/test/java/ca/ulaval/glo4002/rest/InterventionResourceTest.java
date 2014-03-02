package ca.ulaval.glo4002.rest;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.*;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.CreateInterventionRequest;
import ca.ulaval.glo4002.rest.requests.CreateInterventionRequestFactory;
import ca.ulaval.glo4002.services.intervention.InterventionService;

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
}
