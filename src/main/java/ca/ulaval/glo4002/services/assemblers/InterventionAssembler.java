package ca.ulaval.glo4002.services.assemblers;

import javax.persistence.EntityNotFoundException;

import ca.ulaval.glo4002.domain.intervention.Intervention;
import ca.ulaval.glo4002.domain.intervention.InterventionFactory;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.exceptions.domainexceptions.interventionexceptions.PatientDoesNotExist;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionAssembler {
	private InterventionFactory interventionFactory;
	
	public InterventionAssembler() {
		this.interventionFactory = new InterventionFactory();
	}
	
	public Intervention assembleInterventionFromDTO(InterventionCreationDTO dto, PatientRepository patientRepository) {
		Intervention intervention;
		
		Patient patient;
		try {
			patient = patientRepository.getById(dto.getPatientNumber());
		} catch (EntityNotFoundException e) {
			throw new PatientDoesNotExist();
		}

		intervention = interventionFactory.createIntervention(dto, patient);
		
		return intervention;
	}
	
}
