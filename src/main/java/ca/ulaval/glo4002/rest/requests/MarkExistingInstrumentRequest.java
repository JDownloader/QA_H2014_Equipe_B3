package ca.ulaval.glo4002.rest.requests;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.BadRequestException;

public class MarkExistingInstrumentRequest {

	private String status;
	private String serialNumber;
	
	public MarkExistingInstrumentRequest(JSONObject jsonRequest) throws BadRequestException {
		buildRequest(jsonRequest);
	}
	
	private void buildRequest(JSONObject jsonRequest) throws BadRequestException {
		try {
			this.status = jsonRequest.getString("statut");
			this.serialNumber = jsonRequest.getString("noserie");
		} catch (JSONException e) {
			throw new BadRequestException("INT010", "données invalides ou incomplètes");
		}
	}
	
}
