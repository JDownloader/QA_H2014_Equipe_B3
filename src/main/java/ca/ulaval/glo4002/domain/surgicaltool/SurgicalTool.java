package ca.ulaval.glo4002.domain.surgicaltool;

import javax.persistence.*;

@Entity(name = "SURGICAL_TOOL")
@Table(name = "SURGICAL_TOOL", uniqueConstraints = { @UniqueConstraint(columnNames = { "SERIAL_NUMBER" }) }, indexes = { @Index(name = "SERIAL_NUMBER_IDX", columnList = "SERIAL_NUMBER") })
public class SurgicalTool {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SURGICAL_TOOL_ID", nullable = false)
	private int id;

	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	@Column(name = "CODE_TYPE", nullable = false)
	private String typeCode;

	@Column(name = "STATUS", nullable = false)
	private SurgicalToolStatus status;

	protected SurgicalTool() {
		// Required for Hibernate
	}

	public SurgicalTool(SurgicalToolBuilder builder) {
		this.serialNumber = builder.serialNumber;
		this.typeCode = builder.typeCode;
		this.status = builder.status;
	}

	public int getId() {
		return this.id;
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
