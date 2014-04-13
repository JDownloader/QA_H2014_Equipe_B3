package ca.ulaval.glo4002.services.dto.validators;

import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;

public class SurgicalToolModificationDTOValidator {

	public void validate(SurgicalToolModificationDTO surgicalToolModificationDTO) {
		if (surgicalToolModificationDTO.newStatus == null && surgicalToolModificationDTO.newSerialNumber == null) {
			throw new DTOValidationException("Parameter 'statut' and/or 'noserie' is required.");
		}
	}
}
