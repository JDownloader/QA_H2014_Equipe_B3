package ca.ulaval.glo4002.services.dto;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.*;

@SuppressWarnings("unused")
public class PrescriptionCreationDTO {
	private String din;
	@JsonProperty("nom")
	private String drugName;
	@JsonProperty("intervenant")
	private Integer staffMember; //TODO: Change to string; Impacts StaffMember -> Surgeon -> Intervention;
	@JsonProperty("renouvellements")
	private Integer renewals;
	private Date date;
	@JsonIgnore
	private Integer patientNumber;
	
	public String getDin() {
		return din;
	}
	
	public String getDrugName() {
		return drugName;
	}
	
	public Integer getStaffMember() {
		return staffMember;
	}

	public Integer getRenewals() {
		return renewals;
	}
	
	public Date getDate() {
		return date;
	}

	public Integer getPatientNumber() {
		return patientNumber;
	}

	public void setPatientNumber(Integer patientNumber) {
		//Required to set @PathParam value
		this.patientNumber = patientNumber;
	}
}
