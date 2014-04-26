package ca.ulaval.glo4002.services.dto.validators;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;

public class PrescriptionCreationDTOValidator {
	public void validate(PrescriptionCreationDTO prescriptionCreationDTO) {
		if (prescriptionCreationDTO.renewals == null) {
			throw new DTOValidationException("Le paramètre 'renouvellements' est requis.");
		} else if (prescriptionCreationDTO.renewals < 0) {
			throw new DTOValidationException("Le paramètre 'renouvellements' doit être égal ou supérieur à 0.");
		} else if (prescriptionCreationDTO.date == null) {
			throw new DTOValidationException("Le paramètre 'date' est requis.");
		} else if (StringUtils.isBlank(prescriptionCreationDTO.staffMember)) {
			throw new DTOValidationException("Le paramètre 'intervenant' est requis.");
		} 
		validateDinAndName(prescriptionCreationDTO);
	}
	
	private void validateDinAndName(PrescriptionCreationDTO prescriptionCreationDTO) {
		boolean hasDrugName = !StringUtils.isBlank(prescriptionCreationDTO.drugName);
		boolean hasDin = prescriptionCreationDTO.din != null;

		if (hasDrugName && hasDin) {
			throw new DTOValidationException("Un seul des paramètres 'din' ou 'nom' doit être spécifié.");
		} else if (!hasDrugName && !hasDin) {
			throw new DTOValidationException("Les paramètres 'din' ou 'nom' sont requis.");
		}
	}
}
