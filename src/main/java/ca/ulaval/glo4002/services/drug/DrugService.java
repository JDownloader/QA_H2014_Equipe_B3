package ca.ulaval.glo4002.services.drug;

import java.util.List;

import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requestparsers.drug.DrugSearchRequestParser;

public class DrugService {
	private DrugRepository drugRepository;
	private EntityTransaction entityTransaction;
	
	public DrugService(DrugServiceBuilder builder) {
		this.entityTransaction = builder.entityTransaction;
		this.drugRepository = builder.drugRepository;
	}
	
	public List<Drug> searchDrug(DrugSearchRequestParser requestParser) throws BadRequestException {
		try {
			entityTransaction.begin();
			List<Drug> drugResults = doDrugSearch(requestParser);
			entityTransaction.commit();
			return drugResults;
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw e;
		}
	}
	
	protected List<Drug> doDrugSearch(DrugSearchRequestParser requestParser) throws BadRequestException {
		return drugRepository.findByName(requestParser.getName());
	}
}
