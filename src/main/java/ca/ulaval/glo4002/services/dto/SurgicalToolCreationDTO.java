package ca.ulaval.glo4002.services.dto;

import ca.ulaval.glo4002.domain.intervention.InterventionType;

import com.fasterxml.jackson.annotation.*;

public class SurgicalToolCreationDTO { 
	
	@JsonProperty("typecode")
	private String typecode;
	@JsonProperty("statut")
	private String status;
	@JsonProperty("noserie")
	private String serialNumber; //TODO rendre facultatif le noSerie dans la requête json


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
	
	public String getSerialNumber() {
		return serialNumber;
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
