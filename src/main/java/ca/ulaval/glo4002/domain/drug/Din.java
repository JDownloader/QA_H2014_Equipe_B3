package ca.ulaval.glo4002.domain.drug;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonValue;

@Embeddable
public final class Din implements Serializable {
	private static final long serialVersionUID = 7369022975618045987L;
	@JsonProperty
	private String din;

	protected Din() {
		//Required for Hibernate
	}
	
	public Din(String din) {
		this.din = din;
	}

	@JsonValue
	public String getDin() {
		return din;
	}

	@Override
	public String toString() {
		return String.valueOf(din);
	}

	@Override
	public int hashCode() {
		return din.hashCode();
	};

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Din) {
			return din.equals(((Din) obj).din);
		}
		return false;
	};
}
