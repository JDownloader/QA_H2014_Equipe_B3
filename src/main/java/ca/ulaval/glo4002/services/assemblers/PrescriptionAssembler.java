package ca.ulaval.glo4002.services.assemblers;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.domain.prescription.Prescription;
import ca.ulaval.glo4002.domain.staff.StaffMember;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;

public class PrescriptionAssembler {

	public Prescription assembleFromDTO(PrescriptionCreationDTO prescriptionCreationDTO, DrugRepository drugRepository) {
		Prescription prescription;

		if (prescriptionCreationDTO.din != null) {
			prescription = new Prescription(
					getDrug(prescriptionCreationDTO, drugRepository),
					prescriptionCreationDTO.renewals, 
					prescriptionCreationDTO.date,
					new StaffMember(prescriptionCreationDTO.staffMember));
		} else {
			prescription = new Prescription(
					prescriptionCreationDTO.drugName,
					prescriptionCreationDTO.renewals, 
					prescriptionCreationDTO.date,
					new StaffMember(prescriptionCreationDTO.staffMember));
		}

		return prescription;
	}

	private Drug getDrug(PrescriptionCreationDTO prescriptionCreationDTO, DrugRepository drugRepository) {
		return drugRepository.getByDin(prescriptionCreationDTO.din);
	}
}
