package ca.ulaval.glo4002.services.intervention;

import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.intervention.InterventionRepository;
import ca.ulaval.glo4002.domain.patient.PatientRepository;

public class InterventionServiceBuilder {
	protected InterventionRepository interventionRepository = null;
	protected PatientRepository patientRepository = null;
	protected EntityTransaction entityTransaction = null;
	
	public InterventionServiceBuilder interventionRepository(InterventionRepository prescriptionRepository) {
		this.interventionRepository = prescriptionRepository;
		return this;
	}
	
	public InterventionServiceBuilder patientRepository(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
		return this;
	}
	
	public InterventionServiceBuilder entityTransaction(EntityTransaction entityTransaction) {
		this.entityTransaction = entityTransaction;
		return this;
	}
	
	public InterventionService build() {
		InterventionService prescriptionService = new InterventionService(this);
		if (interventionRepository == null 
				|| patientRepository == null 
				|| entityTransaction == null) {
			throw new IllegalStateException();
		}
        return prescriptionService;
    }
}
