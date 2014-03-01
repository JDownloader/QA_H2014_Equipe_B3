package ca.ulaval.glo4002.rest;

import java.text.ParseException;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.persistence.drug.HibernateDrugRepository;
import ca.ulaval.glo4002.persistence.patient.HibernatePatientRepository;
import ca.ulaval.glo4002.persistence.prescription.HibernatePrescriptionRepository;
import ca.ulaval.glo4002.rest.requests.PrescriptionRequest;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.prescription.*;

@Path("patient/{patient_number: [0-9]+}/prescriptions/")
public class PrescriptionResource {
	private PrescriptionService service;
	
	public PrescriptionResource() {
		EntityManager entityManager = new EntityManagerProvider().getEntityManager();
		
		PrescriptionServiceBuilder prescriptionServiceBuilder = new PrescriptionServiceBuilder();
		prescriptionServiceBuilder.entityTransaction(entityManager.getTransaction());
		prescriptionServiceBuilder.prescriptionRepository(new HibernatePrescriptionRepository());
		prescriptionServiceBuilder.drugRepository(new HibernateDrugRepository());
		prescriptionServiceBuilder.patientRepository(new HibernatePatientRepository());
		
		service = new PrescriptionService(prescriptionServiceBuilder);
	}
	
	public PrescriptionResource(PrescriptionService service) {
		this.service = service;
	}
	
	@PathParam("patient_number")
	private int patientNumber;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String request) {
		PrescriptionRequest prescriptionRequest = null;
		
		try {
			prescriptionRequest = getPrescriptionRequest(request);
		} catch (Exception e) {
			return BadRequestJsonResponseBuilder.build("PRES001", "La requête contient des informations invalides et/ou est malformée");
		}
		
		return service.addPrescription(prescriptionRequest); 
	}
	
	private PrescriptionRequest getPrescriptionRequest(String request) throws JSONException, ParseException {
		JSONObject jsonRequest = new JSONObject(request);
		jsonRequest.put("patient", String.valueOf(patientNumber));
		
		PrescriptionRequest prescriptionRequest = new PrescriptionRequest(jsonRequest);
		prescriptionRequest.validateRequestParameters();
		
		return prescriptionRequest;
	}
}
