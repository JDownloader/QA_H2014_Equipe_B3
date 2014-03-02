package ca.ulaval.glo4002.domain.intervention;

import java.io.Serializable;

public enum InterventionStatus implements Serializable {
	PLANIFIEE("Planifiee"), EN_COURS("En_cours"), TERMINEE("Terminee"), ANNULEE("Annulee"), REPORTEE("Reportee"), UNDEFINED("");
	private String value;
	
	private InterventionStatus(String value) {
        this.value = value;
    }
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	public String getValue() {
		return value;
	}
	
	public static InterventionStatus fromString(String statusName) {
		for (InterventionStatus interventionStatus : InterventionStatus.values()) {
			if (statusName.compareToIgnoreCase(interventionStatus.getValue()) == 0) {
				return interventionStatus;
			}
		}
		throw new IllegalArgumentException(String.format("'%s' is not a valid value for enumeration InterventionStatus.", statusName));
	}
}
