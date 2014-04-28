package ca.ulaval.glo4002.rest.utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.services.dto.BadRequestDTO;

public class ResponseAssembler {
	
	public static Response assembleErrorResponse(Status status, String internalCode, String message) {
		return Response.status(status).entity(new BadRequestDTO(internalCode, message)).build();
	}
	
}
