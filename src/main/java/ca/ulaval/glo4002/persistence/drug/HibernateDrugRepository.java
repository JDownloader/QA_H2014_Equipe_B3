package ca.ulaval.glo4002.persistence.drug;

import java.util.List;

import javax.persistence.*;

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
	
	public Drug getByDin(Din din) throws EntityNotFoundException {
		Drug drug = entityManager.find(Drug.class, din);
		if (drug == null) {
			throw new EntityNotFoundException(String.format("Cannot find Drug with din '%s'.", din));
		}
		return drug;
	}
	
	public List<Drug> findByName(String name) throws Exception {
		String likeStatement = name.replace(' ', '%').toUpperCase();
		String queryString = String.format("SELECT d FROM DRUG d WHERE UPPER(d.name) LIKE '%s' OR UPPER(d.description) LIKE '%s'", likeStatement, likeStatement);
		
		TypedQuery<Drug> query = entityManager.createQuery(queryString, Drug.class);
		
		List<Drug> result = query.getResultList();
		return result;
	}
}
