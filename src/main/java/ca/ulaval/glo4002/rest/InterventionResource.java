package ca.ulaval.glo4002.rest;

import java.text.ParseException;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.persistence.intervention.HibernateInterventionRepository;
import ca.ulaval.glo4002.persistence.patient.HibernatePatientRepository;
import ca.ulaval.glo4002.rest.requests.CreateInterventionRequest;
import ca.ulaval.glo4002.rest.requests.CreateInterventionRequestFactory;
import ca.ulaval.glo4002.rest.requests.MarkExistingInstrumentRequest;
import ca.ulaval.glo4002.rest.requests.MarkNewInstrumentRequest;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.intervention.InterventionService;
import ca.ulaval.glo4002.services.intervention.InterventionServiceBuilder;
import ca.ulaval.glo4002.services.surgicalTool.SurgicalToolService;

@Path("interventions/")
public class InterventionResource {
	public String INTERVENTION_NUMBER_PARAMETER = "nointervention";
	public String INSTRUMENT_NUMBER_PARAMETER = "noinstrument";
	
	private InterventionService service;
	private SurgicalToolService surgicalToolService;
	private CreateInterventionRequestFactory createInterventionRequestFactory;

	@PathParam("intervention_number")
	private int interventionNumber;
	@PathParam("instrument_number")
	private String instrumentNumber;
	
	public InterventionResource() {
		EntityManager entityManager = new EntityManagerProvider().getEntityManager();
		
		InterventionServiceBuilder interventionServiceBuilder = new InterventionServiceBuilder();
		interventionServiceBuilder.entityTransaction(entityManager.getTransaction());
		interventionServiceBuilder.interventionRepository(new HibernateInterventionRepository());
		interventionServiceBuilder.patientRepository(new HibernatePatientRepository());
		this.service = new InterventionService(interventionServiceBuilder);
		
		this.createInterventionRequestFactory = new CreateInterventionRequestFactory();
	}
	
	public InterventionResource(InterventionService service, SurgicalToolService surgicalToolService) {
		this.service = service;
		this.surgicalToolService = surgicalToolService;
	}
	
	public InterventionResource(InterventionService service, CreateInterventionRequestFactory createInterventionRequestFactory) {
		this.service = service;
		this.createInterventionRequestFactory = createInterventionRequestFactory;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String request){
		try {
			CreateInterventionRequest interventionRequest = getInterventionRequest(request);
			service.createIntervention(interventionRequest); 
			return Response.status(Status.CREATED).build();
		} catch (JSONException | ParseException e) {
			return BadRequestJsonResponseBuilder.build("INT001", "Invalid parameters were supplied to the request.");
		} catch (IllegalArgumentException e) {
			return BadRequestJsonResponseBuilder.build("INT001", e.getMessage());
		} catch (BadRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private CreateInterventionRequest getInterventionRequest(String request) throws JSONException, ParseException {
		JSONObject jsonRequest = new JSONObject(request);
		CreateInterventionRequest interventionRequest = createInterventionRequestFactory.createInterventionRequest(jsonRequest);
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
			surgicalToolService.markNewInstrument(myRequest);
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
			surgicalToolService.markExistingInstrument(myRequest);
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
	
}
