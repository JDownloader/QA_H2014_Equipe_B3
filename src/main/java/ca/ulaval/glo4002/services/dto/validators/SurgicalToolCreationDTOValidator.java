package ca.ulaval.glo4002.services.dto.validators;

import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;

public class SurgicalToolCreationDTOValidator {
	
	public void validate(SurgicalToolCreationDTO surgicalToolCreationDTO) {
		if (surgicalToolCreationDTO.getStatut() == null) { //TODO: inutile vu qu'on vérifie ça dans la création du tool?
			throw new SurgicalToolCreationException("Parameter 'statut' is required.");
		} else if (surgicalToolCreationDTO.getTypeCode() == null) {
			throw new SurgicalToolCreationException("Parameter 'typecode' is required.");
		} 
		
		//TODO: c'est ici qu'on vérifie si l'instrument n'est pas anonyme selon le type d'intervention?
		
	}
}

