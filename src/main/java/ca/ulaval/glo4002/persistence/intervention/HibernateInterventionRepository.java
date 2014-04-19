package ca.ulaval.glo4002.persistence.intervention;

import javax.persistence.EntityManager;

import ca.ulaval.glo4002.domain.intervention.*;
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

	public void update(Intervention intervention) {
		entityManager.merge(intervention);
	}

	public Intervention getById(Integer id) throws InterventionNotFoundException {
		Intervention intervention = entityManager.find(Intervention.class, id);
		if (intervention == null) {
			throw new InterventionNotFoundException(String.format("Impossible de trouver l'intervention avec id '%s'.", id));
		}
		return intervention;
	}
}
