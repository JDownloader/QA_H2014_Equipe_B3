package ca.ulaval.glo4002.domain.intervention;

import java.io.Serializable;

public enum InterventionStatus implements Serializable {
	PLANNED("PLANIFIEE"), IN_PROGRESS("EN_COURS"), DONE("TERMINEE"), CANCELLED("ANNULEE"), POSTPONED("REPORTEE");
	private String value;

	private InterventionStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public static InterventionStatus fromString(String statusName) {
		for (InterventionStatus interventionStatus : InterventionStatus.values()) {
			if (statusName.compareTo(interventionStatus.getValue()) == 0) {
				return interventionStatus;
			}
		}
		
		throw new IllegalArgumentException(String.format("'%s' n'est pas un statut d'intervention valide.", statusName));
	}
}