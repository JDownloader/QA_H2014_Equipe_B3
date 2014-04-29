package ca.ulaval.glo4002.rest.utils;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.services.dto.BadRequestDTO;

public class ResponseAssembler {
	
	public static Response assembleOkResponse() {
		return Response.status(Status.OK).build();
	}
	
	public static Response assembleOkResponse(Object entity) {
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}
	
	public static Response assembleCreatedResponse(URI resourceLocationURI) {
		return Response.status(Status.CREATED).location(resourceLocationURI).build();
	}
	
	public static Response assembleErrorResponse(Status status, String internalCode, String message) {
		return Response.status(status).entity(new BadRequestDTO(internalCode, message)).build();
	}
	
}
