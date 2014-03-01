package ca.ulaval.glo4002.persistence.drug;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import ca.ulaval.glo4002.domain.drug.*;
import ca.ulaval.glo4002.persistence.HibernateRepository;

public class HibernateDrugRepository extends HibernateRepository implements DrugRepository {

	public HibernateDrugRepository() {
		super();
	}

	public HibernateDrugRepository(EntityManager entityManager) {
		super(entityManager);
	}

	public void create(Drug drug) {
		entityManager.persist(drug);
	}
	
	public Drug get(Din din) throws EntityNotFoundException {
		Drug drug = entityManager.find(Drug.class, din);
		if (drug == null) {
			throw new EntityNotFoundException(String.format("Cannot find Drug with din '%s'.", din));
		}
		return drug;
	}
}
