package ca.ulaval.glo4002.rest.response;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

public class InterventionCreationResponseTest {
	
	static private final int SAMPLE_INTERVENTION_NO = 0;
	static private final String MISSING_INFORMATION_RESPONSE = "{"+"\"message\""+":["+"\"Erreur - informations manquantes ou invalides\""+"],"+"\"code\""+":["+"\"INT001\""+"]}";
	static private final String NON_EXISTING_PATIENT_RESPONSE = "{"+"\"message\""+":["+"\"Erreur - Patient inexistant\""+"],"+"\"code\""+":["+"\"INT002\""+"]}";
	static private final String SUCCESS_RESPONSE = "{"+"\"message\""+":["+"\"Succès\""+"],"+"\"location\""+":["+"\"/interventions/"+SAMPLE_INTERVENTION_NO+"\""+"]}";
	
	private Response response;
	private InterventionCreationResponse interventionCreationResponse = new InterventionCreationResponse();
	
	@Test
	public void createdDefaultbadRequestResponseReturnsBadRequestResponse(){
//		response = interventionCreationResponse.createDefaultBadRequestResponse();
//		assertEquals(Response.status(Status.BAD_REQUEST).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void createDefaultAcceptedResponseReturnsAcceptedResponse(){
		response = interventionCreationResponse.createDefaultAcceptedResponse();
		assertEquals(Response.status(Status.ACCEPTED).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void createCustomBadRequestMissingInformationResponseReturnsBadRequestResponse(){
		response = interventionCreationResponse.createCustomBadRequestMissingInformationResponse();
		assertEquals(Response.status(Status.BAD_REQUEST).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void createCustomBadRequestNonExistingPatientReturnsBadRequestResponse(){
		response = interventionCreationResponse.createCustomBadRequestNonExistingPatientResponse();
		assertEquals(Response.status(Status.BAD_REQUEST).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void createCustomCreatedSuccessResponseReturnsCreatedResponse(){
		response = interventionCreationResponse.createCustomCreatedSuccessResponse(SAMPLE_INTERVENTION_NO);
		assertEquals(Response.status(Status.CREATED).build().getStatus(), response.getStatus());
	}
	
	@Test
	public void createCustomBadRequestMissingInformationResponseReturnsGoodMessageAndCode(){
		response = interventionCreationResponse.createCustomBadRequestMissingInformationResponse();
		assertEquals(MISSING_INFORMATION_RESPONSE, response.getEntity());
	}
	
	@Test
	public void createCustomBadRequestNonExistingPatientResponseReturnsGoodMessageAndCode(){
		response = interventionCreationResponse.createCustomBadRequestNonExistingPatientResponse();
		assertEquals(NON_EXISTING_PATIENT_RESPONSE, response.getEntity());
	}
	
	@Test
	public void createCustomCreatedSuccessResponseReturnsGoodMessageAndCode(){
		response = interventionCreationResponse.createCustomCreatedSuccessResponse(SAMPLE_INTERVENTION_NO);
		assertEquals(SUCCESS_RESPONSE, response.getEntity());
	}
}
