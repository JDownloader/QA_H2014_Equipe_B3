package ca.ulaval.glo4002.rest.resources;

import java.net.URI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.services.InterventionService;
import ca.ulaval.glo4002.services.assemblers.InterventionAssembler;
import ca.ulaval.glo4002.services.dto.BadResponseDTO;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.InterventionCreationDTOValidator;

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
			int interventionId = interventionService.createIntervention(interventionCreationDTO, new InterventionCreationDTOValidator(), new InterventionAssembler());
			
			String newResourceLocation = String.format("/interventions/%d", interventionId);
			return Response.status(Status.CREATED).location(new URI(newResourceLocation)).build();
		} catch (ServiceRequestException e) {
			return Response.status(Status.BAD_REQUEST).entity(new BadResponseDTO(e.getInternalCode(), e.getMessage())).build();
		}
	}

}
