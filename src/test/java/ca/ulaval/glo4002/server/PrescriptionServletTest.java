package ca.ulaval.glo4002.server;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

public class PrescriptionServletTest {

	private static final Integer VALID_DIN = 02240541;
	private static final Integer NULL_DIN = 0;
	private static final String VALID_NAME = "ADVIL NIGHTTIME LIQUI";
	private static final String NULL_NAME = "";
	private static final Integer STAFF_MEMBER = 000000;
	private static final int RENEWALS = 0;
	private static final String DATE = "2001-07-04T12:08:56";
	PrescriptionServlet testServlet = new PrescriptionServlet();

	@Test
	public void servletReceivesBadRequest() {
		String badRequest = "{'intervenant': 000000, 'date': '2001-07-04T12:08:56', 'renouvellements': 0, 'din': 02240541, 'nom': 'ADVIL NIGHTTIME LIQUI'}";
		testServlet.parseJsonObject(badRequest);
	}

	@Test(expected = NullPointerException.class)
	public void servletReceivesRequestWithDin() {
		String goodRequest = "{'intervenant': 000000, 'date': '2001-07-04T12:08:56', 'renouvellements': 0, 'din': 02240541}";
		testServlet.parseJsonObject(goodRequest);
		testServlet.createPrescriptionWithDin(VALID_DIN, NULL_NAME,
				STAFF_MEMBER, RENEWALS, DATE);
	}

	@Test(expected = NullPointerException.class)
	public void servletReceivesRequestWithName() {
		String goodRequest = "{'intervenant': 000000, 'date': '2001-07-04T12:08:56', 'renouvellements': 0, 'nom': 'ADVIL NIGHTTIME LIQUI'}";
		testServlet.parseJsonObject(goodRequest);
		testServlet.createPrescriptionWithName(NULL_DIN, VALID_NAME,
				STAFF_MEMBER, RENEWALS, DATE);
	}

	@Test
	public void validateDinAndNameWhenDinOnly() {
		JSONObject object = new JSONObject();
		object.put("din", VALID_DIN);

		assertTrue(testServlet.validateDinAndName(object));
	}

	@Test
	public void validateDinAndNameWhenNameOnly() {
		JSONObject object = new JSONObject();
		object.put("nom", VALID_NAME);

		assertTrue(testServlet.validateDinAndName(object));
	}

	@Test
	public void validateDinAndNameWhenBoth() {
		JSONObject object = new JSONObject();
		object.put("din", VALID_DIN);
		object.put("nom", VALID_NAME);

		assertFalse(testServlet.validateDinAndName(object));
	}

	@Test
	public void validateDinAndNameWhenNone() {
		JSONObject object = new JSONObject();
		object.put("din", NULL_DIN);
		object.put("nom", NULL_NAME);

		assertFalse(testServlet.validateDinAndName(object));
	}

	@Test
	public void fetchDinWhenExists() {
		JSONObject object = new JSONObject();
		object.put("din", VALID_DIN);
		assertEquals(VALID_DIN, testServlet.fetchDinInJson(object));
	}

	@Test
	public void fecthDinWhenNone() {
		JSONObject object = new JSONObject();
		assertEquals(NULL_DIN, testServlet.fetchDinInJson(object));
	}

	@Test
	public void fetchNameInJsonWhenExists() {
		JSONObject object = new JSONObject();
		object.put("nom", VALID_NAME);
		assertEquals(VALID_NAME, testServlet.fetchNameInJson(object));
	}

	@Test
	public void fetchNameInJsonWhenNone() {
		JSONObject object = new JSONObject();
		assertEquals(NULL_NAME, testServlet.fetchNameInJson(object));
	}

	@Test
	public void fetchStaffMemberWhenExists() {
		JSONObject object = new JSONObject();
		object.put("intervenant", STAFF_MEMBER);
		assertEquals(STAFF_MEMBER, testServlet.fetchStaffMemberInJson(object));
	}

	@Test
	public void fetchDateInJsonWhenExists() {
		JSONObject object = new JSONObject();
		object.put("date", DATE);
		assertEquals(DATE, testServlet.fetchDateInJson(object));
	}

	@Test
	public void fetchRenewalsWhenExists() {
		JSONObject object = new JSONObject();
		object.put("renouvellements", RENEWALS);
		assertEquals(RENEWALS, testServlet.fetchRenewalsInJson(object));

	}

	// TODO test out doPost()
	// TODO parseJsonObject()
	// TODO fetchJsonObject()
	// TODO sendBadRequest()
	// TODO sendCreatedMessage()
	// TODO createPrescriptionWithDin()
	// TODO createPrescriptionWithName()
	// TODO fetchUrlPatientNumber()
}
