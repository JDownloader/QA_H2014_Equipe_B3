package ca.ulaval.glo4002.domain.patient;

import javax.persistence.EntityExistsException;

public interface PatientRepository {

	//TODO : create a more explicit exception (say, PatientExists).
	//Make it a runtime exception since it is the convention in the project. Eliminate the "throws" notation.
	public void create(Patient patient) throws EntityExistsException;

	public void update(Patient patient);

	public Patient getById(int id);
}
