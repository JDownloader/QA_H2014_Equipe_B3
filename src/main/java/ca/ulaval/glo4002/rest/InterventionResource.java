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
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.intervention.InterventionService;
import ca.ulaval.glo4002.services.intervention.InterventionServiceBuilder;

@Path("interventions/")
public class InterventionResource {
	private InterventionService service;
	private CreateInterventionRequestFactory createInterventionRequestFactory;
	
	public InterventionResource() {
		EntityManager entityManager = new EntityManagerProvider().getEntityManager();
		
		InterventionServiceBuilder interventionServiceBuilder = new InterventionServiceBuilder();
		interventionServiceBuilder.entityTransaction(entityManager.getTransaction());
		interventionServiceBuilder.interventionRepository(new HibernateInterventionRepository());
		interventionServiceBuilder.patientRepository(new HibernatePatientRepository());
		this.service = new InterventionService(interventionServiceBuilder);
		
		this.createInterventionRequestFactory = new CreateInterventionRequestFactory();
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
		} catch (JSONException | ParseException | IllegalArgumentException e) {
			return BadRequestJsonResponseBuilder.build("INT001", "La requête contient des informations invalides et/ou est malformée");
		} catch (BadRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private CreateInterventionRequest getInterventionRequest(String request) throws JSONException, ParseException {
		JSONObject jsonRequest = new JSONObject(request);
		CreateInterventionRequest interventionRequest = createInterventionRequestFactory.createCreateInterventionRequest(jsonRequest);
		return interventionRequest;
	}

}
