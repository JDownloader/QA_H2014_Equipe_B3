package ca.ulaval.glo4002.persistence;

import javax.persistence.*;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientExistsException;
import ca.ulaval.glo4002.domain.patient.PatientNotFoundException;
import ca.ulaval.glo4002.domain.patient.PatientRepository;

public class HibernatePatientRepository extends HibernateRepository implements PatientRepository {

	public HibernatePatientRepository() {
		super();
	}

	public HibernatePatientRepository(EntityManager entityManager) {
		super(entityManager);
	}

	public void persist(Patient patient) {
		try {
			entityManager.persist(patient);
		} catch (EntityExistsException e) {
			throw new PatientExistsException("Ce patient existe déjà.", e);
		}
	}

	public void update(Patient patient) {
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
