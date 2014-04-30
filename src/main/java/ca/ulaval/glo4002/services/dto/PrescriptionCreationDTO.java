package ca.ulaval.glo4002.services.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import ca.ulaval.glo4002.domain.drug.Din;

public class PrescriptionCreationDTO {
	public Din din;
	@JsonProperty("nom")
	public String drugName;
	@JsonProperty("intervenant")
	public String staffMember;
	@JsonProperty("renouvellements")
	public Integer renewals;
	public Date date;
	@JsonIgnore
	public Integer patientNumber;
}
