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
import ca.ulaval.glo4002.services.assemblers.SurgicalToolAssembler;
import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;
import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolCreationDTOValidator;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolModificationDTOValidator;
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
	public Response post(SurgicalToolCreationDTO surgicalToolCreationDTO, @PathParam("intervention_number")
	int interventionNumber) throws Exception {
		
		try {
			surgicalToolCreationDTO.setInterventionNumber(interventionNumber);
			service.createSurgicalTool(surgicalToolCreationDTO, new SurgicalToolCreationDTOValidator(),
					new SurgicalToolAssembler());
			return Response.status(Status.CREATED).build();
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		}
	}

	
	@PUT
	@Path("{interventionNumber: [0-9]+}/instruments/{surgicalToolTypeCode}/{surgicalToolSerialNumber}/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifySurgicalTool(SurgicalToolModificationDTO surgicalToolModificationDTO, @PathParam("interventionNumber") int interventionNumber,
			@PathParam("surgicalToolTypeCode") String surgicalToolTypeCode, @PathParam("surgicalToolSerialNumber") String surgicalToolSerialNumber) throws Exception{
		
		try {
			surgicalToolModificationDTO.setInterventionNumber(interventionNumber);
			surgicalToolModificationDTO.setOriginalSerialNumber(surgicalToolSerialNumber);
			surgicalToolModificationDTO.setTypecode(surgicalToolTypeCode);
			service.modifySurgicalTool(surgicalToolModificationDTO, new SurgicalToolModificationDTOValidator());
			return Response.status(Status.OK).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
