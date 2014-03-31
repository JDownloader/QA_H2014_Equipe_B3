package ca.ulaval.glo4002.domain.prescription;

import javax.persistence.EntityNotFoundException;

import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.domain.staff.StaffMember;
import ca.ulaval.glo4002.rest.dto.PrescriptionCreationDto;

public class PrescriptionAssembler {

	public Prescription assemblePrescription(PrescriptionCreationDto prescriptionCreationDto, DrugRepository drugRepository) throws EntityNotFoundException {
		Prescription.Builder prescriptionBuilder = new Prescription.Builder(prescriptionCreationDto.getRenewals(), prescriptionCreationDto.getDate(),
				new StaffMember(prescriptionCreationDto.getStaffMember()));

		if (prescriptionCreationDto.hasDin()) {
			prescriptionBuilder.withDrug(getDrug(prescriptionCreationDto, drugRepository));
		} else if (prescriptionCreationDto.hasDrugName()) {
			prescriptionBuilder.withDrugName(prescriptionCreationDto.getDrugName());
		}

		return prescriptionBuilder.build();
	}

	private Drug getDrug(PrescriptionCreationDto prescriptionCreationDto, DrugRepository drugRepository) {
		return drugRepository.getByDin(new Din(prescriptionCreationDto.getDin()));
	}
}
