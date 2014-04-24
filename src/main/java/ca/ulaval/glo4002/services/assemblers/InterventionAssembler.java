package ca.ulaval.glo4002.services.assemblers;

import ca.ulaval.glo4002.domain.intervention.Intervention;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionAssembler {

	public Intervention assembleFromDTO(InterventionCreationDTO interventionCreationDto, PatientRepository patientRepository) {
		
		Patient patient = patientRepository.getById(interventionCreationDto.patientNumber);
		
		return new Intervention(interventionCreationDto.description,
				interventionCreationDto.surgeon, interventionCreationDto.date,
				interventionCreationDto.room, interventionCreationDto.type,
				interventionCreationDto.status, patient);
	}
	
}
