package ca.ulaval.glo4002.services.dto.validators;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;

public class PrescriptionCreationDTOValidator {
	public void validate(PrescriptionCreationDTO prescriptionCreationDTO) {
		if (prescriptionCreationDTO.renewals == null) {
			throw new DTOValidationException("Parameter 'renouvellements' is required.");
		} else if (prescriptionCreationDTO.renewals < 0) {
			throw new DTOValidationException("Parameter 'renouvellements' must be greater or equal to 0.");
		} else if (prescriptionCreationDTO.date == null) {
			throw new DTOValidationException("Parameter 'date' is required.");
		} else if (prescriptionCreationDTO.staffMember == null) {
			throw new DTOValidationException("Parameter 'intervenant' is required.");
		} 
		validateDinAndName(prescriptionCreationDTO);
	}
	
	private void validateDinAndName(PrescriptionCreationDTO prescriptionCreationDTO) {
		boolean hasDrugName = !StringUtils.isBlank(prescriptionCreationDTO.drugName);
		boolean hasDin = prescriptionCreationDTO.din != null;

		if (hasDrugName && hasDin) {
			throw new DTOValidationException("Either parameter 'din' or 'nom' must be specified, but not both.");
		} else if (!hasDrugName && !hasDin) {
			throw new DTOValidationException("Parameter 'din' or 'nom' must be specified.");
		}
	}
}
