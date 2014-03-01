package ca.ulaval.glo4002.services.intervention;

import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.intervention.InterventionRepository;

public class InterventionServiceBuilder {
	protected InterventionRepository interventionRepository;
	protected EntityTransaction entityTransaction;
	
	public InterventionServiceBuilder InterventionRepository(InterventionRepository interventionRepository) {
		this.interventionRepository = interventionRepository;
		return this;
	}
	
	public InterventionService build() {
		InterventionService interventionService = new InterventionService(this);
		if(interventionRepository == null)
			throw new IllegalStateException();
		return interventionService;
	}
}
