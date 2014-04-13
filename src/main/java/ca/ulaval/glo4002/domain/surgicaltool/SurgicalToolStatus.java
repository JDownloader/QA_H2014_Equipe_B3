package ca.ulaval.glo4002.domain.surgicaltool;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.*;

public enum SurgicalToolStatus implements Serializable {
	PATIENT_USED("Utilise_Patient"), CONTAMINATED("Souille"), UNUSED("Inutilise");
	
	private String value;

	private SurgicalToolStatus(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	@JsonCreator
	public static SurgicalToolStatus fromString(String statusName) {
		for (SurgicalToolStatus surgicalToolStatus : SurgicalToolStatus.values()) {
			if (statusName.compareToIgnoreCase(surgicalToolStatus.getValue()) == 0) {
				return surgicalToolStatus;
			}
		}
		throw new IllegalArgumentException(String.format(
				"'%s' is not a valid value for enumeration SurgicalToolStatus.", statusName));
	}
}
