package ca.ulaval.glo4002.domain.surgicaltool;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("unused") //Suppresses warning for private attributes used for Hibernate persistence

@Entity(name = "SURGICAL_TOOL")
@Table(name = "SURGICAL_TOOL", uniqueConstraints = { @UniqueConstraint(columnNames = { "serialNumber" }) })
public class SurgicalTool {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String serialNumber;
	private String typeCode;
	private SurgicalToolStatus status;

	protected SurgicalTool() {
		// Required for Hibernate
	}

	public SurgicalTool(String serialNumber, String typeCode, SurgicalToolStatus status) {
		this.serialNumber = serialNumber;
		this.typeCode = typeCode;
		this.status = status;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public boolean isAnonymous() {
		return StringUtils.isBlank(serialNumber);
	}
	
	public void setStatus(SurgicalToolStatus status) {
		this.status = status;
	}
}
