package ca.ulaval.glo4002.services.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class InterventionCreationDTO {
	@JsonProperty("description")
	private String description;
	@JsonProperty("chirurgien")
	private Integer surgeonNumber;
	@JsonProperty("date")
	private Date date;
	@JsonProperty("salle")
	private String room;
	@JsonProperty("type")
	private String type;
	@JsonProperty("statut")
	private String status;
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


