package ca.ulaval.glo4002.rest;

import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	public String INTERVENTION_NUMBER_PARAMETER = "nointervention";
	public String INSTRUMENT_NUMBER_PARAMETER = "noserie";
	
	private InterventionService service;
	
	@PathParam("intervention_number")
	private int interventionNumber;
	@PathParam("instrument_number")
	private String instrumentNumber;
	
	public InterventionResource() {

	}
	
	public InterventionResource(InterventionService service) {
		this.service = service;
	}
	
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
		Response response = null;
		MarkNewInstrumentRequest myRequest;
		
		try {
			myRequest = buildNewInstrumentRequest(request);
		}
		catch (BadRequestException exception) { 
			return returnResponseWhenException(exception);
		}
		
		try {
			service.markNewInstrument(myRequest);
		} catch (BadRequestException exception) {
			return returnResponseWhenException(exception);
		}
		
		response = buildCreatedResponseForMarkNewInstrument(myRequest);
		
		return response;
	}
	
	private MarkNewInstrumentRequest buildNewInstrumentRequest(String request) throws BadRequestException {
		JSONObject jsonRequest = new JSONObject(request);
		jsonRequest.put(INTERVENTION_NUMBER_PARAMETER, interventionNumber);
		MarkNewInstrumentRequest myRequest = new MarkNewInstrumentRequest(jsonRequest);
		return myRequest;
	}
	
	private static Response buildCreatedResponseForMarkNewInstrument(MarkNewInstrumentRequest myRequest) {
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.append("Location", "/intervention/" + myRequest.INTERVENTION_NUMBER_PARAMETER + "/instruments/" + myRequest.TYPECODE_PARAMETER + "/" + myRequest.SERIAL_NUMBER_PARAMETER);
		
		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	@PUT @Path("{intervention_number: [0-9]+}/instruments/{instrument_number}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response markExistingInstrument(String request) {
		Response response = null;
		MarkExistingInstrumentRequest myRequest;
		
		try {
			myRequest = buildExistingIntrumentRequest(request);
		}
		catch (BadRequestException exception) {
			return returnResponseWhenException(exception);
		}
		
		try {
			service.markExistingInstrument(myRequest);
		} catch (BadRequestException exception) {
			return returnResponseWhenException(exception);
		}
		
		response = buildOkResponseForMarkExistingInstrument();
		
		return response;
	}
	
	private MarkExistingInstrumentRequest buildExistingIntrumentRequest(String request) throws BadRequestException {
		JSONObject jsonRequest = new JSONObject(request); 
		jsonRequest.put(INTERVENTION_NUMBER_PARAMETER, interventionNumber);
		jsonRequest.put(INSTRUMENT_NUMBER_PARAMETER, instrumentNumber);
		MarkExistingInstrumentRequest myRequest = new MarkExistingInstrumentRequest(jsonRequest);
		return myRequest;
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
