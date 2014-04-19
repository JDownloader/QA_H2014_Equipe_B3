package ca.ulaval.glo4002.domain.intervention;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

public enum InterventionStatus implements Serializable {
	PLANNED("PLANIFIEE"), IN_PROGRESS("EN_COURS"), COMPLETED("TERMINEE"), CANCELLED("ANNULEE"), POSTPONED("REPORTEE");
	private String value;

	private InterventionStatus(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	@JsonCreator
	public static InterventionStatus fromString(String statusName) {
		for (InterventionStatus interventionStatus : InterventionStatus.values()) {
			if (statusName.compareTo(interventionStatus.getValue()) == 0) {
				return interventionStatus;
			}
		}

		throw new IllegalArgumentException(String.format("'%s' n'est pas un statut d'intervention valide.", statusName));
	}
}
