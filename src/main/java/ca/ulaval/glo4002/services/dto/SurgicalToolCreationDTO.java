package ca.ulaval.glo4002.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SurgicalToolCreationDTO { 

	private String typecode;
	@JsonProperty("statut")
	private String status;
	@JsonProperty("noserie")
	private String noSerie;

	@JsonIgnore
	private Integer interventionNumber;
	
	public String getTypeCode() {
		return typecode;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getNoSerie() {
		return noSerie;
	}
	
	public void setInterventionNumber(Integer interventionNumber) {
		this.interventionNumber = interventionNumber;
	}
	
	//TODO: faut-il ajouter un champ pour le type d'intervention et ainsi pouvoir vérifier ça dans le validator?
}
