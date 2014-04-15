package ca.ulaval.glo4002.services.dto;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;

public class BadResponseDTO {
	public String code;
	public String message;
	
	public BadResponseDTO(ServiceRequestException e){
		this.code = e.getInternalCode();
		this.message = e.getMessage();
	}
}
