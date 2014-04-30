package ca.ulaval.glo4002.services.dto;
 
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class SurgicalToolModificationDTO {
	@JsonProperty("statut")
	public String newStatus;
	@JsonProperty("noserie")
	public String newSerialNumber;
	@JsonProperty("typecode")
	public String newTypeCode;
	@JsonIgnore
	public Integer interventionNumber;
	@JsonIgnore
	public String typeCode;
	@JsonIgnore
	public String serialNumberOrId;
}
