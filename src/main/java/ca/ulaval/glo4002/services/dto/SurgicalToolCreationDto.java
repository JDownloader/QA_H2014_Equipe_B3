package ca.ulaval.glo4002.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SurgicalToolCreationDto { 

	private String typecode;
	private String statut;
	@JsonProperty("noserie")
	private String noSerie;

	@JsonIgnore
	private Integer interventionNumber;
	
	public String getTypeCode() {
		return typecode;
	}
	
	public String getStatut() {
		return statut;
	}
	
	public String getNoSerie() {
		return noSerie;
	}

	public void setInterventionNumber(Integer interventionNumber) { //Required to set @PathParam value
		this.interventionNumber = interventionNumber;
	}
}
