package ca.ulaval.glo4002.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DrugSearchDto {
	@JsonProperty("nom")
	private String name;
	
	public String getName() {
		return name;
	}
}
