package ca.ulaval.glo4002.rest.resources;

import java.net.URI;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolFactory;
import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.dto.BadResponseDTO;
import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;
import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolCreationDTOValidator;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolModificationDTOValidator;
import ca.ulaval.glo4002.services.intervention.InterventionService;

@Path("interventions/")
public class InterventionResource {
	public static final String BAD_REQUEST_ERROR_CODE_INT001 = "INT001";
	public static final String BAD_REQUEST_ERROR_CODE_INT010 = "INT010";

	private InterventionService interventionService;

	public InterventionResource() {
		this.interventionService = new InterventionService();
	}

	public InterventionResource(InterventionService interventionService) {
		this.interventionService = interventionService;
	}

	//TODO: Refactor NewMarie BEGIN
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String request) {
		try {
			CreateInterventionRequestParser requestParser = getCreateInterventionRequestParser(request);
			int interventionNumber = interventionService.createIntervention(requestParser);
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

	private CreateInterventionRequestParser getCreateInterventionRequestParser(String request)
			throws RequestParseException {
		JSONObject jsonRequest = new JSONObject(request);
		CreateInterventionRequestParser interventionRequestParser = new CreateInterventionRequestParser(jsonRequest);
		return interventionRequestParser;
	}
	//TODO: Refactor NewMarie END

	@POST
	@Path("{interventionNumber: [0-9]+}/instruments/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createSurgicalTool(SurgicalToolCreationDTO surgicalToolCreationDTO, 
			@PathParam("interventionNumber") int interventionNumber) throws Exception {
		try {
			surgicalToolCreationDTO.interventionNumber = interventionNumber;
			
			int surgicalToolId = interventionService.createSurgicalTool(surgicalToolCreationDTO, new SurgicalToolCreationDTOValidator(), new SurgicalToolFactory());
			
			String newResourceLocation = String.format("/%s/%s", surgicalToolCreationDTO.typeCode, surgicalToolId);
			return Response.status(Status.CREATED).location(new URI(newResourceLocation)).build();
		} catch (ServiceRequestException e) {
			return Response.status(Status.BAD_REQUEST).entity(new BadResponseDTO(e.getInternalCode(), e.getMessage())).build();
		}
	}

	@PUT
	@Path("{interventionNumber: [0-9]+}/instruments/{surgicalToolTypeCode}/{surgicalToolSerialNumberOrId}/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifySurgicalTool(SurgicalToolModificationDTO surgicalToolModificationDTO,
			@PathParam("interventionNumber") int interventionNumber,
			@PathParam("surgicalToolTypeCode") String surgicalToolTypeCode,
			@PathParam("surgicalToolSerialNumberOrId") String surgicalToolSerialNumberOrId) throws Exception {

		try {
			surgicalToolModificationDTO.interventionNumber = interventionNumber;
			surgicalToolModificationDTO.serialNumberOrId = surgicalToolSerialNumberOrId;
			surgicalToolModificationDTO.typeCode = surgicalToolTypeCode;
			
			interventionService.modifySurgicalTool(surgicalToolModificationDTO, new SurgicalToolModificationDTOValidator());
			
			return Response.status(Status.OK).build();
		} catch (ServiceRequestException e) {
			return Response.status(Status.BAD_REQUEST).entity(new BadResponseDTO(e.getInternalCode(), e.getMessage())).build();
		}
	}
}
