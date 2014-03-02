package ca.ulaval.glo4002.domain.surgicaltool;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="SurgicalTool")
public class SurgicalTool {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SURGICAL_TOOL_ID", nullable=false)
	private int id;
	
	@Column(name = "SERIAL_NUMBER", nullable=true)
	private String serialNumber;
	
	@Column(name = "TYPECODE", nullable=false)
	private String typecode;
	
	@Column(name = "STATUS", nullable=false)
	private String status;
	
	@Column(name = "INTERVENTION_ID", nullable=false)
	private int interventionId;
	
	protected SurgicalTool() {
		//Required for Hibernate
	}
	
	public SurgicalTool(SurgicalToolBuilder builder) {
		this.serialNumber = builder.serialNumber;
		this.typecode = builder.typecode;
		this.status = builder.status;
		this.interventionId = builder.interventionId;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getSerialNumber() {
		return this.serialNumber;
	}
	
	public String getTypecode() {
		return this.typecode;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public int getInterventionId() {
		return this.interventionId;
	}
}
