package ca.ulaval.glo4002.domain.surgicaltool;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.*;

public enum SurgicalToolStatus implements Serializable {
	PATIENT_USED("UTILISE_PATIENT"), CONTAMINATED("SOUILLE"), UNUSED("INUTILISE");
	
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
			if (statusName.compareTo(surgicalToolStatus.getValue()) == 0) {
				return surgicalToolStatus;
			}
		}
		throw new IllegalArgumentException(String.format("'%s' n'est pas une valeur valide pour le statut d'un instrument.", statusName));
	}
}
