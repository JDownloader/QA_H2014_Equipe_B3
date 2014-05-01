package ca.ulaval.glo4002.domain.surgicaltool;

import java.util.Observable;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("unused") //Suppresses warning for private attributes used for Hibernate persistence

@Entity(name = "SURGICAL_TOOL")
@Table(name = "SURGICAL_TOOL", uniqueConstraints = { @UniqueConstraint(name = "UQ_SERIALNUMBER", columnNames = { "serialNumber" }) })
public class SurgicalTool extends Observable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String serialNumber;
	private String typeCode;
	@Enumerated(EnumType.STRING)
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

	public boolean isAnonymous() {
		return StringUtils.isBlank(serialNumber);
	}
	
	public void setStatus(SurgicalToolStatus status) {
		this.status = status;
	}
	
	public boolean compareToSerialNumber(String serialNumber) {
		if (this.serialNumber != null && this.serialNumber.compareToIgnoreCase(serialNumber) == 0) {
			return true;
		}
		return false;
	}
	
	public boolean compareToId(String id) {
		return this.id.toString().compareToIgnoreCase(id) == 0;
	}
	
	public void changeSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
		setChanged();
		notifyObservers();
	}
}
