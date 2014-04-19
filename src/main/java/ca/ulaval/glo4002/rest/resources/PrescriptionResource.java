package ca.ulaval.glo4002.rest.resources;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.persistence.HibernatePatientRepository;
import ca.ulaval.glo4002.persistence.drug.HibernateDrugRepository;
import ca.ulaval.glo4002.persistence.prescription.HibernatePrescriptionRepository;
import ca.ulaval.glo4002.rest.requestparsers.prescription.AddPrescriptionRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.prescription.AddPrescriptionRequestParserFactory;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.prescription.PrescriptionService;
import ca.ulaval.glo4002.services.prescription.PrescriptionServiceBuilder;

@Path("patient/{patient_number: [0-9]+}/prescriptions/")
public class PrescriptionResource {
	public static final String BAD_REQUEST_ERROR_CODE_PRES001 = "PRES001";

	private PrescriptionService service;
	private AddPrescriptionRequestParserFactory addPrescriptionRequestParserFactory;

	public PrescriptionResource() {
		EntityManager entityManager = new EntityManagerProvider().getEntityManager();

		buildPrescriptionService(entityManager);

		this.addPrescriptionRequestParserFactory = new AddPrescriptionRequestParserFactory();
	}

	private void buildPrescriptionService(EntityManager entityManager) {
		PrescriptionServiceBuilder prescriptionServiceBuilder = new PrescriptionServiceBuilder()
			.entityTransaction(entityManager.getTransaction())
			.prescriptionRepository(new HibernatePrescriptionRepository())
			.drugRepository(new HibernateDrugRepository())
			.patientRepository(new HibernatePatientRepository());
		this.service = prescriptionServiceBuilder.build();
	}

	public PrescriptionResource(PrescriptionService service, AddPrescriptionRequestParserFactory addPrescriptionRequestParserFactory) {
		this.service = service;
		this.addPrescriptionRequestParserFactory = addPrescriptionRequestParserFactory;
	}

	@PathParam("patient_number")
	private int patientNumber;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String request) {
		try {
			AddPrescriptionRequestParser requestParser = getRequestParser(request);
			service.addPrescription(requestParser);
			return Response.status(Status.CREATED).build();
		} catch (RequestParseException | JSONException e) {
			return BadRequestJsonResponseBuilder.build(BAD_REQUEST_ERROR_CODE_PRES001, e.getMessage());
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private AddPrescriptionRequestParser getRequestParser(String request) throws RequestParseException {
		JSONObject jsonRequest = new JSONObject(request);
		jsonRequest.put(AddPrescriptionRequestParser.PATIENT_NUMBER_PARAMETER_NAME, String.valueOf(patientNumber));

		AddPrescriptionRequestParser prescriptionParserRequest = addPrescriptionRequestParserFactory.getParser(jsonRequest);
		return prescriptionParserRequest;
	}
}
