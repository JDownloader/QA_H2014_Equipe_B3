package ca.ulaval.glo4002.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolExistsException;

public class HibernateInterventionRepository extends HibernateRepository implements InterventionRepository {

	private static final String UNIQUE_SERIALNUMBER_CONSTRAINT_NAME = "UQ_SERIALNUMBER";

	public HibernateInterventionRepository() {
		super();
	}

	public HibernateInterventionRepository(EntityManager entityManager) {
		super(entityManager);
	}

	public void persist(Intervention intervention) {
		try {
    		entityManager.persist(intervention);
    		entityManager.flush();
		} catch (PersistenceException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				String contraintName = ((ConstraintViolationException) e.getCause()).getConstraintName();
				if (contraintName.contains(UNIQUE_SERIALNUMBER_CONSTRAINT_NAME)) {
					throw new SurgicalToolExistsException("Un instrument avec le numéro de série spécifié existe déjà.", e);
				}
			}
			throw e;
		}
	}

	public Intervention getById(Integer id) {
		Intervention intervention = entityManager.find(Intervention.class, id);
		if (intervention == null) {
			throw new InterventionNotFoundException(String.format("Impossible de trouver l'intervention avec id '%s'.", id));
		}
		return intervention;
	}
}
