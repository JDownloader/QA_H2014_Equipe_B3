package ca.ulaval.glo4002.services.surgicalTool;

import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.intervention.InterventionRepository;
import ca.ulaval.glo4002.domain.surgicalTool.SurgicalToolRepository;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.MarkExistingInstrumentRequest;
import ca.ulaval.glo4002.rest.requests.MarkNewInstrumentRequest;

public class SurgicalToolService {
	private SurgicalToolRepository surgicalToolRepository;
	private InterventionRepository interventionRepository;
	private EntityTransaction entityTransaction;
	
	public SurgicalToolService(SurgicalToolServiceBuilder builder) {
		//TODO
	}
	
	public void markNewInstrument(MarkNewInstrumentRequest request) throws BadRequestException {
		try {
			entityTransaction.begin();
			doMarkNewInstrument(request);
			entityTransaction.commit();
		} catch (BadRequestException exception) {
			throw exception;
		} finally {
			if(entityTransaction.isActive())
				entityTransaction.rollback();
		}
	}
	
	private void doMarkNewInstrument(MarkNewInstrumentRequest request) throws BadRequestException {
		//TODO
	}
	
	public void markExistingInstrument(MarkExistingInstrumentRequest request) throws BadRequestException {
		try {
			entityTransaction.begin();
			doMarkExistingInstrument(request);
			entityTransaction.commit();
		} catch (BadRequestException exception) {
			throw exception;
		} finally {
			if(entityTransaction.isActive())
				entityTransaction.rollback();
		}
	}
	
	private void doMarkExistingInstrument(MarkExistingInstrumentRequest request) throws BadRequestException {
		//TODO
	}
}
