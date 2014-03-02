package ca.ulaval.glo4002.requestparsers.surgicaltool;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.MarkExistingInstrumentRequestParser;

public class MarkExistingInstrumentRequestTest {
	
	private JSONObject myJsonRequest;
	private MarkExistingInstrumentRequestParser myRequest;

	@Rule public ExpectedException thrown=ExpectedException.none();
	
	@Test
	public void buildValidRequest() throws ServiceRequestException {
		myJsonRequest = new JSONObject("{ \"statut\":\"SOUILLE\", \"noserie\":\"23562543-3635345\", \"nointervention\":\"0\" }");
		
		myRequest = new MarkExistingInstrumentRequestParser(myJsonRequest);
	}
	
	@Test
	public void doNotBuildEmptyRequest() throws ServiceRequestException {
		thrown.expect(ServiceRequestException.class);
		myJsonRequest = new JSONObject("{}");
		
		myRequest = new MarkExistingInstrumentRequestParser(myJsonRequest);
	}
	
	@Test
	public void doNotBuildWithInvalidStatus() throws ServiceRequestException {
		thrown.expect(ServiceRequestException.class);
		myJsonRequest = new JSONObject("{ \"statut\":\"AUTRE\", \"noserie\":\"23562543-3635345\", \"nointervention\":\"0\" }");
		
		myRequest = new MarkExistingInstrumentRequestParser(myJsonRequest);
	}
	
	@Test
	public void buildWithDirtyStatus() throws ServiceRequestException {
		myJsonRequest = new JSONObject("{ \"statut\":\"SOUILLE\", \"noserie\":\"23562543-3635345\", \"nointervention\":\"0\" }");
		
		myRequest = new MarkExistingInstrumentRequestParser(myJsonRequest);
	}
	
	@Test
	public void buildWithUsedOnPatientStatus() throws ServiceRequestException {
		myJsonRequest = new JSONObject("{ \"statut\":\"UTILISE_PATIENT\", \"noserie\":\"23562543-3635345\", \"nointervention\":\"0\" }");
		
		myRequest = new MarkExistingInstrumentRequestParser(myJsonRequest);
	}
	
	@Test
	public void buildWithUnusedStatus() throws ServiceRequestException {
		myJsonRequest = new JSONObject("{ \"statut\":\"INUTILISE\", \"noserie\":\"23562543-3635345\", \"nointervention\":\"0\" }");
		
		myRequest = new MarkExistingInstrumentRequestParser(myJsonRequest);
	}
	
	@Test
	public void doNotModifyTypeCode() throws ServiceRequestException {
		myJsonRequest = new JSONObject("{ \"statut\":\"INUTILISE\", \"noserie\":\"23562543-3635345\", \"typecode\":\"IT72353\", \"nointervention\":\"0\" }");
	}
}
