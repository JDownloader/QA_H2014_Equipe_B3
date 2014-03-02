package ca.ulaval.glo4002.services.intervention;

import javax.persistence.EntityTransaction;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.domain.intervention.InterventionRepository;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.MarkExistingInstrumentRequest;
import ca.ulaval.glo4002.rest.requests.MarkInstrumentRequest;
import ca.ulaval.glo4002.rest.requests.MarkNewInstrumentRequest;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;

public class InterventionService {
	private InterventionRepository interventionRepository;
	private EntityTransaction entityTransaction;
	
	public InterventionService(InterventionServiceBuilder builder) {
		this.entityTransaction = builder.entityTransaction;
		this.interventionRepository = builder.interventionRepository;
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
