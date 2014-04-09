package ca.ulaval.glo4002.services.dto.validators;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;

public class PrescriptionCreationDTOValidator {
	public void validate(PrescriptionCreationDTO prescriptionCreationDTO) {
		if (prescriptionCreationDTO.getRenewals() == null) {
			throw new PrescriptionCreationException("Parameter 'renouvellements' is required.");
		} else if (prescriptionCreationDTO.getRenewals() < 0) {
			throw new PrescriptionCreationException("Parameter 'renouvellements' must be greater or equal to 0.");
		}	else if (prescriptionCreationDTO.getDate() == null) {
			throw new PrescriptionCreationException("Parameter 'date' is required.");
		} else if (prescriptionCreationDTO.getStaffMember() == null) {
			throw new PrescriptionCreationException("Parameter 'intervenant' is required.");
		} 
		validateDinAndName(prescriptionCreationDTO);
	}
	
	private void validateDinAndName(PrescriptionCreationDTO prescriptionCreationDTO) {
		boolean hasDrugName = !StringUtils.isBlank(prescriptionCreationDTO.getDrugName());
		boolean hasDin = prescriptionCreationDTO.getDin() != null;

		if (hasDrugName && hasDin) {
			throw new PrescriptionCreationException("Either parameter 'din' or 'nom' must be specified, but not both.");
		} else if (!hasDrugName && !hasDin) {
			throw new PrescriptionCreationException("Parameter 'din' or 'nom' must be specified.");
		}
	}
}
