package ca.ulaval.glo4002.services.dto.validators;

import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;

public class SurgicalToolModificationDTOValidator {

	public void validate(SurgicalToolModificationDTO surgicalToolModificationDTO) {
		if ((surgicalToolModificationDTO.getNewStatus() == null)
				&& (surgicalToolModificationDTO.getNewSerialNumber() == null)) {
			throw new SurgicalToolCreationException("Parameter 'statut' and/or 'noserie' is required.");
		}		
	}
}
