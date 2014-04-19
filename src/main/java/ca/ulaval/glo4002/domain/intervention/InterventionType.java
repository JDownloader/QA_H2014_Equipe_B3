package ca.ulaval.glo4002.domain.intervention;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

public enum InterventionType implements Serializable {
	EYE("OEIL"), HEART("COEUR"), MARROW("MOELLE"), ONCOLOGIC("ONCOLOGIQUE"), OTHER("AUTRE");
	private String value;

	private InterventionType(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	@JsonCreator
	public static InterventionType fromString(String type) {
		for (InterventionType interventionType : InterventionType.values()) {
			if (type.compareTo(interventionType.getValue()) == 0) {
				return interventionType;
			}
		}
		throw new IllegalArgumentException(String.format("'%s' n'est pas un type d'intervention valide.", type));
	}
}
