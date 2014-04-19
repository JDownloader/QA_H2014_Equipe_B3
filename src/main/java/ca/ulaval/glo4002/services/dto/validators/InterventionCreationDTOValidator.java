package ca.ulaval.glo4002.services.dto.validators;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionCreationDTOValidator {
	
	public void validate(InterventionCreationDTO interventionCreationDTO) {
		if (StringUtils.isBlank(interventionCreationDTO.description)) {
			throw new DTOValidationException("Le paramètre 'description' est requis.");
		} else if (interventionCreationDTO.date == null) {
			throw new DTOValidationException("Le paramètre 'date' est requis.");
		} else if (interventionCreationDTO.patientNumber == null) {
			throw new DTOValidationException("Le paramètre 'patient' est requis.");
		} else if (StringUtils.isBlank(interventionCreationDTO.room)) {
			throw new DTOValidationException("Le paramètre 'salle' est requis.");
		} else if (interventionCreationDTO.surgeon == null) {
			throw new DTOValidationException("Le paramètre 'chirurgien' est requis.");
		} else if (interventionCreationDTO.type == null) {
			throw new DTOValidationException("Le paramètre 'type' est requis.");
		}
	}
}