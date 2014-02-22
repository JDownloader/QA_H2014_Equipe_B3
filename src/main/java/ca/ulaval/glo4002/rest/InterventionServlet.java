package ca.ulaval.glo4002.rest;

import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.dao.DataAccessTransaction;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.intervention.Intervention;
import ca.ulaval.glo4002.requests.InterventionRequest;

@Path("interventions/")
public class InterventionServlet extends Rest<InterventionRequest>{

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	//TODO Service Interface to separate DAO methods from rest objects
	public Response post(final String input){
		try {
			JSONObject jsonRequest = new JSONObject(input);
			InterventionRequest interventionRequest = new InterventionRequest(jsonRequest);
			interventionRequest.validateStatus();
			interventionRequest.validateType();
		} catch (JSONException | ParseException | IllegalArgumentException e) {
			JSONObject interventionError = new JSONObject();
			interventionError.append("code", "INT001").append("message", "La requête contient des informations invalides et/ou est malformée");
			return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(interventionError.toString()).build();
		}
		return Response.status(Status.CREATED).build();
	}
	
	@Override
	protected void executeDaoTransactions(DataAccessTransaction transaction,
			InterventionRequest request) throws BadRequestException {
		//TODO complete DAO method
		
	}

}
