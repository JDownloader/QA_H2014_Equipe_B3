package ca.ulaval.glo4002.services.assemblers;

import ca.ulaval.glo4002.domain.intervention.Intervention;
import ca.ulaval.glo4002.domain.intervention.InterventionFactory;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionAssembler {
	private InterventionFactory interventionFactory;
		
	public InterventionAssembler() {
		this.interventionFactory = new InterventionFactory();
	}
	
	public Intervention assembleFromDTO(InterventionCreationDTO interventionCreationDto, PatientRepository patientRepository) {
		
		Patient patient = patientRepository.getById(interventionCreationDto.patientNumber);
		
		return interventionFactory.createFromDTO(interventionCreationDto, patient);
	}
	
}
