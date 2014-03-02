package ca.ulaval.glo4002.domain.surgicalTool;

public class SurgicalToolBuilder {
	
	private int UNSPECIFIED_INTERVENTION = -5;
	
	protected String typecode;
	protected String serialNumber;
	protected String status;
	protected int interventionId;
	
	public SurgicalToolBuilder typecode(String typecode) {
		this.typecode = typecode;
		return this;
	}
	
	public SurgicalToolBuilder serialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
		return this;
	}
	
	public SurgicalToolBuilder status(String status) {
		this.status = status;
		return this;
	}
	
	public SurgicalToolBuilder interventionId(int interventionId) {
		this.interventionId = interventionId;
		return this;
	}
	
	public SurgicalTool build() {
		SurgicalTool surgicalTool = new SurgicalTool(this);
	
		//TODO review to make sure this is accurate
		if(typecode.isEmpty()
			|| status.isEmpty()
			|| interventionId == UNSPECIFIED_INTERVENTION) {
			throw new IllegalStateException();
		}
		
		return surgicalTool;
	}
}
