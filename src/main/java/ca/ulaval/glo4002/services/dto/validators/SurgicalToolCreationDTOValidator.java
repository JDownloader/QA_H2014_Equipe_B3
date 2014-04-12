package ca.ulaval.glo4002.services.dto.validators;

import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;

public class SurgicalToolCreationDTOValidator {

	public void validate(SurgicalToolCreationDTO surgicalToolCreationDTO) {
		if (surgicalToolCreationDTO.getTypeCode() == null) {
			throw new SurgicalToolCreationException("Parameter 'typecode' is required.");
		}
	}
}
