package ca.ulaval.glo4002.services.dto.validators;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.services.dto.DrugSearchDTO;

public class DrugSearchDTOValidator {
	
	public void validate(DrugSearchDTO drugSearchDTO) {
		String searchCriteria = drugSearchDTO.name;
		
		if (StringUtils.isBlank(searchCriteria)) {
			throw new DTOValidationException("Le paramètre 'nom' est requis.");
		} else if (searchCriteria.replace(" ", "").length() < 3) {
			throw new DTOValidationException("La taille minimale du critère de recherche (excluant les patrons génériques) est de 3 caractères.");
		}
	}
	
}
