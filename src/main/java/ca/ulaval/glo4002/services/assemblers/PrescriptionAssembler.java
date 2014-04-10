package ca.ulaval.glo4002.services.assemblers;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.domain.prescription.Prescription;
import ca.ulaval.glo4002.domain.staff.StaffMember;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;

public class PrescriptionAssembler {

	public Prescription fromDTO(PrescriptionCreationDTO prescriptionCreationDTO, DrugRepository drugRepository) {
		Prescription prescription;

		if (prescriptionCreationDTO.getDin() != null) {
			prescription = new Prescription(
					getDrug(prescriptionCreationDTO, drugRepository),
					prescriptionCreationDTO.getRenewals(), 
					prescriptionCreationDTO.getDate(),
					new StaffMember(prescriptionCreationDTO.getStaffMember()));
		} else {
			prescription = new Prescription(
					prescriptionCreationDTO.getDrugName(),
					prescriptionCreationDTO.getRenewals(), 
					prescriptionCreationDTO.getDate(),
					new StaffMember(prescriptionCreationDTO.getStaffMember()));
		}

		return prescription;
	}

	private Drug getDrug(PrescriptionCreationDTO prescriptionCreationDTO, DrugRepository drugRepository) {
		return drugRepository.getByDin(prescriptionCreationDTO.getDin());
	}
}
