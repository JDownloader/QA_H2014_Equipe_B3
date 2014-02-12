package ca.ulaval.glo4002.rest;

import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;
import org.mockito.Mock;

public class AddPrescriptionTest {
	
	//TODO : Complete the test for AddPrescription
	
	static final Response badRequest = Response.status(Status.BAD_REQUEST).build();
	static final Response goodRequest = Response.status(Status.BAD_REQUEST).build();
	
	@Mock
	AddPrescription myAddPrescription;
	
//	@Test
//	public void sendInAGoodRequest() {
//		assertTrue(false);
//	}
//	
//	@Test
//	public void sendInABadRequest() {
//		assertTrue(false);
//	}
	
}
