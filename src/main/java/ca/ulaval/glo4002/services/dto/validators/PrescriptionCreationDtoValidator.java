package ca.ulaval.glo4002.services.dto.validators;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.services.dto.PrescriptionCreationDto;

public class PrescriptionCreationDtoValidator {
	public void validate(PrescriptionCreationDto prescriptionCreationDto) {
		if (prescriptionCreationDto.getRenewals() == null) {
			throw new PrescriptionCreationException("Parameter 'renouvellements' is required.");
		} else if (prescriptionCreationDto.getRenewals() < 0) {
			throw new PrescriptionCreationException("Parameter 'renouvellements' must be greater or equal to 0.");
		}	else if (prescriptionCreationDto.getDate() == null) {
			throw new PrescriptionCreationException("Parameter 'date' is required.");
		} else if (prescriptionCreationDto.getStaffMember() == null) {
			throw new PrescriptionCreationException("Parameter 'intervenant' is required.");
		} 
		validateDinAndName(prescriptionCreationDto);
	}
	
	private void validateDinAndName(PrescriptionCreationDto prescriptionCreationDto) {
		boolean hasDrugName = !StringUtils.isBlank(prescriptionCreationDto.getDrugName());
		boolean hasDin = prescriptionCreationDto.getDin() != null;

		if (hasDrugName && hasDin) {
			throw new PrescriptionCreationException("Either parameter 'din' or 'nom' must be specified, but not both.");
		} else if (!hasDrugName && !hasDin) {
			throw new PrescriptionCreationException("Parameter 'din' or 'nom' must be specified.");
		}
	}
}
