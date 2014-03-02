package ca.ulaval.glo4002.domain.surgicaltool;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public interface SurgicalToolRepository {
	public void create(SurgicalTool surgicalTool) throws EntityExistsException;
	
	public void update(SurgicalTool surgicalTool);
	
	public SurgicalTool getBySerialNumber(String serialNumber) throws EntityNotFoundException;
}
