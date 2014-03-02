package ca.ulaval.glo4002.domain.surgicaltool;

import javax.persistence.EntityExistsException;

public interface SurgicalToolRepository {
	public void create(SurgicalTool surgicalTool) throws EntityExistsException;
}
