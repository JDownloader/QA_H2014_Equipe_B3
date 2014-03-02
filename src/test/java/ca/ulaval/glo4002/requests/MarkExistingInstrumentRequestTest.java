package ca.ulaval.glo4002.requests;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.MarkExistingInstrumentRequest;

public class MarkExistingInstrumentRequestTest {
	
	private JSONObject myJsonRequest;
	private MarkExistingInstrumentRequest myRequest;

	@Rule public ExpectedException thrown=ExpectedException.none();
	
	@Test
	public void buildValidRequest() throws BadRequestException {
		myJsonRequest = new JSONObject("{ \"statut\":\"SOUILLE\", \"noserie\":\"23562543-3635345\", \"nointervention\":\"0\" }");
		
		myRequest = new MarkExistingInstrumentRequest(myJsonRequest);
	}
	
	@Test
	public void doNotBuildEmptyRequest() throws BadRequestException {
		thrown.expect(BadRequestException.class);
		myJsonRequest = new JSONObject("{}");
		
		myRequest = new MarkExistingInstrumentRequest(myJsonRequest);
	}
	
	@Test
	public void doNotBuildWithInvalidStatus() throws BadRequestException {
		thrown.expect(BadRequestException.class);
		myJsonRequest = new JSONObject("{ \"statut\":\"AUTRE\", \"noserie\":\"23562543-3635345\", \"nointervention\":\"0\" }");
		
		myRequest = new MarkExistingInstrumentRequest(myJsonRequest);
	}
	
	@Test
	public void buildWithDirtyStatus() throws BadRequestException {
		myJsonRequest = new JSONObject("{ \"statut\":\"SOUILLE\", \"noserie\":\"23562543-3635345\", \"nointervention\":\"0\" }");
		
		myRequest = new MarkExistingInstrumentRequest(myJsonRequest);
	}
	
	@Test
	public void buildWithUsedOnPatientStatus() throws BadRequestException {
		myJsonRequest = new JSONObject("{ \"statut\":\"UTILISE_PATIENT\", \"noserie\":\"23562543-3635345\", \"nointervention\":\"0\" }");
		
		myRequest = new MarkExistingInstrumentRequest(myJsonRequest);
	}
	
	@Test
	public void buildWithUnusedStatus() throws BadRequestException {
		myJsonRequest = new JSONObject("{ \"statut\":\"INUTILISE\", \"noserie\":\"23562543-3635345\", \"nointervention\":\"0\" }");
		
		myRequest = new MarkExistingInstrumentRequest(myJsonRequest);
	}
	
	@Test
	public void doNotModifyTypeCode() throws BadRequestException {
		myJsonRequest = new JSONObject("{ \"statut\":\"INUTILISE\", \"noserie\":\"23562543-3635345\", \"typecode\":\"IT72353\", \"nointervention\":\"0\" }");
	}
}
