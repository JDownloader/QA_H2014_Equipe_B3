package ca.ulaval.glo4002.entitymanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EntityTransactionFactory {
	public static EntityTransaction getTransaction(EntityManager entityManager) {
		return entityManager.getTransaction();
	}
}
