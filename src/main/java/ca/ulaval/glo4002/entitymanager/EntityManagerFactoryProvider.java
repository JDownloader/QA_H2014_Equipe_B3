package ca.ulaval.glo4002.entitymanager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider {
	private static final String PERSISTENCE_UNIT_NAME = "QA_H2014";
	private static EntityManagerFactory entityManagerFactory = null;

	public static EntityManagerFactory getFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		return entityManagerFactory;
	}
}
