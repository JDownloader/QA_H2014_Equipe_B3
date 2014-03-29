package ca.ulaval.glo4002.rest.dto.validators;

import org.apache.commons.lang.StringUtils;

import ca.ulaval.glo4002.rest.dto.PrescriptionCreationDto;

public class PrescriptionCreationDtoValidator {
	public static void validate(PrescriptionCreationDto prescriptionCreationDto) {
		if (prescriptionCreationDto.getStaffMember() < 0) {
			throw new IllegalArgumentException("Parameter 'intervenant' must be greater or equal to 0.");
		} else if (prescriptionCreationDto.getRenewals() < 0) {
			throw new IllegalArgumentException("Parameter 'renouvellements' must be greater or equal to 0.");
		} else if (prescriptionCreationDto.getPatientNumber() < 0) {
			throw new IllegalArgumentException("Path parameter '$NO_PATIENT$' must be greater or equal to 0.");
		}
		validateDinAndName(prescriptionCreationDto);
	}
	
	private static void validateDinAndName(PrescriptionCreationDto prescriptionCreationDto) throws IllegalArgumentException {
		boolean drugNameValid = !StringUtils.isBlank(prescriptionCreationDto.getDrugName());
		boolean isDinValid = prescriptionCreationDto.getDin() >= 0;

		if (drugNameValid && isDinValid) {
			throw new IllegalArgumentException("Either parameter 'din' or 'nom' must be specified, but not both.");
		} else if (!drugNameValid && !isDinValid) {
			throw new IllegalArgumentException("Parameter 'din' or 'nom' must be specified.");
		}
	}
}
