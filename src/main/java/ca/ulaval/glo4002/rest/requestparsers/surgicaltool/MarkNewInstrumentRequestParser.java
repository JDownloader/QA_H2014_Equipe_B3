package ca.ulaval.glo4002.rest.requestparsers.surgicaltool;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;

public class MarkNewInstrumentRequestParser extends MarkInstrumentRequestParser {
	
	public MarkNewInstrumentRequestParser(JSONObject jsonRequest) throws ServiceRequestException {
		buildObject(jsonRequest);
		validateRequestParameters();
	}
	
	private void buildObject(JSONObject jsonRequest) throws ServiceRequestException {
		try {
			this.typecode = jsonRequest.getString(TYPECODE_PARAMETER);
			this.status = jsonRequest.getString(STATUS_PARAMETER);
			this.interventionId = jsonRequest.getInt(INTERVENTION_NUMBER_PARAMETER);
			initializeSerialNumber(jsonRequest);
		} catch (JSONException e) {
			throw new ServiceRequestException(INVALID_OR_INCOMPLETE_REQUEST_CODE,INVALID_OR_INCOMPLETE_REQUEST_MESSAGE);
		}
	}
	
	private void initializeSerialNumber(JSONObject jsonRequest) throws ServiceRequestException {
		try {
			this.serialNumber = jsonRequest.getString(SERIAL_NUMBER_PARAMETER);
		} catch (JSONException e) {
			this.serialNumber = null;
		}
	}
	
	void validateRequestParameters() throws ServiceRequestException {
		validateStatus();
	}
	
}
