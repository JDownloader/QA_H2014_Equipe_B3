package ca.ulaval.glo4002.rest.utils;

import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

public class BadRequestJsonResponseBuilder {
	
    static private final String CODE_PARAMETER = "code";
    static private final String MESSAGE_PARAMETER = "message";

    
	public static Response build(String code, String message) {
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.append(CODE_PARAMETER, code).append(MESSAGE_PARAMETER, message);
		return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
}
