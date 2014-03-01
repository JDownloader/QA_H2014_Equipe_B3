package ca.ulaval.glo4002.domain.drug;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public interface DrugRepository {
	
	public void create(Drug drug) throws EntityExistsException;
	
	public Drug getByDin(Din din) throws EntityNotFoundException;
	
	
}
