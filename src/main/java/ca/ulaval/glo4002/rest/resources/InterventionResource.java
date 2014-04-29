package ca.ulaval.glo4002.rest.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.domain.intervention.InterventionNotFoundException;
import ca.ulaval.glo4002.domain.intervention.InterventionStatusParseException;
import ca.ulaval.glo4002.domain.intervention.InterventionTypeParseException;
import ca.ulaval.glo4002.domain.patient.PatientNotFoundException;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolExistsException;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolNotFoundException;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolRequiresSerialNumberException;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatusParseException;
import ca.ulaval.glo4002.rest.utils.ResponseAssembler;
import ca.ulaval.glo4002.services.InterventionService;
import ca.ulaval.glo4002.services.assemblers.InterventionAssembler;
import ca.ulaval.glo4002.services.assemblers.SurgicalToolAssembler;
import ca.ulaval.glo4002.services.dto.*;
import ca.ulaval.glo4002.services.dto.validators.*;

@Path("interventions/")
public class InterventionResource {
	public static final String ERROR_INT001 = "INT001";
	public static final String ERROR_INT002 = "INT002";
	public static final String ERROR_INT010 = "INT010";
	public static final String ERROR_INT011 = "INT011";
	public static final String ERROR_INT012 = "INT012";
	
	private InterventionService interventionService;

	public InterventionResource() {
		this.interventionService = new InterventionService();
	}

	public InterventionResource(InterventionService interventionService) {
		this.interventionService = interventionService;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createIntervention(InterventionCreationDTO interventionCreationDTO) throws Exception {
		try {
			Integer interventionId = interventionService.createIntervention(interventionCreationDTO, new InterventionCreationDTOValidator(),
					new InterventionAssembler());

			URI resourceLocationURI = getInterventionResourceLocationURI(interventionId);
			return ResponseAssembler.assembleCreatedResponse(resourceLocationURI);
		} catch (DTOValidationException | InterventionTypeParseException | InterventionStatusParseException e) {
			return ResponseAssembler.assembleErrorResponse(Status.BAD_REQUEST, ERROR_INT001, e.getMessage());
		} catch (PatientNotFoundException e) {
			return ResponseAssembler.assembleErrorResponse(Status.BAD_REQUEST, ERROR_INT002, e.getMessage());
		}
	}
	
	private URI getInterventionResourceLocationURI(Integer interventionId) throws URISyntaxException {
		return new URI(String.format("/interventions/%d", interventionId));
	}

	@POST
	@Path("{interventionNumber: [0-9]+}/instruments/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createSurgicalTool(SurgicalToolCreationDTO surgicalToolCreationDTO, @PathParam("interventionNumber") Integer interventionNumber)
			throws Exception {
		try {
			surgicalToolCreationDTO.interventionNumber = interventionNumber;

			Integer surgicalToolId = interventionService.createSurgicalTool(surgicalToolCreationDTO, new SurgicalToolCreationDTOValidator(),
					new SurgicalToolAssembler());

			URI resourceLocationURI = getSurgicalToolResourceLocationURI(interventionNumber, surgicalToolId, surgicalToolCreationDTO.typeCode);
			return ResponseAssembler.assembleCreatedResponse(resourceLocationURI);
		} catch (DTOValidationException | InterventionNotFoundException | SurgicalToolStatusParseException e) {
			return ResponseAssembler.assembleErrorResponse(Status.BAD_REQUEST, ERROR_INT010, e.getMessage());
		} catch (SurgicalToolExistsException e) {
			return ResponseAssembler.assembleErrorResponse(Status.BAD_REQUEST, ERROR_INT011, e.getMessage());
		} catch (SurgicalToolRequiresSerialNumberException e) {
			return ResponseAssembler.assembleErrorResponse(Status.BAD_REQUEST, ERROR_INT012, e.getMessage());
		}
	}
	
	private URI getSurgicalToolResourceLocationURI(Integer interventionNumber, Integer surgicalToolId, String typeCode) throws URISyntaxException {
		return new URI(String.format("/interventions/%d/instruments/%s/%s", interventionNumber, typeCode, surgicalToolId));
	}

	@PUT
	@Path("{interventionNumber: [0-9]+}/instruments/{surgicalToolTypeCode}/{surgicalToolSerialNumberOrId}/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifySurgicalTool(SurgicalToolModificationDTO surgicalToolModificationDTO, @PathParam("interventionNumber") Integer interventionNumber,
			@PathParam("surgicalToolTypeCode") String surgicalToolTypeCode, @PathParam("surgicalToolSerialNumberOrId") String surgicalToolSerialNumberOrId)
			throws Exception {

		try {
			surgicalToolModificationDTO.interventionNumber = interventionNumber;
			surgicalToolModificationDTO.serialNumberOrId = surgicalToolSerialNumberOrId;
			surgicalToolModificationDTO.typeCode = surgicalToolTypeCode;

			interventionService.modifySurgicalTool(surgicalToolModificationDTO, new SurgicalToolModificationDTOValidator());

			return ResponseAssembler.assembleOkResponse();
		} catch (DTOValidationException | InterventionNotFoundException | SurgicalToolNotFoundException | SurgicalToolStatusParseException e) {
			return ResponseAssembler.assembleErrorResponse(Status.BAD_REQUEST, ERROR_INT010, e.getMessage());
		} catch (SurgicalToolExistsException e) {
			return ResponseAssembler.assembleErrorResponse(Status.BAD_REQUEST, ERROR_INT011, e.getMessage());
		} catch (SurgicalToolRequiresSerialNumberException e) {
			return ResponseAssembler.assembleErrorResponse(Status.BAD_REQUEST, ERROR_INT012, e.getMessage());
		}
	}
}
