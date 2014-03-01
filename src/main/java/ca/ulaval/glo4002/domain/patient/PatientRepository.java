package ca.ulaval.glo4002.domain.patient;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public interface PatientRepository {
	
	public void create(Patient patient) throws EntityExistsException;
	
	public void update(Patient patient);
	
	public Patient getById(int id) throws EntityNotFoundException;
}
