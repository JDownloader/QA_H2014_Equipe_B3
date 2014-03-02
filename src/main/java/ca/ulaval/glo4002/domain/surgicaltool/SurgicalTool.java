package ca.ulaval.glo4002.domain.surgicaltool;

import javax.persistence.*;

@Entity(name="SurgicalTool")
public class SurgicalTool {
	
	@Id
	@Column(name = "SERIAL_NUMBER", nullable=true, unique=true)
	private String serialNumber;
	
	@Column(name = "CODE_TYPE", nullable=false)
	private String typeCode;
	
	@Column(name = "STATUS", nullable=false)
	private SurgicalToolStatus status;
	
	protected SurgicalTool() {
		//Required for Hibernate
	}
	
	public SurgicalTool(SurgicalToolBuilder builder) {
		this.serialNumber = builder.serialNumber;
		this.typeCode = builder.typeCode;
		this.status = builder.status;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public String getTypeCode() {
		return this.typeCode;
	}
	
	public SurgicalToolStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(SurgicalToolStatus status) {
		this.status = status;
	}
}
