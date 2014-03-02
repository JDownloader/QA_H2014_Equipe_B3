package ca.ulaval.glo4002.domain.intervention;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public interface InterventionRepository {
	public void create(Intervention intervention) throws EntityExistsException;
	
	public void update(Intervention intervention);
	
	public Intervention getById(int id) throws EntityNotFoundException;
}
