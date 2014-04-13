package ca.ulaval.glo4002.services.dto;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;

import com.fasterxml.jackson.annotation.*;

public class SurgicalToolCreationDTO {
	@JsonProperty("typecode")
	private String typeCode;
	@JsonProperty("statut")
	private SurgicalToolStatus status;
	@JsonProperty("noserie")
	private String serialNumber;
	@JsonIgnore
	private Integer interventionNumber;

	public String getTypeCode() {
		return typeCode;
	}

	public SurgicalToolStatus getStatus() {
		return status;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public Integer getInterventionNumber() {
		return interventionNumber;
	}

	public void setInterventionNumber(Integer interventionNumber) {
		this.interventionNumber = interventionNumber;
	}
}
