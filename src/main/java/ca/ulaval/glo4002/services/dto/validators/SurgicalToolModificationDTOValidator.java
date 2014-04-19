package ca.ulaval.glo4002.services.dto.validators;

import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;

public class SurgicalToolModificationDTOValidator {

	public void validate(SurgicalToolModificationDTO surgicalToolModificationDTO) {
		if (surgicalToolModificationDTO.newStatus == null) {
			throw new DTOValidationException("Le paramètre 'statut' est requis.");
		} else if (surgicalToolModificationDTO.newSerialNumber.equals("")) {
			throw new DTOValidationException("Le paramètre 'noserie' ne peut être vide.");
		}
	}
}
