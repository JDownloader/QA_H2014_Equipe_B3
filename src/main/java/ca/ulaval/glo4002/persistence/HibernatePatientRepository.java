package ca.ulaval.glo4002.persistence;

import javax.persistence.EntityManager;

import ca.ulaval.glo4002.domain.patient.*;
 
public class HibernatePatientRepository extends HibernateRepository implements PatientRepository {

	public HibernatePatientRepository() {
		super();
	}

	public HibernatePatientRepository(EntityManager entityManager) {
		super(entityManager);
	}

	public void persist(Patient patient) {
		entityManager.persist(patient);
	}

	public void merge(Patient patient) {
		entityManager.merge(patient);
	}

	public Patient getById(Integer id) {
		Patient patient = entityManager.find(Patient.class, id);
		if (patient == null) {
			throw new PatientNotFoundException(String.format("Impossible de trouver le patient avec id '%s'.", id));
		}
		return patient;
	}
}
