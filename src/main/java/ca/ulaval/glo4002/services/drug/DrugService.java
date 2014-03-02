package ca.ulaval.glo4002.services.drug;

import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.DrugSearchRequest;

public class DrugService {
	private DrugRepository drugRepository;
	private EntityTransaction entityTransaction;
	
	public DrugService(DrugServiceBuilder builder) {
		this.entityTransaction = builder.entityTransaction;
		this.drugRepository = builder.drugRepository;
	}
	
	public void searchDrug(DrugSearchRequest interventionRequest) throws BadRequestException {
		try {
			doDrugSearch(interventionRequest);
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}
	}
	
	protected void doDrugSearch(DrugSearchRequest interventionRequest) throws BadRequestException {
		
	}
}
