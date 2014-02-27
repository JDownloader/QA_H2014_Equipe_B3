package ca.ulaval.glo4002.persistence.drug;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
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
		return drug;
	}
}
