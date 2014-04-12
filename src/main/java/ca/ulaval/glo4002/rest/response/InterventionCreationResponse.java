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
	
	public Response createDefaultBadRequestResponse(DomainException e){
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.append(CODE_PARAMETER, e.getCode()).append(MESSAGE_PARAMETER, e.getMessage());
		return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build(); 
	}
	
	public Response createDefaultAcceptedResponse(){
		return Response.status(Status.ACCEPTED).build();
	}
	
	public Response createCustomBadRequestMissingInformationResponse(){
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.append(CODE_PARAMETER, "INT001").append(MESSAGE_PARAMETER, "Erreur - informations manquantes ou invalides");
		return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	public Response createCustomBadRequestNonExistingPatientResponse(){
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.append(CODE_PARAMETER, "INT002").append(MESSAGE_PARAMETER, "Erreur - Patient inexistant");
		return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	public Response createCustomCreatedSuccessResponse(int interventionNumber){
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.append(LOCATION_PARAMETER, "/interventions/"+interventionNumber).append(MESSAGE_PARAMETER, "Succès");
		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
}
