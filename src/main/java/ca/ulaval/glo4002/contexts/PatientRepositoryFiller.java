package ca.ulaval.glo4002.contexts;

import javax.persistence.EntityManager;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;

public class PatientRepositoryFiller {

	public void fill(EntityManager entityManager, PatientRepository patientRepository) {
		entityManager.getTransaction().begin();
		patientRepository.persist(new Patient(0));
		patientRepository.persist(new Patient(1));
		patientRepository.persist(new Patient(2));
		entityManager.getTransaction().commit();
	}
}
