package ca.ulaval.glo4002.rest;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

public class InterventionServletTest {

	static final Response badRequest = Response.status(Status.BAD_REQUEST).build();
	static final Response goodRequest = Response.status(Status.CREATED).build();
	static final String goodJson = "{ \"description\": \"Cataracte à l'oeil gauche\", "
			+ "\"chirurgien\": \"101224\", "
			+ "\"date\": \"0000-00-00T24:01:00\", "
			+ "\"salle\": \"blocB\", "
			+ "\"type\": \"OEIL\", "
			+ "\"status\": \"EN_COURS\", "
			+ "\"patient\": \"1\" }";
	static final String badJson = "{ \"description\": \"Cataracte à l'oeil gauche\", "
			+ "\"chirurgien\": \"101224\", "
			+ "\"date\": \"0000-00-00T24:01:00\", "
			+ "\"salle\": \"blocB\", "
			+ "\"type\": \"MAUVAIS_TYPE\", "
			+ "\"status\": \"EN_COURS\", "
			+ "\"patient\": \"1\" }";
	
	static final InterventionServlet myInterventionServlet = new InterventionServlet();
	
	@Test
	public void sendInAGoodRequest(){
		assertEquals(goodRequest.getStatus(), myInterventionServlet.post(goodJson).getStatus());
	}
	
	@Test
	public void sendInABadRequest(){
		assertEquals(badRequest.getStatus(), myInterventionServlet.post(badJson).getStatus());
	}
}
