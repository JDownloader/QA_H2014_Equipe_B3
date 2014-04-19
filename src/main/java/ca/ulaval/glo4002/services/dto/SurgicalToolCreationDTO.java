package ca.ulaval.glo4002.services.dto;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;

import com.fasterxml.jackson.annotation.*;

public class SurgicalToolCreationDTO {
	@JsonProperty("typecode")
	public String typeCode;
	@JsonProperty("statut")
	public SurgicalToolStatus status;
	@JsonProperty("noserie")
	public String serialNumber;
	@JsonIgnore
	public Integer interventionNumber;
}
