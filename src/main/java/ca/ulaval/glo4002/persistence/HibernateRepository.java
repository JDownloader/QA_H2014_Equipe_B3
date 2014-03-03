package ca.ulaval.glo4002.persistence;

import javax.persistence.EntityManager;

import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;

public abstract class HibernateRepository {
	protected EntityManager entityManager = null;

	public HibernateRepository() {
		entityManager = new EntityManagerProvider().getEntityManager();
	}

	public HibernateRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
