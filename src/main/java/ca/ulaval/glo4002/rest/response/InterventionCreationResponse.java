package ca.ulaval.glo4002.rest.response;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

public class InterventionCreationResponse {
	
	private static final String CODE_PARAMETER = "code";
	private static final String MESSAGE_PARAMETER = "message";
	private static final String LOCATION_PARAMETER = "location";
	
	public Response createDefaultBadRequestResponse(){
		return Response.status(Status.BAD_REQUEST).build(); 
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
	
	@POST
	@Path(value = "/interventions/{interventionNumber}")
	public Response createCustomCreatedSuccessResponse(@PathParam("interventionNumber") int interventionNumber){
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.append(LOCATION_PARAMETER, "/interventions/"+interventionNumber).append(MESSAGE_PARAMETER, "Succès");
		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
}
