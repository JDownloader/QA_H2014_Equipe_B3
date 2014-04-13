package ca.ulaval.glo4002.rest.response;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.domainexceptions.DomainException;

public class InterventionCreationResponse {
	
	private static final String CODE_PARAMETER = "code";
	private static final String MESSAGE_PARAMETER = "message";
	private static final String LOCATION_PARAMETER = "location";
	
	public Response createBadRequestResponse(DomainException e){
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put(CODE_PARAMETER, e.getCode()).put(MESSAGE_PARAMETER, e.getMessage());
		return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build(); 
	}
	
	public Response createSuccessResponse(int interventionNumber){
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put(LOCATION_PARAMETER, "/interventions/"+interventionNumber).put(MESSAGE_PARAMETER, "Succès");
		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
}
