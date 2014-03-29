package ca.ulaval.glo4002.rest.dto;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PrescriptionCreationDto {
	private Integer din;
	private String drugName;
	private Integer staffMember;
	private Integer renewals;
	private Date date;
	private Integer patientNumber;
	
	public int getDin() {
		return din;
	}
	
	public boolean hasDin() {
		return din != null;
	}
	
	@JsonProperty("din")
	public void setDin(Integer din) {
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
	
	@JsonProperty("intervenant")
	public void setStaffMember(Integer staffMember) {
		this.staffMember = staffMember;
	}

	public int getRenewals() {
		return renewals;
	}
	
	@JsonProperty("renouvellements")
	public void setRenewals(Integer renewals) {
		this.renewals = renewals;
	}
	
	public Date getDate() {
		return date;
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
}
