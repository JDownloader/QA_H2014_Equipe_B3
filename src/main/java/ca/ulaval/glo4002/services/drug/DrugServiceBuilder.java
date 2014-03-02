package ca.ulaval.glo4002.services.drug;

import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.drug.DrugRepository;

public class DrugServiceBuilder {
	protected DrugRepository drugRepository = null;
	protected EntityTransaction entityTransaction = null;

	public DrugServiceBuilder drugRepository(DrugRepository drugRepository) {
		this.drugRepository = drugRepository;
		return this;
	}

	public DrugServiceBuilder entityTransaction(EntityTransaction entityTransaction) {
		this.entityTransaction = entityTransaction;
		return this;
	}

	public DrugService build() {
		DrugService drugService = new DrugService(this);
		if (drugRepository == null || entityTransaction == null) {
			throw new IllegalStateException();
		}
		return drugService;
	}
}
