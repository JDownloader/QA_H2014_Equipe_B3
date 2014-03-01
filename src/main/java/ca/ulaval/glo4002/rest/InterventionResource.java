package ca.ulaval.glo4002.rest;

import java.text.ParseException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.rest.requests.CreateInterventionRequest;

@Path("interventions/")
public class InterventionResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response post(String request){
		JSONObject interventionError = new JSONObject();
		CreateInterventionRequest interventionRequest = null;
		try {
			interventionRequest = getInterventionRequest(request);
			if(!interventionRequest.validatePatientId()){
				interventionError.append("code", "INT002").append("message", "Le patient est inexistant");
				return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(interventionError.toString()).build();
			}
		} catch (JSONException | IllegalArgumentException | ParseException e) {
			interventionError.append("code", "INT001").append("message", "La requête contient des informations invalides et/ou est malformée");
			return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(interventionError.toString()).build();
	}
		return Response.status(Status.CREATED).build();
	}
	
	private CreateInterventionRequest getInterventionRequest(String request) throws JSONException, ParseException {
		JSONObject jsonRequest = new JSONObject(request);
		CreateInterventionRequest interventionRequest = new CreateInterventionRequest(jsonRequest);
		interventionRequest.validateStatus();
		interventionRequest.validateType();
		return interventionRequest;
	}
	
	//TODO complete class, based on same pattern as PrescriptionResource and PrescriptionService.

}
