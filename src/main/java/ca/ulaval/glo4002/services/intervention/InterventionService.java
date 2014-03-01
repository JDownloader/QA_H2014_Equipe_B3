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
	
	public Response markNewInstrument(MarkNewInstrumentRequest request) {
		Response response = null;
		try {
			entityTransaction.begin();
			doMarkNewInstrument(request);
			entityTransaction.commit();
		} catch (BadRequestException exception) {
			response = BadRequestJsonResponseBuilder.build(exception.getInternalCode(), exception.getMessage());
		} finally {
			if(entityTransaction.isActive())
				entityTransaction.rollback();
		}
		
		return response;
	}
	
	private void doMarkNewInstrument(MarkNewInstrumentRequest request) throws BadRequestException {
		//TODO
	}
	
	public Response markExistingInstrument(MarkExistingInstrumentRequest request) {
		Response response = null;
		try {
			entityTransaction.begin();
			doMarkExistingInstrument(request);
			entityTransaction.commit();
		} catch (BadRequestException exception) {
			response = BadRequestJsonResponseBuilder.build(exception.getInternalCode(), exception.getMessage());
		} finally {
			if(entityTransaction.isActive())
				entityTransaction.rollback();
		}

		return response;
	}
	
	private void doMarkExistingInstrument(MarkExistingInstrumentRequest request) throws BadRequestException {
		//TODO
	}
}
