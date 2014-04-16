package ca.ulaval.glo4002.persistence;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import ca.ulaval.glo4002.domain.prescription.Prescription;
import ca.ulaval.glo4002.domain.prescription.PrescriptionExistsException;
import ca.ulaval.glo4002.domain.prescription.PrescriptionRepository;

public class HibernatePrescriptionRepository extends HibernateRepository implements PrescriptionRepository {

	public void persist(Prescription prescription) {
		try {
			entityManager.persist(prescription);
		} catch (EntityExistsException e) { 
			throw new PrescriptionExistsException("Cette prescription existe déjà.", e);
		}
	}

	public HibernatePrescriptionRepository() {
		super();
	}

	public HibernatePrescriptionRepository(EntityManager entityManager) {
		super(entityManager);
	}
}
