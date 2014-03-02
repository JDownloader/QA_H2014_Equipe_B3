package ca.ulaval.glo4002.services.surgicaltool;

import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.intervention.InterventionRepository;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolRepository;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.MarkExistingInstrumentRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.MarkNewInstrumentRequestParser;

public class SurgicalToolService {
	private SurgicalToolRepository surgicalToolRepository;
	private InterventionRepository interventionRepository;
	private EntityTransaction entityTransaction;
	
	public SurgicalToolService(SurgicalToolServiceBuilder builder) {
		//TODO
	}
	
	public void markNewInstrument(MarkNewInstrumentRequestParser request) throws ServiceRequestException {
		try {
			entityTransaction.begin();
			doMarkNewInstrument(request);
			entityTransaction.commit();
		} catch (ServiceRequestException exception) {
			throw exception;
		} finally {
			if(entityTransaction.isActive())
				entityTransaction.rollback();
		}
	}
	
	private void doMarkNewInstrument(MarkNewInstrumentRequestParser request) throws ServiceRequestException {
		//TODO
	}
	
	public void markExistingInstrument(MarkExistingInstrumentRequestParser request) throws ServiceRequestException {
		try {
			entityTransaction.begin();
			doMarkExistingInstrument(request);
			entityTransaction.commit();
		} catch (ServiceRequestException exception) {
			throw exception;
		} finally {
			if(entityTransaction.isActive())
				entityTransaction.rollback();
		}
	}
	
	private void doMarkExistingInstrument(MarkExistingInstrumentRequestParser request) throws ServiceRequestException {
		//TODO
	}
}
