package ca.ulaval.glo4002.rest.response;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import ca.ulaval.glo4002.exceptions.domainexceptions.interventionexceptions.PatientDoesNotExist;

public class InterventionCreationResponseTest {
	
	static private final int SAMPLE_INTERVENTION_NO = 0;
	static private final String SUCCESS_RESPONSE = "{"+"\"message\""+":"+"\"Succès\""+","+"\"location\""+":"+"\"/interventions/"+SAMPLE_INTERVENTION_NO+"\""+"}";
	private PatientDoesNotExist e = new PatientDoesNotExist();
	
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
	
	@Test
	public void createCustomCreatedSuccessResponseReturnsGoodMessageAndCode(){
		response = interventionCreationResponse.createSuccessResponse(SAMPLE_INTERVENTION_NO);
		assertEquals(SUCCESS_RESPONSE, response.getEntity());
	}
}
