package ca.ulaval.glo4002.domain.drug;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class Din implements Serializable {
	private static final long serialVersionUID = 7369022975618045987L;
	@JsonProperty
	private String din;

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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		
		final Din otherDin = (Din) obj;

		if (this.din != otherDin.din) {
			return false;
		}

		return true;
	}
}
