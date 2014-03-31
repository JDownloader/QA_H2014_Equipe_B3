package ca.ulaval.glo4002.persistence;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;

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
