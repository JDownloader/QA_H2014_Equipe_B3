package ca.ulaval.glo4002.services.surgicalTools;

import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.intervention.InterventionRepository;
import ca.ulaval.glo4002.domain.surgicalTool.SurgicalToolRepository;

public class SurgicalToolServiceBuilder {
	protected SurgicalToolRepository surgicalToolRepository = null;
	protected InterventionRepository interventionRepository = null;
	protected EntityTransaction entityTransaction = null;
	
	public SurgicalToolServiceBuilder surgicalToolRepository(SurgicalToolRepository surgicalToolRepository) {
		this.surgicalToolRepository = surgicalToolRepository;
		return this;
	}
	
	public SurgicalToolServiceBuilder interventionRepository(InterventionRepository interventionRepository) {
		this.interventionRepository = interventionRepository;
		return this;
	}
	
	public SurgicalToolServiceBuilder entityTransaction(EntityTransaction entityTransaction) {
		this.entityTransaction = entityTransaction;
		return this;
	}
	
	public SurgicalToolService build() {
		SurgicalToolService surgicalToolService = new SurgicalToolService(this);
		if(surgicalToolRepository == null
			|| interventionRepository == null
			|| entityTransaction == null) {
			throw new IllegalStateException();
		}
		return surgicalToolService;
	}
}
