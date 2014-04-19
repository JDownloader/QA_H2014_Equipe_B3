package ca.ulaval.glo4002.services.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import ca.ulaval.glo4002.domain.intervention.InterventionStatus;
import ca.ulaval.glo4002.domain.intervention.InterventionType;
import ca.ulaval.glo4002.domain.staff.Surgeon;

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


