package ca.ulaval.glo4002.services.dto.validators;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;

public class SurgicalToolCreationDTOValidator {

	public void validate(SurgicalToolCreationDTO surgicalToolCreationDTO) {
		if (StringUtils.isBlank(surgicalToolCreationDTO.typeCode)) {
			throw new DTOValidationException("Le paramètre 'typecode' est requis.");
		} else if (surgicalToolCreationDTO.status == null) {
			throw new DTOValidationException("Le paramètre 'statut' est requis.");
		} else if (surgicalToolCreationDTO.status == null) {
			throw new DTOValidationException("Le paramètre 'statut' est requis.");
		} else if (surgicalToolCreationDTO.serialNumber != null && StringUtils.isBlank(surgicalToolCreationDTO.serialNumber)) {
			throw new DTOValidationException("Le paramètre 'noserie' ne peut être vide.");
		} 
	}
}
