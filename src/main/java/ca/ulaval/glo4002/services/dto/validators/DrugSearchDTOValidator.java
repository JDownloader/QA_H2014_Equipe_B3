package ca.ulaval.glo4002.services.dto.validators;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.services.dto.DrugSearchDTO;

public class DrugSearchDTOValidator {
	
	public void validate(DrugSearchDTO drugSearchDTO) {
		String searchCriteria = drugSearchDTO.name;
		
		if (StringUtils.isBlank(searchCriteria)) {
			throw new DTOValidationException("Parameter 'nom' is required.");
		} else if (searchCriteria.replace(" ", "").length() < 3) {
			throw new DTOValidationException("Length of search criteria (excluding wildcards) must not be less than 3 characters.");
		}
	}
	
}
