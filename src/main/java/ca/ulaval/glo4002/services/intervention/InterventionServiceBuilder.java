package ca.ulaval.glo4002.services.intervention;

import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.intervention.InterventionRepository;

public class InterventionServiceBuilder {
	protected InterventionRepository interventionRepository;
	protected EntityTransaction entityTransaction;
	
	public InterventionServiceBuilder interventionRepository(InterventionRepository prescriptionRepository) {
		this.interventionRepository = prescriptionRepository;
		return this;
	}
	
	public InterventionServiceBuilder entityTransaction(EntityTransaction entityTransaction) {
		this.entityTransaction = entityTransaction;
		return this;
	}
	
	public InterventionService build() {
		InterventionService prescriptionService = new InterventionService(this);
		if (interventionRepository == null 
				|| entityTransaction == null) {
			throw new IllegalStateException();
		}
        return prescriptionService;
    }
}
