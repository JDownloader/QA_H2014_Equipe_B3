package ca.ulaval.glo4002.server;

import org.junit.Test;



public class PrescriptionServletTest {

	PrescriptionServlet testServlet = new PrescriptionServlet();
	
	@Test
	public void servletReceivesBadRequest(){
		String badRequest = "{'intervenant': 000000, 'date': '2001-07-04T12:08:56', 'renouvellements': 0, 'din': 02240541, 'nom': 'ADVIL NIGHTTIME LIQUI'}";
	}
}
