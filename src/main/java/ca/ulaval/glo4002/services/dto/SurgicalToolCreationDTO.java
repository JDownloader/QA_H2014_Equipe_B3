package ca.ulaval.glo4002.services.dto;

import ca.ulaval.glo4002.domain.intervention.InterventionType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SurgicalToolCreationDTO { 

	private String typecode;
	@JsonProperty("statut")
	private String status;
	@JsonProperty("noserie")
	private String noSerie;

	@JsonIgnore
	private Integer interventionNumber;

	@JsonIgnore
	private InterventionType interventionType;
	
	public String getTypeCode() {
		return typecode;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getNoSerie() { //TODO: Pourquoi get/set plutôt que public?
		return noSerie;
	}
	
	public Integer getInterventionNumber() {
		return interventionNumber;
	}

	public void setInterventionNumber(Integer interventionNumber) {
		this.interventionNumber = interventionNumber;
	}
	
	public InterventionType getInterventionType() {
		return this.interventionType;
	}

	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}


}
