package ca.ulaval.glo4002.domain.prescription;

import javax.persistence.EntityNotFoundException;

import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.domain.staff.StaffMember;
import ca.ulaval.glo4002.rest.dto.PrescriptionCreationDto;

public class PrescriptionFactory {
	
	public Prescription createPrescription(PrescriptionCreationDto prescriptionCreationDto, DrugRepository drugRepository) throws EntityNotFoundException {
		Prescription.Builder prescriptionBuilder = new Prescription.Builder( 
				prescriptionCreationDto.getRenewals(), 
				prescriptionCreationDto.getDate(), 
				new StaffMember(prescriptionCreationDto.getStaffMember()));
		
		if (prescriptionCreationDto.hasDin()) {
			prescriptionBuilder.withDrug(getDrug(prescriptionCreationDto, drugRepository));
		} else {
			prescriptionBuilder.withDrugName(prescriptionCreationDto.getDrugName());
		}
		
		return prescriptionBuilder.build();
	}

	private Drug getDrug(PrescriptionCreationDto prescriptionCreationDto, DrugRepository drugRepository) {
		return drugRepository.getByDin(new Din(prescriptionCreationDto.getDin()));
	}
}
