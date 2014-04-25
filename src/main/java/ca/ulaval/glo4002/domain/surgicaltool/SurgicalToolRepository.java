package ca.ulaval.glo4002.domain.surgicaltool;

public interface SurgicalToolRepository {
	void persist(SurgicalTool surgicalTool) throws SurgicalToolExistsException;

	SurgicalTool getById(Integer id);

	SurgicalTool getBySerialNumber(String serialNumber);

	SurgicalTool getBySerialNumberOrId(String serialNumberOrId);
}
