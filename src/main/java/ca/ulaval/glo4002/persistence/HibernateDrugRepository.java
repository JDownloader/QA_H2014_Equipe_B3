package ca.ulaval.glo4002.persistence;

import java.util.List;
import javax.persistence.*;
import org.apache.commons.lang3.StringUtils;
import ca.ulaval.glo4002.domain.drug.*;

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
			throw new EntityNotFoundException(String.format("Cannot find drug with din '%s'.", din));
		}
		return drug;
	}

	public List<Drug> search(String keywords) {
		validateSearchParameters(keywords);
		return performSearch(keywords);
	}

	private void validateSearchParameters(String name) {
		String wildcardInsensitiveSearchCriteria = name.replace(" ", "");
		if (StringUtils.isBlank(name) || wildcardInsensitiveSearchCriteria.length() < 3) {
			throw new IllegalArgumentException("Search criteria  must not be less than 3 characters, excluding wilcards.");
		}
	}

	private List<Drug> performSearch(String keywords) {
		String keywordsWithWildcards = keywords.replace(' ', '%').toUpperCase();
			
		final String DRUG_SEARCH_QUERY = "SELECT d FROM DRUG d WHERE UPPER(d.name) LIKE :keywords OR UPPER(d.description) LIKE :keywords";
		
		TypedQuery<Drug> query = entityManager.createQuery(DRUG_SEARCH_QUERY, Drug.class)
				.setParameter("keywords", keywordsWithWildcards);

		List<Drug> result = query.getResultList();
		return result;
	}
}
