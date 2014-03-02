package ca.ulaval.glo4002.domain.surgicaltool;

public class SurgicalToolBuilder {
	
	protected String typeCode;
	protected String serialNumber;
	protected SurgicalToolStatus status;
	
	public SurgicalToolBuilder typecode(String typeCode) {
		this.typeCode = typeCode;
		return this;
	}
	
	public SurgicalToolBuilder serialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
		return this;
	}
	
	public SurgicalToolBuilder status(SurgicalToolStatus status) {
		this.status = status;
		return this;
	}
	
	public SurgicalTool build() {
		SurgicalTool surgicalTool = new SurgicalTool(this);

		if(typeCode.isEmpty()
			|| status == null) {
			throw new IllegalStateException();
		}
		
		return surgicalTool;
	}
}
