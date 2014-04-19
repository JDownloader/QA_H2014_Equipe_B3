package ca.ulaval.glo4002.persistence;

import javax.persistence.EntityManager;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.intervention.InterventionNotFoundException;
import ca.ulaval.glo4002.domain.intervention.InterventionRepository;

public class HibernateInterventionRepository extends HibernateRepository implements InterventionRepository {

	public HibernateInterventionRepository() {
		super();
	}

	public HibernateInterventionRepository(EntityManager entityManager) {
		super(entityManager);
	}

	public void persist(Intervention intervention) {
		entityManager.persist(intervention);
	}

	public void update(Intervention intervention) {
		entityManager.merge(intervention);
	}

	public Intervention getById(Integer id) {
		Intervention intervention = entityManager.find(Intervention.class, id);
		if (intervention == null) {
			throw new InterventionNotFoundException(String.format("Impossible de trouver l'intervention avec id '%s'.", id));
		}
		return intervention;
	}
}
