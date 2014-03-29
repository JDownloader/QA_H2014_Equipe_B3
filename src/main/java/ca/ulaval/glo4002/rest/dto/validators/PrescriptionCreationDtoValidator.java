package ca.ulaval.glo4002.rest.dto.validators;

import ca.ulaval.glo4002.rest.dto.PrescriptionCreationDto;

public class PrescriptionCreationDtoValidator {
	public static void validate(PrescriptionCreationDto prescriptionCreationDto) {
		if (!prescriptionCreationDto.hasRenewals()) {
			throw new IllegalArgumentException("Parameter 'renouvellements' is required.");
		} else if (prescriptionCreationDto.getRenewals() < 0) {
			throw new IllegalArgumentException("Parameter 'renouvellements' must be greater or equal to 0.");
		} else if (!prescriptionCreationDto.hasDate()) {
			throw new IllegalArgumentException("Parameter 'date' is required.");
		} else if (!prescriptionCreationDto.hasStaffMember()) {
			throw new IllegalArgumentException("Parameter 'intervenant' is required.");
		} 
		validateDinAndName(prescriptionCreationDto);
	}
	
	private static void validateDinAndName(PrescriptionCreationDto prescriptionCreationDto) throws IllegalArgumentException {
		boolean hasDrugName = prescriptionCreationDto.hasDrugName();
		boolean hasDin = prescriptionCreationDto.hasDin();

		if (hasDrugName && hasDin) {
			throw new IllegalArgumentException("Either parameter 'din' or 'nom' must be specified, but not both.");
		} else if (!hasDrugName && !hasDin) {
			throw new IllegalArgumentException("Parameter 'din' or 'nom' must be specified.");
		}
	}
}
