package ca.ulaval.glo4002.services.dto.validators;

import ca.ulaval.glo4002.exceptions.domainexceptions.InvalidArgument;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionCreationDTOValidator {
	private String INVALID_ARGUMENT_CODE = "INT001";
	private String INVALID_ARGUMENT_MESSAGE = "Erreur - informations manquantes ou invalides";
	
	public void validate(InterventionCreationDTO interventionCreationDTO) {
			if(interventionCreationDTO.getDescription() == null){
				throw new InvalidArgument(INVALID_ARGUMENT_CODE, INVALID_ARGUMENT_MESSAGE);
			}
			else if(interventionCreationDTO.getDate() == null) {
				throw new InvalidArgument(INVALID_ARGUMENT_CODE, INVALID_ARGUMENT_MESSAGE);
			}
			else if(interventionCreationDTO.getPatientNumber() == null){
				throw new InvalidArgument(INVALID_ARGUMENT_CODE, INVALID_ARGUMENT_MESSAGE);
			}
			else if(interventionCreationDTO.getRoom() == null) {
				throw new InvalidArgument(INVALID_ARGUMENT_CODE, INVALID_ARGUMENT_MESSAGE);
			}
			else if(interventionCreationDTO.getSurgeonNumber() == null){
				throw new InvalidArgument(INVALID_ARGUMENT_CODE, INVALID_ARGUMENT_MESSAGE);
			}
			else if(interventionCreationDTO.getType() == null){
				throw new InvalidArgument(INVALID_ARGUMENT_CODE, INVALID_ARGUMENT_MESSAGE);
			}
			else if(interventionCreationDTO.getPatientNumber() < 0){
				throw new InvalidArgument(INVALID_ARGUMENT_CODE, INVALID_ARGUMENT_MESSAGE);
			}
			else if(interventionCreationDTO.getSurgeonNumber() < 0){
				throw new InvalidArgument(INVALID_ARGUMENT_CODE, INVALID_ARGUMENT_MESSAGE);
			}
		}
}