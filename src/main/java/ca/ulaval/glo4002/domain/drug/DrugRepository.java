package ca.ulaval.glo4002.domain.drug;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public interface DrugRepository {
	
	public void create(Drug drug) throws EntityExistsException;
	
	public Drug getByDin(Din din) throws EntityNotFoundException, DrugDoesNotContainDinException;
	
	public List<Drug> findByName(String name) throws Exception;
}
