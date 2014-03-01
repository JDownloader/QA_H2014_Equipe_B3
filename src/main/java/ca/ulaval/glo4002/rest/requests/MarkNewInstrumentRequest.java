package ca.ulaval.glo4002.rest.requests;

import org.json.JSONException;
import org.json.JSONObject;

public class MarkNewInstrumentAsUsedRequest {
	private String typecode;
	private String status;
	private String serialNumber;
	
	public MarkNewInstrumentAsUsedRequest(JSONObject jsonRequest) throws Exception {
		try {
			this.typecode = jsonRequest.getString("typecode");
			this.status = jsonRequest.getString("statut");
			this.serialNumber = jsonRequest.getString("noserie");
		} catch (JSONException e) {
			throw e;
		}
		
	}

	public String getTypecode() {
		return typecode;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
}
