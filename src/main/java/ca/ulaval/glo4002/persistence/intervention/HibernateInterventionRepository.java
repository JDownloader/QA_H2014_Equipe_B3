package ca.ulaval.glo4002.persistence.intervention;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

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

	public void update(Intervention intervention) {
		entityManager.merge(intervention);
	}

	public Intervention getById(int id) throws EntityNotFoundException {
		Intervention intervention = entityManager.find(Intervention.class, id);
		if (intervention == null) {
			throw new EntityNotFoundException(String.format("Cannot find Intervention with id '%s'.", id));
		}
		return intervention;
	}
}
