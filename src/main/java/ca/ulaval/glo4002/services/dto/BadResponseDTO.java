package ca.ulaval.glo4002.services.dto;

import ca.ulaval.glo4002.exceptions.domainexceptions.DomainException;

public class BadResponseDTO {
	public String code;
	public String message;
	
	public BadResponseDTO(DomainException e){
		this.code = e.getCode();
		this.message = e.getMessage();
	}
}
