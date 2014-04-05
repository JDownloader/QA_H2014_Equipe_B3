package ca.ulaval.glo4002.services.dto.validators;

import javax.ws.rs.core.Response;
import ca.ulaval.glo4002.rest.response.InterventionCreationResponse;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionCreationDTOValidator {
	InterventionCreationResponse interventionCreationResponse = new InterventionCreationResponse();
	
	public Response validateDTO(InterventionCreationDTO interventionCreationDTO){
		if((interventionCreationDTO.getDescription() == null) || (interventionCreationDTO.getDate() == null) 
				|| (interventionCreationDTO.getPatientNumber() == null) || (interventionCreationDTO.getRoom() == null) 
				|| (interventionCreationDTO.getSurgeonNumber() == null) || (interventionCreationDTO.getType() == null)){
			return interventionCreationResponse.createCustomBadRequestMissingInformationResponse();
		}
		//TODO validate date
		else if(interventionCreationDTO.getPatientNumber() < 0){
			return interventionCreationResponse.createCustomBadRequestNonExistingPatientResponse();
		}
		return interventionCreationResponse.createDefaultAcceptedResponse();
	}
}
