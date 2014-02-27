package ca.ulaval.glo4002.rest;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

public class PrescriptionResourceTest {
	
	//TODO : Complete the test for PrescriptionServlet. Mock the Repositories for it to work.
	
	static final Response badRequest = Response.status(Status.BAD_REQUEST).build();
	static final Response goodRequest = Response.status(Status.CREATED).build();
	
	static final String goodJson = "{ \"intervenant\": \"000000\", "
								+ "\"date\": \"2001-07-04T12:08:56\", "
								+ "\"renouvellements\": \"0\", "
								+ "\"din\": \"02240541\"}";
	
	static final String badJson = "{ \"intervenant\": \"000000\", "
								+ "\"date\": \"2001-07-04T12:08:56\", "
								+ "\"renouvellements\": \"0\", "
								+ "\"din\": \"02240541\", "
								+ "\"nom\": \"ADVIL NIGHTTIME LIQUI\" }";

	static final PrescriptionResource myPrescriptionServlet = new PrescriptionResource();
	
	@Test
	public void sendInAGoodRequest() {
		assertEquals(goodRequest.getStatus(), myPrescriptionServlet.post(goodJson).getStatus());
	}
	
	@Test
	public void sendInABadRequest() {
		assertEquals(badRequest.getStatus(), myPrescriptionServlet.post(badJson).getStatus());
	}
	
}
