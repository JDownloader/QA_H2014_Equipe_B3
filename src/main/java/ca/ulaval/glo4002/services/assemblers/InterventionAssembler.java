package ca.ulaval.glo4002.services.assemblers;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;
 
public class InterventionAssembler {

	public Intervention assembleFromDTO(InterventionCreationDTO interventionCreationDto, InterventionFactory interventionFactory, PatientRepository patientRepository) {
		
		Patient patient = patientRepository.getById(interventionCreationDto.patientNumber);
		
		return interventionFactory.createIntervention(interventionCreationDto.description,
				interventionCreationDto.surgeon, interventionCreationDto.date,
				interventionCreationDto.room, InterventionType.fromString(interventionCreationDto.type),
				InterventionStatus.fromString(interventionCreationDto.status), patient);
	}
	
}
