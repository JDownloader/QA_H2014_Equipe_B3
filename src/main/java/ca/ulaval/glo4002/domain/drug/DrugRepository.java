package ca.ulaval.glo4002.domain.drug;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public interface DrugRepository {

	void create(Drug drug) throws EntityExistsException;

	Drug getByDin(Din din) throws EntityNotFoundException, NoSuchFieldException;

	List<Drug> search(String name) throws Exception;
}
