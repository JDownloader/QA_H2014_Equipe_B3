package ca.ulaval.glo4002.prescription;

import ca.ulaval.glo4002.dao.DataAccessObject;
import ca.ulaval.glo4002.dao.DataAccessTransaction;

public class PrescriptionDAO extends DataAccessObject implements IPrescriptionDAO {
	DataAccessTransaction transaction = null;
	
	
	public PrescriptionDAO(DataAccessTransaction transaction) {
		this.transaction = transaction;
	}
	
	public void create(Prescription entity) {
		transaction.getEntityManager().persist(entity);
	}

	public void update(Prescription entity) {
		throw new UnsupportedOperationException();
	}

	public void delete(Prescription entity) {
		throw new UnsupportedOperationException();
	}
	
	public Prescription get(Integer id) {
		throw new UnsupportedOperationException();
	}
}
