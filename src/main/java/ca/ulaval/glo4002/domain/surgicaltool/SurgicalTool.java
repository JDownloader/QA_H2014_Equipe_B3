package ca.ulaval.glo4002.domain.surgicaltool;

import javax.persistence.*;

@SuppressWarnings("unused") //Suppresses warning for private attributes used for Hibernate persistence

@Entity(name = "SURGICAL_TOOL")
@Table(name = "SURGICAL_TOOL", uniqueConstraints = { @UniqueConstraint(columnNames = { "serialNumber" }) })
public class SurgicalTool {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

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
	
	public int getId() {
		return id;
	}

	public boolean isAnonymous() {
		return serialNumber == null;
	}
}
