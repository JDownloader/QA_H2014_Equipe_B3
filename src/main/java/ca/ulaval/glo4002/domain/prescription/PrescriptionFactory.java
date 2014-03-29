package ca.ulaval.glo4002.domain.prescription;

import javax.persistence.EntityNotFoundException;

import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugFactory;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.domain.staff.StaffMember;
import ca.ulaval.glo4002.rest.dto.PrescriptionCreationDto;

public class PrescriptionFactory {
	public Prescription createPrescription(PrescriptionCreationDto prescriptionCreationDto, DrugRepository drugRepository) throws EntityNotFoundException {
		Drug drug = getDrug(prescriptionCreationDto, drugRepository);

		return new Prescription(drug, 
				prescriptionCreationDto.getRenewals(), 
				prescriptionCreationDto.getDate(), 
				new StaffMember(prescriptionCreationDto.getStaffMember()));
	}
	
	private Drug getDrug(PrescriptionCreationDto prescriptionCreationDto, DrugRepository drugRepository) throws EntityNotFoundException {
		if (prescriptionCreationDto.hasDin()) {
			return drugRepository.getByDin(new Din(prescriptionCreationDto.getDin()));
		} else {
			return new DrugFactory().createDrug(prescriptionCreationDto.getDrugName());
		}
	}
}
