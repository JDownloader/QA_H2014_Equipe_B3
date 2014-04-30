package ca.ulaval.glo4002.services.dto;
 
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

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
