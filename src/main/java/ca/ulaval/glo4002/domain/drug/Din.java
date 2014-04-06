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
		return din.equals(obj);
	};
}
