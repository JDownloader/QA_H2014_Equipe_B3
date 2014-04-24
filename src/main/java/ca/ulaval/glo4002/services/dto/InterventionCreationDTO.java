package ca.ulaval.glo4002.services.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import ca.ulaval.glo4002.domain.intervention.InterventionStatus;
import ca.ulaval.glo4002.domain.staff.Surgeon;

public class InterventionCreationDTO {
	
	public String description;
	@JsonProperty("chirurgien")
	public Surgeon surgeon;
	public Date date;
	@JsonProperty("salle")
	public String room;
	public String type;
	@JsonProperty("statut")
	public String status = InterventionStatus.PLANNED.getValue();
	@JsonProperty("patient")
	public Integer patientNumber;
}


