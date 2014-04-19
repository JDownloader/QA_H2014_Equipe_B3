package ca.ulaval.glo4002.services.dto;

public class BadResponseDTO {
	public String code;
	public String message;

	public BadResponseDTO(String code, String message){
		this.code = code;
		this.message = message;
	}
}
