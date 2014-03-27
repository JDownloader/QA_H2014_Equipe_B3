package ca.ulaval.glo4002.rest.resources;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.persistence.intervention.HibernateInterventionRepository;
import ca.ulaval.glo4002.persistence.patient.HibernatePatientRepository;
import ca.ulaval.glo4002.persistence.surgicaltool.HibernateSurgicalToolRepository;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParserFactory;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.*;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.intervention.InterventionService;
import ca.ulaval.glo4002.services.intervention.InterventionServiceBuilder;

@Path("interventions/")
public class InterventionResource {
	public static final String BAD_REQUEST_ERROR_CODE_INT001 = "INT001";
	public static final String BAD_REQUEST_ERROR_CODE_INT010 = "INT010";

	private InterventionService service;
	private CreateInterventionRequestParserFactory createInterventionRequestParserFactory;
	private CreateSurgicalToolRequestParserFactory createSurgicalToolRequestParserFactory;
	private SurgicalToolModificationRequestParserFactory modifySurgicalToolRequestParserFactory;

	public InterventionResource() {
		EntityManager entityManager = new EntityManagerProvider().getEntityManager();

		buildInterventionService(entityManager);

		this.createInterventionRequestParserFactory = new CreateInterventionRequestParserFactory();
		this.createSurgicalToolRequestParserFactory = new CreateSurgicalToolRequestParserFactory();
		this.modifySurgicalToolRequestParserFactory = new SurgicalToolModificationRequestParserFactory();
	}

	private void buildInterventionService(EntityManager entityManager) {
		InterventionServiceBuilder interventionServiceBuilder = new InterventionServiceBuilder()
			.entityTransaction(entityManager.getTransaction())
			.interventionRepository(new HibernateInterventionRepository())
			.patientRepository(new HibernatePatientRepository())
			.surgicalToolRepository(new HibernateSurgicalToolRepository());
		this.service = interventionServiceBuilder.build();
	}

	public InterventionResource(InterventionResourceBuilder interventionResourceBuilder) {
		this.service = interventionResourceBuilder.service;
		this.createInterventionRequestParserFactory = interventionResourceBuilder.createInterventionRequestParserFactory;
		this.createSurgicalToolRequestParserFactory = interventionResourceBuilder.createSurgicalToolRequestParserFactory;
		this.modifySurgicalToolRequestParserFactory = interventionResourceBuilder.modifySurgicalToolRequestParserFactory;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String request) {
		try {
			CreateInterventionRequestParser requestParser = getCreateInterventionRequestParser(request);
			int interventionNumber = service.createIntervention(requestParser);
			String newResourceLocation = getNewResourceLocation(interventionNumber);
			return Response.status(Status.CREATED).location(new URI(newResourceLocation)).build();
		} catch (RequestParseException | JSONException e) {
			return BadRequestJsonResponseBuilder.build(BAD_REQUEST_ERROR_CODE_INT001, e.getMessage());
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private String getNewResourceLocation(int interventionNumber) {
		return String.format("/%d", interventionNumber);
	}

	private CreateInterventionRequestParser getCreateInterventionRequestParser(String request) throws RequestParseException {
		JSONObject jsonRequest = new JSONObject(request);
		CreateInterventionRequestParser interventionRequestParser = createInterventionRequestParserFactory.getParser(jsonRequest);
		return interventionRequestParser;
	}

	@POST
	@Path("{interventionNumber: [0-9]+}/instruments/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createSurgicalTool(String request, @PathParam("interventionNumber") int interventionNumber) {
		try {
			SurgicalToolCreationRequestParser requestParser = getCreateSurgicalToolRequestParser(request, interventionNumber);
			int surgicalToolId = service.createSurgicalTool(requestParser);
			String newResourceLocation = getNewSurgicalToolResourceLocation(requestParser, surgicalToolId);
			return Response.status(Status.CREATED).location(new URI(newResourceLocation)).build();
		} catch (RequestParseException | JSONException e) {
			return BadRequestJsonResponseBuilder.build(BAD_REQUEST_ERROR_CODE_INT010, e.getMessage());
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private String getNewSurgicalToolResourceLocation(SurgicalToolCreationRequestParser requestParser, int surgicalToolId) {
		return String.format("/%s/%s", requestParser.getTypeCode(), surgicalToolId);
	}

	private SurgicalToolCreationRequestParser getCreateSurgicalToolRequestParser(String request, int interventionNumber) throws RequestParseException {
		JSONObject jsonRequest = new JSONObject(request);
		jsonRequest.put(SurgicalToolCreationRequestParser.INTERVENTION_NUMBER_PARAMETER_NAME, String.valueOf(interventionNumber));

		SurgicalToolCreationRequestParser requestParser = createSurgicalToolRequestParserFactory.getParser(jsonRequest);
		return requestParser;
	}

	@PUT
	@Path("{interventionNumber: [0-9]+}/instruments/{surgicalToolTypeCode}/{surgicalToolSerialNumber}/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifySurgicalTool(String request, @PathParam("interventionNumber") int interventionNumber,
			@PathParam("surgicalToolTypeCode") String surgicalToolTypeCode, @PathParam("surgicalToolSerialNumber") String surgicalToolSerialNumber) {
		try {
			SurgicalToolModificationRequestParser requestParser = getModifySurgicalToolRequestParser(request, interventionNumber, surgicalToolTypeCode,
					surgicalToolSerialNumber);
			service.modifySurgicalTool(requestParser);
			return Response.status(Status.OK).build();
		} catch (RequestParseException | JSONException e) {
			return BadRequestJsonResponseBuilder.build(BAD_REQUEST_ERROR_CODE_INT010, e.getMessage());
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private SurgicalToolModificationRequestParser getModifySurgicalToolRequestParser(String request, int interventionNumber, String surgicalToolTypeCode,
			String surgicalToolSerialNumber) throws RequestParseException {
		JSONObject jsonRequest = new JSONObject(request);
		addPathParametersToJsonObject(jsonRequest, interventionNumber, surgicalToolTypeCode, surgicalToolSerialNumber);

		SurgicalToolModificationRequestParser requestParser = modifySurgicalToolRequestParserFactory.getParser(jsonRequest);
		return requestParser;
	}

	private void addPathParametersToJsonObject(JSONObject jsonRequest, int interventionNumber, String surgicalToolTypeCode, String surgicalToolSerialNumber) {
		jsonRequest.put(SurgicalToolModificationRequestParser.INTERVENTION_NUMBER_PARAMETER_NAME, String.valueOf(interventionNumber));
		jsonRequest.put(SurgicalToolModificationRequestParser.NEW_TYPECODE_PARAMETER_NAME, jsonRequest.opt("typecode"));
		jsonRequest.put(SurgicalToolModificationRequestParser.TYPECODE_PARAMETER_NAME, String.valueOf(surgicalToolTypeCode));
		jsonRequest.put(SurgicalToolModificationRequestParser.NEW_SERIAL_NUMBER_PARAMETER_NAME, jsonRequest.opt("noserie"));
		jsonRequest.put(SurgicalToolModificationRequestParser.SERIAL_NUMBER_PARAMETER_NAME, String.valueOf(surgicalToolSerialNumber));
	}
}
