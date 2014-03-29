package ca.ulaval.glo4002.rest.dto;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import ca.ulaval.glo4002.rest.dto.validators.PrescriptionCreationDtoValidator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PrescriptionCreationDto {
	private String din;
	private String drugName;
	private Integer staffMember;
	private Integer renewals;
	private Date date;
	private Integer patientNumber;
	
	public String getDin() {
		return din;
	}
	
	public boolean hasDin() {
		return din != null;
	}
	
	@JsonProperty("din")
	public void setDin(String din) {
		this.din = din;
	}
	
	public String getDrugName() {
		return drugName;
	}
	
	public boolean hasDrugName() {
		return !StringUtils.isBlank(drugName);
	}
	
	@JsonProperty("nom")
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	
	public int getStaffMember() {
		return staffMember;
	}
	
	public boolean hasStaffMember() {
		return staffMember != null;
	}
	
	@JsonProperty("intervenant")
	public void setStaffMember(Integer staffMember) {
		this.staffMember = staffMember;
	}

	public int getRenewals() {
		return renewals;
	}
	
	public boolean hasRenewals() {
		return renewals != null;
	}
	
	@JsonProperty("renouvellements")
	public void setRenewals(Integer renewals) {
		this.renewals = renewals;
	}
	
	public Date getDate() {
		return date;
	}
	
	public boolean hasDate() {
		return date != null;
	}
	
	@JsonProperty("date")
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getPatientNumber() {
		return patientNumber;
	}
	
	@JsonIgnore
	public void setPatientNumber(Integer patientNumber) {
		this.patientNumber = patientNumber;
	}
	
	@JsonIgnore
	public void validate() {
		PrescriptionCreationDtoValidator.validate(this);
	}
}
