package ca.ulaval.glo4002.services.dto.validators;

import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionCreationDTOValidator {
	
	public void validate(InterventionCreationDTO interventionCreationDTO) {
			if(interventionCreationDTO.getDescription() == null){
				throw new InvalidDTOAttribute();
			}
			else if(interventionCreationDTO.getDate() == null) {
				throw new InvalidDTOAttribute();
			}
			else if(interventionCreationDTO.getPatientNumber() == null){
				throw new InvalidDTOAttribute();
			}
			else if(interventionCreationDTO.getRoom() == null) {
				throw new InvalidDTOAttribute();
			}
			else if(interventionCreationDTO.getSurgeonNumber() == null){
				throw new InvalidDTOAttribute();
			}
			else if(interventionCreationDTO.getType() == null){
				throw new InvalidDTOAttribute();
			}
			//TODO : following cases belong to domain logic?
			else if(interventionCreationDTO.getPatientNumber() < 0){
				throw new InvalidDTOAttribute();
			}
			else if(interventionCreationDTO.getSurgeonNumber() < 0){
				throw new InvalidDTOAttribute();
			}
		}
}