package ca.ulaval.glo4002.server;

import org.junit.Test;



public class PrescriptionServletTest {
	
	private static final int VALID_DIN = 02240541;
	private static final Integer NULL_DIN = null;
	private static final String VALID_NAME = "ADVIL NIGHTTIME LIQUI";
	private static final String NULL_NAME = null;
	private static final Integer STAFF_MEMBER = 000000;
	private static final int RENEWALS = 0;
	private static final String DATE = "2001-07-04T12:08:56";
	PrescriptionServlet testServlet = new PrescriptionServlet();
	
	@Test
	public void servletReceivesBadRequest(){
		String badRequest = "{'intervenant': 000000, 'date': '2001-07-04T12:08:56', 'renouvellements': 0, 'din': 02240541, 'nom': 'ADVIL NIGHTTIME LIQUI'}";
		testServlet.parseJsonObject(badRequest);
	}
	
	/*@Test(expected = NullPointerException.class)
	public void servletReceivesRequestWithDin(){
		String goodRequest = "{'intervenant': 000000, 'date': '2001-07-04T12:08:56', 'renouvellements': 0, 'din': 02240541}";
		testServlet.parseJsonObject(goodRequest);
		testServlet.createPrescriptionWithDin(VALID_DIN, NULL_NAME, STAFF_MEMBER, RENEWALS, DATE);
	}*/
	
	@Test(expected = NullPointerException.class)
	public void servletReceivesRequestWithName(){
		String goodRequest = "{'intervenant': 000000, 'date': '2001-07-04T12:08:56', 'renouvellements': 0, 'nom': 'ADVIL NIGHTTIME LIQUI'}";
		testServlet.parseJsonObject(goodRequest);
		testServlet.createPrescriptionWithName(NULL_DIN, VALID_NAME, STAFF_MEMBER, RENEWALS, DATE);
	}
}
