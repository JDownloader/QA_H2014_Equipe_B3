package ca.ulaval.glo4002.persistence;

import java.util.List;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.drug.*;

public class HibernateDrugRepository extends HibernateRepository implements DrugRepository {

	public HibernateDrugRepository() {
		super();
	}

	public HibernateDrugRepository(EntityManager entityManager) {
		super(entityManager);
	}

	public void persist(Drug drug) {
		try {
			entityManager.persist(drug);
		} catch (EntityExistsException e) {
			throw new DrugExistsException(String.format("Un médicament avec le din '%s' existe déjà.", drug.getDin()), e);
		}
	}

	public Drug getByDin(Din din) {
		Drug drug = entityManager.find(Drug.class, din);
		if (drug == null) {
			throw new DrugNotFoundException(String.format("Impossible de trouver un médicament avec din '%s'.", din));
		}
		return drug;
	}

	public List<Drug> search(String keywords) {
		String keywordsWithWildcards = String.format("%%%s%%", keywords.replace(' ', '%'));

		final String DRUG_SEARCH_QUERY = "SELECT d FROM DRUG d WHERE UPPER(d.name) LIKE :keywords OR UPPER(d.description) LIKE :keywords";

		TypedQuery<Drug> query = entityManager.createQuery(DRUG_SEARCH_QUERY, Drug.class)
				.setParameter("keywords", keywordsWithWildcards.toUpperCase());

		return query.getResultList();
	}
}
