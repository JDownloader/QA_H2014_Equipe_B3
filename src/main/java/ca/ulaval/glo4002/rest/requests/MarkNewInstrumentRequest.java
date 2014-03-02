package ca.ulaval.glo4002.rest.requests;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.BadRequestException;

public class MarkNewInstrumentRequest extends MarkInstrumentRequest {
	
	public MarkNewInstrumentRequest(JSONObject jsonRequest) throws BadRequestException {
		buildObject(jsonRequest);
		validateRequestParameters();
	}
	
	private void buildObject(JSONObject jsonRequest) throws BadRequestException {
		try {
			this.typecode = jsonRequest.getString(TYPECODE_PARAMETER);
			this.status = jsonRequest.getString(STATUS_PARAMETER);
			this.interventionId = jsonRequest.getInt(INTERVENTION_NUMBER_PARAMETER);
			initializeSerialNumber(jsonRequest);
		} catch (JSONException e) {
			throw new BadRequestException(INVALID_OR_INCOMPLETE_REQUEST_CODE,INVALID_OR_INCOMPLETE_REQUEST_MESSAGE);
		}
	}
	
	private void initializeSerialNumber(JSONObject jsonRequest) throws BadRequestException {
		try {
			this.serialNumber = jsonRequest.getString(SERIAL_NUMBER_PARAMETER);
		} catch (JSONException e) {
			this.serialNumber = null;
		}
	}
	
	void validateRequestParameters() throws BadRequestException {
		validateStatus();
	}
	
}
