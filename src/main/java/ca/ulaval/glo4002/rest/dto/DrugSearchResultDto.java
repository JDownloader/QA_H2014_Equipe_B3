package ca.ulaval.glo4002.rest.dto;

import java.util.List;

import ca.ulaval.glo4002.domain.drug.Drug;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DrugSearchResultDto {
	@JsonProperty("")
	List<Drug> drugEntries;
	
	public List<Drug> drugEntries() {
		return drugEntries;
	}
	
	public void setDrugSearchResultEntries(List<Drug> drugEntries) {
		this.drugEntries = drugEntries;
	}
}
