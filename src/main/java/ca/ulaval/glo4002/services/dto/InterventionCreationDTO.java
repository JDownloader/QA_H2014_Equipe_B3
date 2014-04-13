package ca.ulaval.glo4002.services.dto;

import java.util.Date;

import ca.ulaval.glo4002.domain.intervention.InterventionStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InterventionCreationDTO {
	
	private String description;
	@JsonProperty("chirurgien")
	private Integer surgeonNumber;
	private Date date;
	@JsonProperty("salle")
	private String room;
	private String type;
	//TODO : see if you can move this away from here
	@JsonProperty("statut")
	private String status = InterventionStatus.PLANNED.getValue();
	@JsonProperty("patient")
	private Integer patientNumber;
	
	public String getDescription(){
		return description;
	}
	
	public Integer getSurgeonNumber(){
		return surgeonNumber;
	}
	
	public Date getDate(){
		return date;
	}
	
	public String getRoom(){
		return room;
	}
	
	public String getType(){
		return type;
	}
	
	public String getStatus(){
		return status;
	}
	
	public Integer getPatientNumber(){
		return patientNumber;
	}
}


