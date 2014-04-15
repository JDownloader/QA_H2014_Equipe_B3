package ca.ulaval.glo4002.rest.response;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import ca.ulaval.glo4002.exceptions.domainexceptions.PatientDoesNotExist;

public class InterventionCreationResponseTest {
	
	private String SAMPLE_EXCEPTION_CODE = "";
	private String SAMPLE_EXCEPTION_MESSAGE = "";
	static private final int SAMPLE_INTERVENTION_NO = 0;
	private PatientDoesNotExist e = new PatientDoesNotExist(SAMPLE_EXCEPTION_CODE, SAMPLE_EXCEPTION_MESSAGE);
	
	private Response response;
	private InterventionCreationResponse interventionCreationResponse = new InterventionCreationResponse();
	
	@Test
	public void createdBadRequestResponseReturnsBadRequestResponse(){
		response = interventionCreationResponse.createBadRequestResponse(e);
		assertEquals(Response.status(Status.BAD_REQUEST).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void createCustomCreatedSuccessResponseReturnsCreatedResponse(){
		response = interventionCreationResponse.createSuccessResponse(SAMPLE_INTERVENTION_NO);
		assertEquals(Response.status(Status.CREATED).build().getStatus(), response.getStatus());
	}
}
