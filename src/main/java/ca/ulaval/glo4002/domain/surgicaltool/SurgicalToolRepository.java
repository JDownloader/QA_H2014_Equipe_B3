package ca.ulaval.glo4002.domain.surgicaltool;

public interface SurgicalToolRepository {
	public void persist(SurgicalTool surgicalTool) throws SurgicalToolExistsException;

	public void update(SurgicalTool surgicalTool);

	public SurgicalTool getById(Integer id);

	public SurgicalTool getBySerialNumber(String serialNumber);

	public SurgicalTool getBySerialNumberOrId(String serialNumberOrId);
}
