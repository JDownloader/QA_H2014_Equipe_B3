package ca.ulaval.glo4002.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DrugSearchDTO {
	@JsonProperty("nom")
	public String name;
}
