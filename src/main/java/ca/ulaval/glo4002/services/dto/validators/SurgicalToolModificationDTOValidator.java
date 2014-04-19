package ca.ulaval.glo4002.services.dto.validators;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;

public class SurgicalToolModificationDTOValidator {

	public void validate(SurgicalToolModificationDTO surgicalToolModificationDTO) {
		if (surgicalToolModificationDTO.newStatus == null) {
			throw new DTOValidationException("Le paramètre 'statut' est requis.");
		} else if (StringUtils.equals(surgicalToolModificationDTO.newSerialNumber, "")) {
			throw new DTOValidationException("Le paramètre 'noserie' ne peut être vide.");
		}
	}
}
