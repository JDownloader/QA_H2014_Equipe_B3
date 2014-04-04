package ca.ulaval.glo4002.domain.intervention;

import java.io.Serializable;

public enum InterventionType implements Serializable {
	EYE("OEIL"), HEART("COEUR"), MARROW("MOELLE"), ONCOLOGIC("ONCOLOGIQUE"), OTHER("AUTRE");
	private String value;

	private InterventionType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public String getValue() {
		return value;
	}

	public static InterventionType fromString(String type) {
		for (InterventionType interventionType : InterventionType.values()) {
			if (type.compareTo(interventionType.getValue()) == 0) {
				return interventionType;
			}
		}
		throw new IllegalArgumentException(String.format("'%s' is not a valid value for enumeration InterventionStatus.", type));
	}
}