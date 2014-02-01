package ca.ulaval.glo4002.server;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class prescriptionRequestTest {
	private PrescriptionRequest myPrescriptionRequest;

	// TODO create required items
	@Before
	public void init() {

	}

	// TODO Constructor tests
	public void verifyConstructor() {
		// try to mock the JSON
		// JSONObject sampleJson = mock(JSONObject.class);
		JSONObject sampleJson = new JSONObject();
		// Not working for the moment
		// myPrescriptionRequest = PrescriptionRequest(sampleJson);
		assertNotNull(myPrescriptionRequest);
	}

	// TODO isValid() tests
	@Test
	public void isValidWithDin() {
		assertTrue(false);
	}

	// TODO get tests
	@Test
	public void getDin() {
		assertTrue(false);
	}

	@Test
	public void getName() {
		assertTrue(false);
	}

	@Test
	public void getStaffMember() {
		assertTrue(false);
	}

	@Test
	public void getRenewals() {
		assertTrue(false);
	}

	@Test
	public void getDate() {
		assertTrue(false);
	}
}
