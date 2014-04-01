package ca.ulaval.glo4002.services.assemblers;

import javax.persistence.EntityNotFoundException;

import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.domain.prescription.Prescription;
import ca.ulaval.glo4002.domain.staff.StaffMember;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDto;

public class PrescriptionAssembler {

	public Prescription assemblePrescription(PrescriptionCreationDto prescriptionCreationDto, DrugRepository drugRepository) throws EntityNotFoundException {
		Prescription prescription;

		if (prescriptionCreationDto.getDin() != null) {
			prescription = new Prescription(
					getDrug(prescriptionCreationDto, drugRepository),
					prescriptionCreationDto.getRenewals(), 
					prescriptionCreationDto.getDate(),
					new StaffMember(prescriptionCreationDto.getStaffMember()));
		} else {
			prescription = new Prescription(
					prescriptionCreationDto.getDrugName(),
					prescriptionCreationDto.getRenewals(), 
					prescriptionCreationDto.getDate(),
					new StaffMember(prescriptionCreationDto.getStaffMember()));
		}

		return prescription;
	}

	private Drug getDrug(PrescriptionCreationDto prescriptionCreationDto, DrugRepository drugRepository) {
		return drugRepository.getByDin(new Din(prescriptionCreationDto.getDin()));
	}
}
