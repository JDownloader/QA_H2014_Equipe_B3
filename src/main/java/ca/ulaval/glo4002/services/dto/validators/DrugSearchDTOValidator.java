package ca.ulaval.glo4002.services.dto.validators;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.services.dto.DrugSearchDTO;

public class DrugSearchDTOValidator {
	
	private static final String EMPTY_STRING = "";
	private static final String SEARCH_WILDCARD = " ";
	private static final int MINIMUM_LENGTH = 3;

	public void validate(DrugSearchDTO drugSearchDTO) {
		String searchCriteria = drugSearchDTO.name;
		
		if (StringUtils.isBlank(searchCriteria)) {
			throw new DTOValidationException("Le paramètre 'nom' est requis.");
		} else if (searchCriteria.replace(SEARCH_WILDCARD, EMPTY_STRING).length() < MINIMUM_LENGTH) {
			throw new DTOValidationException("La taille minimale du critère de recherche (excluant les patrons génériques) est de 3 caractères.");
		}
	}
	
}
