package ca.ulaval.glo4002.domain.surgicaltool;

public interface SurgicalToolRepository {
	void persist(SurgicalTool surgicalTool) throws SurgicalToolExistsException;

	void update(SurgicalTool surgicalTool);

	SurgicalTool getById(Integer id) throws SurgicalToolNotFoundException;

	SurgicalTool getBySerialNumber(String serialNumber) throws SurgicalToolNotFoundException;

	SurgicalTool getBySerialNumberOrId(String serialNumberOrId) throws SurgicalToolNotFoundException;
}
