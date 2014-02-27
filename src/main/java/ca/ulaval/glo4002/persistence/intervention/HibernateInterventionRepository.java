package ca.ulaval.glo4002.intervention;

import ca.ulaval.glo4002.dao.DataAccessObject;
import ca.ulaval.glo4002.dao.DataAccessTransaction;

public class InterventionDAO extends DataAccessObject implements IInterventionDAO {
	DataAccessTransaction transaction = null;
	
	
	public InterventionDAO(DataAccessTransaction transaction) {
		this.transaction = transaction;
	}
	
	public void create(Intervention entity) {
		transaction.getEntityManager().persist(entity);
	}

	public void update(Intervention entity) {
		throw new UnsupportedOperationException();
	}

	public void delete(Intervention entity) {
		throw new UnsupportedOperationException();
	}
	
	public Intervention get(Integer id) {
		throw new UnsupportedOperationException();
	}
}
