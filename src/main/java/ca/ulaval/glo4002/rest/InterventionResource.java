package ca.ulaval.glo4002.rest;

import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.InterventionRequest;
import ca.ulaval.glo4002.rest.requests.MarkExistingInstrumentRequest;
import ca.ulaval.glo4002.rest.requests.MarkNewInstrumentRequest;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.intervention.InterventionService;

@Path("interventions/")
public class InterventionResource {
	private InterventionService service;
	
	public InterventionResource() {
		//TODO
	}
	
	public InterventionResource(InterventionService service) {
		this.service = service;
	}
	
	//TODO this user story should be renamed and should communicate with the service layer
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String request){
		JSONObject interventionError = new JSONObject();
		InterventionRequest interventionRequest = null;
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
	
	private InterventionRequest getInterventionRequest(String request) throws JSONException, ParseException {
		JSONObject jsonRequest = new JSONObject(request);
		InterventionRequest interventionRequest = new InterventionRequest(jsonRequest);
		interventionRequest.validateStatus();
		interventionRequest.validateType();
		return interventionRequest;
	}
	
	@POST @Path("{intervention_number: [0-9]+}/instruments/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response markNewInstrument(String request) {
		Response response = BadRequestJsonResponseBuilder.build("INT010", "Données invalides ou incomplètes");
		JSONObject jsonRequest = new JSONObject(request); 
		MarkNewInstrumentRequest myRequest;
		
		try {
			myRequest = new MarkNewInstrumentRequest(jsonRequest);
		}
		catch (BadRequestException exception) { 
			return returnResponseWhenException(exception);
		}
		
		try {
			service.markNewInstrument(myRequest);
		} catch (BadRequestException exception) {
			return returnResponseWhenException(exception);
		}
		
		response = buildCreatedResponseForMarkNewInstrument();
		
		return response;
	}
	
	private static Response buildCreatedResponseForMarkNewInstrument() {
		//TODO include actual path
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.append("Location", "/intervention/$NO_INTERVENTION$/instruments/$TYPE_CODE$/$SERIE_OU_NOUNIQUE$");
		
		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	@PUT @Path("{intervention_number: [0-9]+}/instruments/{instrument_number}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response markExistingInstrument(String request) {
		Response response = BadRequestJsonResponseBuilder.build("INT010", "Données invalides ou incomplètes");
		JSONObject jsonRequest = new JSONObject(request); 
		
		try {
			MarkExistingInstrumentRequest myRequest = new MarkExistingInstrumentRequest(jsonRequest);
		}
		catch (BadRequestException exception) {
			return returnResponseWhenException(exception);
		}
		
		//TODO call service layer
		
		response = buildOkResponseForMarkExistingInstrument();
		
		return response;
	}
	
	private Response buildOkResponseForMarkExistingInstrument() {
		return Response.status(Status.OK).build();
	}
	
	private Response returnResponseWhenException(BadRequestException exception) {
		Response response = BadRequestJsonResponseBuilder.build(exception.getInternalCode(), exception.getMessage());
		return response;
	}
	
	//TODO complete class, based on same pattern as PrescriptionResource and PrescriptionService.
}
