package ca.ulaval.glo4002.rest.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.response.InterventionCreationResponse;
import ca.ulaval.glo4002.services.InterventionService;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

@Path("interventions/")
public class InterventionCreationResource {
	private InterventionService interventionService;
	private InterventionCreationResponse interventionCreationResponse = new InterventionCreationResponse();
	
	public InterventionCreationResource(){
		this.interventionService = new InterventionService();
	}
	
	//Utilisé dans les tests
	public InterventionCreationResource(InterventionService interventionService){
		this.interventionService = interventionService;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(InterventionCreationDTO interventionCreationDTO) throws ServiceRequestException {
		int interventionId = 0;	
		try {
				interventionId = interventionService.createIntervention(interventionCreationDTO);
			} catch (ServiceRequestException e) {
				return interventionCreationResponse.createBadRequestResponse(e);
			}
			return interventionCreationResponse.createSuccessResponse(interventionId);
	}
	
}
