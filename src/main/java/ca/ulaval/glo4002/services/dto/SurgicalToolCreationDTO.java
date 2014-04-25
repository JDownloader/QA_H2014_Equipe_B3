package ca.ulaval.glo4002.services.dto;

import org.codehaus.jackson.annotate.*;

public class SurgicalToolCreationDTO {
	@JsonProperty("typecode")
	public String typeCode;
	@JsonProperty("statut")
	public String status;
	@JsonProperty("noserie")
	public String serialNumber;
	@JsonIgnore
	public Integer interventionNumber;
}
