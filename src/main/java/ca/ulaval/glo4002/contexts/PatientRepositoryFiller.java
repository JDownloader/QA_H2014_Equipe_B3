package ca.ulaval.glo4002.contexts;

import javax.persistence.EntityManager;

import ca.ulaval.glo4002.domain.patient.*;

public class PatientRepositoryFiller {
	
	public void fill(EntityManager entityManager, PatientRepository patientRepository) {
		entityManager.getTransaction().begin();
		patientRepository.create(new Patient(0));
		patientRepository.create(new Patient(1));
		patientRepository.create(new Patient(2));
		entityManager.getTransaction().commit();
	}
}
