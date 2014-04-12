package ca.ulaval.glo4002.domain.surgicaltool;

import java.io.Serializable;

public enum SurgicalToolStatus implements Serializable {
	UTILISE_PATIENT("Utilise_Patient"), SOUILLE("Souille"), INUTILISE("Inutilise");
	private String value;

	private SurgicalToolStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

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
