package ca.ulaval.glo4002.patient;

import ca.ulaval.glo4002.dao.DataAccessObject;
import ca.ulaval.glo4002.dao.DataAccessTransaction;
import ca.ulaval.glo4002.exceptions.ItemNotFoundException;

public class PatientDAO extends DataAccessObject implements IPatientDAO {
	DataAccessTransaction transaction = null;
	
	public PatientDAO(DataAccessTransaction transaction) {
		this.transaction= transaction;
	}
	
	public void create(Patient entity) {
		transaction.getEntityManager().persist(entity);
	}

	public void update(Patient entity) {
		transaction.getEntityManager().merge(entity);
	}

	public void delete(Patient entity) {
		throw new UnsupportedOperationException();
	}
	
	public Patient get(Integer id) throws ItemNotFoundException {
		Patient patient = transaction.getEntityManager().find(Patient.class, id);
		if (patient == null) {
			throw new ItemNotFoundException(String.format("Cannot find Patient with id '%s'.", id));
		}
		return patient;
	}
}
