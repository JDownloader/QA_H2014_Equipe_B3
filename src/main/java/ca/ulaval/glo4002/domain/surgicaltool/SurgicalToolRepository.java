package ca.ulaval.glo4002.domain.surgicaltool;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public interface SurgicalToolRepository {
	void persist(SurgicalTool surgicalTool) throws EntityExistsException;

	void update(SurgicalTool surgicalTool);

	SurgicalTool getById(int id) throws EntityNotFoundException;

	SurgicalTool getBySerialNumber(String serialNumber) throws EntityNotFoundException;

}
