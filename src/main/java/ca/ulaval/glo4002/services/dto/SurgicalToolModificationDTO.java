package ca.ulaval.glo4002.services.dto;

import org.codehaus.jackson.annotate.*;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;

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
