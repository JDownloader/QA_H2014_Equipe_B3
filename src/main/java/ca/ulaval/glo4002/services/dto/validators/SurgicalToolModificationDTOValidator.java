package ca.ulaval.glo4002.services.dto.validators;

import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;

public class SurgicalToolModificationDTOValidator {

	public void validate(SurgicalToolModificationDTO surgicalToolModificationDTO) {
		if (surgicalToolModificationDTO.getNewStatus() == null) {
			throw new SurgicalToolCreationException("Parameter 'statut' is required.");
			//TODO: Verify if it is really required
		}
	}
}
