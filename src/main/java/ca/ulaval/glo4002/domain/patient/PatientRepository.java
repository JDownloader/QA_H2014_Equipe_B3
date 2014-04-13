package ca.ulaval.glo4002.domain.patient;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public interface PatientRepository {

	void create(Patient patient) throws EntityExistsException;

	void update(Patient patient);

	Patient getById(Integer id) throws EntityNotFoundException;
}
