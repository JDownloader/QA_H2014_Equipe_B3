package ca.ulaval.glo4002.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.dao.DataAccessTransaction;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.requests.InterventionRequest;

@Path("interventions/")
public class CreateIntervention extends Rest<InterventionRequest>{

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	//TODO Service Interface to separate DAO methods from rest objects
	
	@Override
	protected void executeDaoTransactions(DataAccessTransaction transaction,
			InterventionRequest request) throws BadRequestException {
		// TODO Auto-generated method stub
		
	}

}
