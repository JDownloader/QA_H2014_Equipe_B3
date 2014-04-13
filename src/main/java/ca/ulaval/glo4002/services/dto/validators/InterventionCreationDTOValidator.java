package ca.ulaval.glo4002.services.dto.validators;

import ca.ulaval.glo4002.exceptions.InterventionValidationException;
import ca.ulaval.glo4002.rest.response.InterventionCreationResponse;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionCreationDTOValidator {
	InterventionCreationResponse interventionCreationResponse = new InterventionCreationResponse();
	
	public void validate(InterventionCreationDTO interventionCreationDTO) throws InterventionValidationException {
		if(interventionCreationDTO.getDescription() == null){
			throw new InterventionValidationException("Parameter 'description' is required");
		}
		else if(interventionCreationDTO.getSurgeonNumber() == null){
			throw new InterventionValidationException("Parameter 'chirurgien' is required");
		}
		else if(interventionCreationDTO.getDate() == null) {
			throw new InterventionValidationException("Parameter 'date' is required");
		}
		else if(interventionCreationDTO.getPatientNumber() == null){
			throw new InterventionValidationException("Parameter 'patient' is required");
		}
		else if(interventionCreationDTO.getRoom() == null) {
			throw new InterventionValidationException("Parameter 'salle' is required");
		}
		else if(interventionCreationDTO.getType() == null){
			throw new InterventionValidationException("Parameter 'type' is required");
		}
		else if(interventionCreationDTO.getPatientNumber() < 0){
			throw new InterventionValidationException("Parameter 'patient' must be greater than 0");
		}
		else if(interventionCreationDTO.getSurgeonNumber() < 0){
			throw new InterventionValidationException("Parameter 'chirurgien' must be greater than 0");
		}
	}
}
