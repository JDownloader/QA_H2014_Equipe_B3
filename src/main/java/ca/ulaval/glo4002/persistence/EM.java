package ca.ulaval.glo4002.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EM {

	private static final String PERSISTENCE_UNIT_NAME = "QA_H2014";
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static EntityTransaction userTransaction;

	public static void setEntityManager() {
		entityManagerFactory = Persistence
				.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		entityManager = entityManagerFactory.createEntityManager();
		userTransaction = entityManager.getTransaction();
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}

	public static EntityTransaction getUserTransaction() {
		return userTransaction;
	}

	public static void closeEntityManager() {
		entityManager.close();
		entityManagerFactory.close();
	}

	public static void persist(Object object) {
		userTransaction.begin();
		entityManager.persist(object);
		userTransaction.commit();
	}

}