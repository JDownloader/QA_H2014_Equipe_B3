package ca.ulaval.glo4002.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DataAccessTransaction {
	private EntityManager entityManager = null;
	EntityTransaction transaction = null;

	public DataAccessTransaction() {
		entityManager = DataAccessManager.createEntityManager();
	}

	public void begin() {
		transaction = entityManager.getTransaction();
		transaction.begin();
	}

	public void commit() {
		transaction.commit();
		entityManager.close();
	}

	public void rollback() {
		transaction.rollback();
		entityManager.close();
	}

	public boolean isActive() {
		return transaction.isActive();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
}
