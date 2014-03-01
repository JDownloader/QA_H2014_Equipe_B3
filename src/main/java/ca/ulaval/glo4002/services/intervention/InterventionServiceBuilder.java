package ca.ulaval.glo4002.services.intervention;

import ca.ulaval.glo4002.domain.intervention.InterventionRepository;

public class InterventionServiceBuilder {
	protected InterventionRepository interventionRepository;
	
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
