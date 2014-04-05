package ca.ulaval.glo4002.rest.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;
import ca.ulaval.glo4002.rest.response.InterventionCreationResponse;
import ca.ulaval.glo4002.services.InterventionService;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

@Path("interventions/")
public class InterventionCreationResource {
	private InterventionService interventionService;
	private InterventionCreationResponse interventionCreationResponse;
	
	//TODO est-ce pertinent de mettre un constructeur sans paramètre?
	/*public InterventionCreationResource(){
		this.interventionService = new InterventionService();
	}*/
	
	public InterventionCreationResource(InterventionService interventionService){
		this.interventionService = interventionService;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(InterventionCreationDTO interventionCreationDTO) throws ServiceRequestException {
			CreateInterventionRequestParser requestParser = null; //TODO delete when service will be refactored
			try {
				interventionService.createIntervention(requestParser);//TODO will have other parameters after service is refactored
			} catch (Exception e) {
				return interventionCreationResponse.createDefaultBadRequestResponse();
			}  
			//TODO: aller chercher l'id de l'intervention pour pouvoir le passer à la réponse, car mauvaise réponse en ce moment
			return Response.status(Status.CREATED).build();
	}
	
}
