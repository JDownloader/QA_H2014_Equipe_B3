package ca.ulaval.glo4002.services.dto.validators;

import ca.ulaval.glo4002.exceptions.domainexceptions.interventionexceptions.InvalidArgument;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionCreationDTOValidator {
	
	public void validate(InterventionCreationDTO interventionCreationDTO) {
			if(interventionCreationDTO.getDescription() == null){
				throw new InvalidArgument();
			}
			else if(interventionCreationDTO.getDate() == null) {
				throw new InvalidArgument();
			}
			else if(interventionCreationDTO.getPatientNumber() == null){
				throw new InvalidArgument();
			}
			else if(interventionCreationDTO.getRoom() == null) {
				throw new InvalidArgument();
			}
			else if(interventionCreationDTO.getSurgeonNumber() == null){
				throw new InvalidArgument();
			}
			else if(interventionCreationDTO.getType() == null){
				throw new InvalidArgument();
			}
			else if(interventionCreationDTO.getPatientNumber() < 0){
				throw new InvalidArgument();
			}
			else if(interventionCreationDTO.getSurgeonNumber() < 0){
				throw new InvalidArgument();
			}
		}
}