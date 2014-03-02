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
import ca.ulaval.glo4002.persistence.drug.HibernateDrugRepository;
import ca.ulaval.glo4002.persistence.patient.HibernatePatientRepository;
import ca.ulaval.glo4002.persistence.prescription.HibernatePrescriptionRepository;
import ca.ulaval.glo4002.rest.requests.AddPrescriptionRequest;
import ca.ulaval.glo4002.rest.requests.AddPrescriptionRequestFactory;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.prescription.*;

@Path("patient/{patient_number: [0-9]+}/prescriptions/")
public class PrescriptionResource {
	private PrescriptionService service;
	private AddPrescriptionRequestFactory addPrescriptionRequestFactory;
	
	public PrescriptionResource() {
		EntityManager entityManager = new EntityManagerProvider().getEntityManager();
		
		PrescriptionServiceBuilder prescriptionServiceBuilder = new PrescriptionServiceBuilder();
		prescriptionServiceBuilder.entityTransaction(entityManager.getTransaction());
		prescriptionServiceBuilder.prescriptionRepository(new HibernatePrescriptionRepository());
		prescriptionServiceBuilder.drugRepository(new HibernateDrugRepository());
		prescriptionServiceBuilder.patientRepository(new HibernatePatientRepository());	
		this.service = new PrescriptionService(prescriptionServiceBuilder);
		
		this.addPrescriptionRequestFactory = new AddPrescriptionRequestFactory();
	}
	
	public PrescriptionResource(PrescriptionService service, AddPrescriptionRequestFactory addPrescriptionRequestFactory) {
		this.service = service;
		this.addPrescriptionRequestFactory = addPrescriptionRequestFactory;
	}
	
	@PathParam("patient_number")
	private int patientNumber;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String request) {
		try {
			AddPrescriptionRequest prescriptionRequest = getPrescriptionRequest(request);
			service.addPrescription(prescriptionRequest); 
			return Response.status(Status.CREATED).build();
		} catch (JSONException | ParseException e) {
			return BadRequestJsonResponseBuilder.build("PRES001", "Invalid parameters were supplied to the request.");
		} catch (IllegalArgumentException e) {
			return BadRequestJsonResponseBuilder.build("PRES001", e.getMessage());
		} catch (BadRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private AddPrescriptionRequest getPrescriptionRequest(String request) throws JSONException, ParseException {
		JSONObject jsonRequest = new JSONObject(request);
		String patientNumberParameter = String.valueOf(patientNumber);
				
		AddPrescriptionRequest prescriptionRequest = addPrescriptionRequestFactory.createAddPrescriptionRequest(jsonRequest, patientNumberParameter);
		return prescriptionRequest;
	}
}
