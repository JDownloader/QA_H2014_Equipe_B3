package ca.ulaval.glo4002.services.dto;

import java.util.Date;

import ca.ulaval.glo4002.domain.intervention.InterventionStatus;
import ca.ulaval.glo4002.domain.intervention.InterventionType;
import ca.ulaval.glo4002.domain.staff.Surgeon;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InterventionCreationDTO {
	
	public String description;
	@JsonProperty("chirurgien")
	public Surgeon surgeon;
	public Date date;
	@JsonProperty("salle")
	public String room;
	public InterventionType type;
	@JsonProperty("statut")
	public InterventionStatus status = InterventionStatus.PLANNED;
	@JsonProperty("patient")
	public Integer patientNumber;
}


