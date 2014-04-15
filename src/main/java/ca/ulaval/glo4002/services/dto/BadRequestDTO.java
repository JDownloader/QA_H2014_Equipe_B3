package ca.ulaval.glo4002.services.dto;

public class BadRequestDTO {
	public String code;
	public String message;

	public BadRequestDTO(String code, String message){
		this.code = code;
		this.message = message;
	}
}
