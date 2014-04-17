package ca.ulaval.glo4002.services.dto;

import org.codehaus.jackson.annotate.*;

public class DrugSearchDTO {
	@JsonProperty("nom")
	public String name;
}
