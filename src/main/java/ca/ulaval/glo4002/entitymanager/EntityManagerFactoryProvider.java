package ca.ulaval.glo4002.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ca.ulaval.glo4002.drug.DrugDAO;

public class DataAccessManager {
	private static final String PERSISTENCE_UNIT_NAME = "QA_H2014";
	private static EntityManagerFactory entityManagerFactory = null;
	protected static EntityManager entityManager = null;

	public static void init() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		DrugDAO.init();
	}

	public static void close() {
		entityManagerFactory.close();
	}
	
	public static EntityManager createEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
}
