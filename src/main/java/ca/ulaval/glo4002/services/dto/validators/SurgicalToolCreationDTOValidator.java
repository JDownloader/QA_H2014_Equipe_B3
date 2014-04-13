package ca.ulaval.glo4002.services.dto.validators;

import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;

public class SurgicalToolCreationDTOValidator {

	public void validate(SurgicalToolCreationDTO surgicalToolCreationDTO) {
		if (surgicalToolCreationDTO.typeCode == null) {
			throw new DTOValidationException("Parameter 'typecode' is required.");
		}
	}
}
