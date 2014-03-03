package ca.ulaval.glo4002.domain.intervention;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public interface InterventionRepository {
	void create(Intervention intervention) throws EntityExistsException;

	void update(Intervention intervention);

	Intervention getById(int id) throws EntityNotFoundException;
}
