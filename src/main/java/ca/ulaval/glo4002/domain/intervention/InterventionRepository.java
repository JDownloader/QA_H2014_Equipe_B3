package ca.ulaval.glo4002.domain.intervention;

import javax.persistence.EntityExistsException;

public interface InterventionRepository {
	public void create(Intervention intervention) throws EntityExistsException;
}
