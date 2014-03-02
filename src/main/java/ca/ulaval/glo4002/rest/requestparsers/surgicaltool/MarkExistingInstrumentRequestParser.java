package ca.ulaval.glo4002.rest.requestparsers.surgicaltool;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;

public class MarkExistingInstrumentRequestParser extends MarkInstrumentRequestParser {
	
	public MarkExistingInstrumentRequestParser(JSONObject jsonRequest) throws ServiceRequestException {
		buildRequest(jsonRequest);
		validateRequestParameters();
	}
	
	private void buildRequest(JSONObject jsonRequest) throws ServiceRequestException {
		if(jsonRequest.has(TYPECODE_PARAMETER))
			throw new ServiceRequestException(INVALID_OR_INCOMPLETE_REQUEST_CODE, INVALID_OR_INCOMPLETE_REQUEST_MESSAGE);
		
		try {
			this.status = jsonRequest.getString("statut");
			this.serialNumber = jsonRequest.getString("noserie");
			this.interventionId = jsonRequest.getInt(INTERVENTION_NUMBER_PARAMETER);
		} catch (JSONException e) {
			throw new ServiceRequestException("INT010", "données invalides ou incomplètes");
		}
	}
	
	void validateRequestParameters() throws ServiceRequestException {
		validateStatus();
	}
	
}
