package ca.ulaval.glo4002.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatedWithLocationResponseDTO {
	@JsonProperty("Location")
	public String location;
	
	public CreatedWithLocationResponseDTO(String location){
		this.location = location;
	}
}
