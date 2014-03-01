package ca.ulaval.glo4002.rest.utils;

import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

public class BadRequestJsonResponseBuilder {
	
	public static Response build(String code, String message) {
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.append("code", code).append("message", message);
		return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
}
