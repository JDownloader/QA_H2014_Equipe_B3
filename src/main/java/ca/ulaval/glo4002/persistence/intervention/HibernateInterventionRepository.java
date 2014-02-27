package ca.ulaval.glo4002.persistence.intervention;

import javax.persistence.EntityManager;

import ca.ulaval.glo4002.domain.intervention.Intervention;
import ca.ulaval.glo4002.domain.intervention.InterventionRepository;
import ca.ulaval.glo4002.persistence.HibernateRepository;

public class HibernateInterventionRepository extends HibernateRepository implements InterventionRepository {
	
	public HibernateInterventionRepository() {
		super();
	}

	public HibernateInterventionRepository(EntityManager entityManager) {
		super(entityManager);
	}

	public void create(Intervention entity) {
		entityManager.persist(entity);
	}
}
