package ca.ulaval.glo4002.services.dto;

import java.util.Date;

import ca.ulaval.glo4002.domain.drug.Din;

import com.fasterxml.jackson.annotation.*;

public class PrescriptionCreationDTO {
	public Din din;
	@JsonProperty("nom")
	public String drugName;
	@JsonProperty("intervenant")
	public Integer staffMember; //TODO: Change to string; Impacts StaffMember -> Surgeon -> Intervention;
	@JsonProperty("renouvellements")
	public Integer renewals;
	public Date date;
	@JsonIgnore
	public Integer patientNumber;
}
