package ca.ulaval.glo4002.rest.dto;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.*;

public class PrescriptionCreationDto {
	private String din;
	@JsonProperty("nom")
	private String drugName;
	@JsonProperty("intervenant")
	private Integer staffMember;
	@JsonProperty("renouvellements")
	private Integer renewals;
	private Date date;
	@JsonIgnore
	private Integer patientNumber;
	
	public String getDin() {
		return din;
	}
	
	public boolean hasDin() {
		return din != null;
	}
	
	public void setDin(String din) {
		this.din = din;
	}
	
	public String getDrugName() {
		return drugName;
	}
	
	public boolean hasDrugName() {
		return !StringUtils.isBlank(drugName);
	}
	
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	
	public int getStaffMember() {
		return staffMember;
	}
	
	public boolean hasStaffMember() {
		return staffMember != null;
	}
	
	public void setStaffMember(Integer staffMember) {
		this.staffMember = staffMember;
	}

	public int getRenewals() {
		return renewals;
	}
	
	public boolean hasRenewals() {
		return renewals != null;
	}
	
	public void setRenewals(Integer renewals) {
		this.renewals = renewals;
	}
	
	public Date getDate() {
		return date;
	}
	
	public boolean hasDate() {
		return date != null;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getPatientNumber() {
		return patientNumber;
	}
	
	public void setPatientNumber(Integer patientNumber) {
		this.patientNumber = patientNumber;
	}
}
