package ca.ulaval.glo4002.rest.dto;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DrugSearchDto {
	@JsonProperty("nom")
	private String name;
	
	public String getName() {
		return name;
	}
	
	public boolean hasName() {
		return !StringUtils.isBlank(name);
	}
	
	public void setName(String din) {
		this.name = din;
	}
}
