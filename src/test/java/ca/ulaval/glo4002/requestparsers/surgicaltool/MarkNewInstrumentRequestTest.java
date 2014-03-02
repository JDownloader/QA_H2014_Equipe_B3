package ca.ulaval.glo4002.requestparsers.surgicaltool;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.MarkNewInstrumentRequestParser;

public class MarkNewInstrumentRequestTest {
	
	private JSONObject aValidRequest;
	private JSONObject anInvalidRequest;
	private MarkNewInstrumentRequestParser myRequest;
	
	@Rule public ExpectedException thrown=ExpectedException.none();
	
	@Test
	public void buildValidObject() throws BadRequestException {
		aValidRequest = new JSONObject("{\"typecode\": \"IT72353\", \"statut\": \"UTILISE_PATIENT\", \"noserie\" : \"23562543-3635345\", \"nointervention\":\"0\" }");
		
		myRequest = new MarkNewInstrumentRequestParser(aValidRequest);
	}
	
	@Test
	public void doNotBuildEmptyObject() throws BadRequestException {
		thrown.expect(BadRequestException.class);
		anInvalidRequest = new JSONObject("{}");
		
		myRequest = new MarkNewInstrumentRequestParser(anInvalidRequest);
	}
	
	@Test
	public void doNotBuildWithoutTypecode() throws BadRequestException {
		thrown.expect(BadRequestException.class);
		anInvalidRequest = new JSONObject("{\"statut\": \"UTILISE_PATIENT\", \"noserie\" : \"23562543-3635345\", \"nointervention\":\"0\" }");
		
		myRequest = new MarkNewInstrumentRequestParser(anInvalidRequest);
	}
	
	@Test
	public void doNotBuildWithoutStatus() throws BadRequestException {
		thrown.expect(BadRequestException.class);
		anInvalidRequest = new JSONObject("{\"typecode\": \"IT72353\", \"noserie\" : \"23562543-3635345\", \"nointervention\":\"0\" }");
		
		myRequest = new MarkNewInstrumentRequestParser(anInvalidRequest);
	}
	
	@Test
	public void buildWithoutSerialNumber() throws BadRequestException {
		anInvalidRequest = new JSONObject("{\"typecode\": \"IT72353\", \"statut\": \"UTILISE_PATIENT\", \"nointervention\":\"0\"}");
		
		myRequest = new MarkNewInstrumentRequestParser(anInvalidRequest);
	}

	@Test
	public void buildObjectWithUsedOnPatientStatus() throws BadRequestException {
		aValidRequest = new JSONObject("{\"typecode\": \"IT72353\", \"statut\": \"UTILISE_PATIENT\", \"noserie\" : \"23562543-3635345\", \"nointervention\":\"0\"}");
		
		myRequest = new MarkNewInstrumentRequestParser(aValidRequest);
	}
	
	@Test
	public void buildObjectWithDirtyStatus() throws BadRequestException {
		aValidRequest = new JSONObject("{\"typecode\": \"IT72353\", \"statut\": \"SOUILLE\", \"noserie\" : \"23562543-3635345\", \"nointervention\":\"0\"}");
		
		myRequest = new MarkNewInstrumentRequestParser(aValidRequest);
	}
	
	@Test
	public void buildObjectWithUnusedStatus() throws BadRequestException {
		aValidRequest = new JSONObject("{\"typecode\": \"IT72353\", \"statut\": \"INUTILISE\", \"noserie\" : \"23562543-3635345\", \"nointervention\":\"0\"}");
		
		myRequest = new MarkNewInstrumentRequestParser(aValidRequest);
	}
	
	@Test
	public void doNotBuildObjectWithBadStatus() throws BadRequestException {
		thrown.expect(BadRequestException.class);
		anInvalidRequest = new JSONObject("{\"typecode\": \"IT72353\", \"statut\": \"AUTRE\", \"noserie\" : \"23562543-3635345\", \"nointervention\":\"0\"}");
		
		myRequest = new MarkNewInstrumentRequestParser(anInvalidRequest);
	}
}
