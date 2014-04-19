package ca.ulaval.glo4002.services.dto;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SurgicalToolModificationDTO {
	@JsonProperty("statut")
	public SurgicalToolStatus newStatus;
	@JsonProperty("noserie")
	public String newSerialNumber;
	@JsonIgnore
	public Integer interventionNumber;
	@JsonIgnore
	public String typeCode;
	@JsonIgnore
	public String serialNumberOrId;
}
