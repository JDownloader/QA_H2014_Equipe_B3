package ca.ulaval.glo4002.services.intervention;

import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.intervention.InterventionRepository;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.CreateInterventionRequest;

public class InterventionService {
	private InterventionRepository interventionRepository;
	private EntityTransaction entityTransaction;
	
	public InterventionService(InterventionServiceBuilder builder) {
		this.entityTransaction = builder.entityTransaction;
		this.interventionRepository = builder.interventionRepository;
	}
	
	public void createIntervention(CreateInterventionRequest interventionRequest) throws BadRequestException {
		
	}
}
