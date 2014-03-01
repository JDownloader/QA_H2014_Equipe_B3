package ca.ulaval.glo4002.persistence.patient;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import ca.ulaval.glo4002.domain.patient.*;
import ca.ulaval.glo4002.persistence.HibernateRepository;

public class HibernatePatientRepository extends HibernateRepository implements PatientRepository {
	
	public HibernatePatientRepository() {
		super();
	}

	public HibernatePatientRepository(EntityManager entityManager) {
		super(entityManager);
	}

	public void create(Patient patient) throws EntityExistsException {
		entityManager.persist(patient);
	}

	public void update(Patient patient) {
		entityManager.merge(patient);
	}
	
	public Patient getById(int id) throws EntityNotFoundException {
		Patient patient = entityManager.find(Patient.class, id);
		if (patient == null) {
			throw new EntityNotFoundException(String.format("Cannot find Patient with id '%s'.", id));
		}
		return patient;
	}
}
