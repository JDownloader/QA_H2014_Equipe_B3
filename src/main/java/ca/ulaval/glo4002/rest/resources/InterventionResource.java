package ca.ulaval.glo4002.rest.resources;

import java.net.URI;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.services.InterventionService;
import ca.ulaval.glo4002.services.ServiceRequestException;
import ca.ulaval.glo4002.services.assemblers.InterventionAssembler;
import ca.ulaval.glo4002.services.assemblers.SurgicalToolAssembler;
import ca.ulaval.glo4002.services.dto.*;
import ca.ulaval.glo4002.services.dto.validators.*;

@Path("interventions/")
public class InterventionResource {
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

			String newResourceLocation = String.format("/interventions/%d", interventionId);
			return Response.status(Status.CREATED).location(new URI(newResourceLocation)).build();
		} catch (ServiceRequestException e) {
			return Response.status(Status.BAD_REQUEST).entity(new BadRequestDTO(e.getInternalCode(), e.getMessage())).build();
		}
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

			String newResourceLocation = String.format("/%s/%s", surgicalToolCreationDTO.typeCode, surgicalToolId);
			return Response.status(Status.CREATED).location(new URI(newResourceLocation)).build();
		} catch (ServiceRequestException e) {
			return Response.status(Status.BAD_REQUEST).entity(new BadRequestDTO(e.getInternalCode(), e.getMessage())).build();
		}
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

			return Response.status(Status.OK).build();
		} catch (ServiceRequestException e) {
			return Response.status(Status.BAD_REQUEST).entity(new BadRequestDTO(e.getInternalCode(), e.getMessage())).build();
		}
	}
}
