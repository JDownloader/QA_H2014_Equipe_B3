package ca.ulaval.glo4002.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import ca.ulaval.glo4002.dao.DataAccessTransaction;
import ca.ulaval.glo4002.exceptions.BadRequestException;

public abstract class Rest<Request> {
	
	protected Response buildBadRequestResponse(String code, String message) {
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.append("code", code).append("message", message);
		return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	
	protected Response executeTransaction(Request request) {
		DataAccessTransaction transaction = new DataAccessTransaction();
		Response response = null;

		try {
			transaction.begin();
			executeDaoTransactions(transaction, request);
			transaction.commit();
			response = Response.status(Status.CREATED).build();
		} catch (BadRequestException e) {
			response = buildBadRequestResponse(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}

		return response;
	}
	
	abstract protected void executeDaoTransactions(DataAccessTransaction transaction, Request request) throws BadRequestException;
}
