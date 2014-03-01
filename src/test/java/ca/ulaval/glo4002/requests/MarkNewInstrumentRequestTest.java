package ca.ulaval.glo4002.requests;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.rest.requests.MarkNewInstrumentAsUsedRequest;

public class MarkNewInstrumentAsUsedRequestTest {
	
	private JSONObject anEmptyRequest;
	private JSONObject aValidRequest;
	
	private MarkNewInstrumentAsUsedRequest myRequest;

	@Before
	public void init() {
		anEmptyRequest = new JSONObject("{}");
		aValidRequest = new JSONObject("{\"typecode\": \"IT72353\", \"statut\": \"UTILISE_PATIENT\", \"noserie\" : \"23562543-3635345\" }");
	}
	
	@Test
	public void buildValidObject() throws Exception {
		myRequest = new MarkNewInstrumentAsUsedRequest(aValidRequest);
	}
	
	@Test(expected=JSONException.class)
	public void doNotBuildEmptyObject() {
		myRequest = new MarkNewInstrumentAsUsedRequest(anEmptyRequest);
	}
	
	@Test(expected=JSONException.class)
	public void doNotBuildWithoutTypecode() {
		assertTrue(false);
	}
	
	@Test(expected=JSONException.class)
	public void doNotBuildWithoutStatus() {
		assertTrue(false);
	}
	
	@Test(expected=JSONException.class)
	public void doNotBuildWithoutSerialNumber() {
		assertTrue(false);
	}

}
