package ca.ulaval.glo4002.requests;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

//@RunWith(MockitoJUnitRunner.class)
public class PrescriptionRequestTest {
	
	static final int SAMPLE_DIN = 0;
	static final String SAMPLE_NAME = "SAMPLE";
	static final int SAMPLE_RENEWAL = 5;
	static final int SAMPLE_STAFF_MEMBER = 5;

	@Mock
	private JSONObject myJsonObject;
	
//	@Test
//	public void validateDinAndNameWhenNoName() {
//		myJsonObject.append("din", SAMPLE_DIN);
//		PrescriptionRequest myRequest = new PrescriptionRequest(myJsonObject);
//		assertTrue(myRequest.validateDinAndName());
//	}
//	
//	@Test
//	public void validateDinAndNameWhenNoDin() {
//		myJsonObject.append("nom", SAMPLE_NAME);
//		PrescriptionRequest myRequest = new PrescriptionRequest(myJsonObject);
//		assertTrue(myRequest.validateDinAndName());
//	}
//	
//	@Test
//	public void validateDinAndNameWhenNone() {
//		PrescriptionRequest myRequest = new PrescriptionRequest(myJsonObject);
//		assertFalse(myRequest.validateDinAndName());
//	}
//
//	@Test
//	public void validateDinAndNameWhenBoth() {
//		myJsonObject.append("din", SAMPLE_DIN);
//		myJsonObject.append("nom", SAMPLE_NAME);
//		PrescriptionRequest myRequest = new PrescriptionRequest(myJsonObject);
//		assertFalse(myRequest.validateDinAndName());
//	}
//	
//	@Test
//	public void isValidWhenValid() {
//		assertTrue(false);
//	}
//	
//	@Test
//	public void isValidWhenMissingDinAndName() {
//		assertTrue(false);
//	}
//	
//	@Test
//	public void isValidWhenNoStaffMember() {
//		assertTrue(false);
//	}
//	
//	@Test
//	public void isValidWhenNoRenewal() {
//		assertTrue(false);
//	}
//	
//	@Test
//	public void isValidWhenInvalidDate() {
//		assertTrue(false);
//	}
//
//	@Test
//	public void getDin() {
//		myJsonObject.append("din", SAMPLE_DIN);
//		PrescriptionRequest myRequest = new PrescriptionRequest(myJsonObject);
//		assertEquals(SAMPLE_DIN, myRequest.getDin());
//	}
//
//	@Test
//	public void getName() {
//		myJsonObject.append("nom", SAMPLE_NAME);
//		PrescriptionRequest myRequest = new PrescriptionRequest(myJsonObject);
//		assertEquals(SAMPLE_NAME, myRequest.getName());
//	}
//
//	@Test
//	public void getStaffMember() {
//		myJsonObject.append("internevant", SAMPLE_STAFF_MEMBER);
//		PrescriptionRequest myRequest = new PrescriptionRequest(myJsonObject);
//		assertEquals(SAMPLE_RENEWAL, myRequest.getStaffMember());
//	}
//
//	@Test
//	public void getRenewals() {
//		myJsonObject.append("renouvellements", SAMPLE_RENEWAL);
//		PrescriptionRequest myRequest = new PrescriptionRequest(myJsonObject);
//		assertEquals(SAMPLE_RENEWAL, myRequest.getRenewals());
//	}
//
//	@Test
//	public void getDate() {
//		assertTrue(false);
//	}
}
