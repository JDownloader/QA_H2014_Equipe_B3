package ca.ulaval.glo4002.rest.requestparsers.surgicaltool;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.RequestParseException;

public class ModifySurgicalToolRequestParser extends CreateSurgicalToolRequestParser {
	public String NEW_SERIAL_NUMBER_PARAMETER_NAME = "nouveaunoserie";
	
	protected String newSerialNumber;
	
	public ModifySurgicalToolRequestParser(JSONObject jsonRequest) throws RequestParseException {
		super(jsonRequest);
		try {
			parseParameters(jsonRequest);
		}
		catch(Exception e) {
			throw new RequestParseException("Invalid parameters were supplied to the request.");
		}
		validateParameterSemantics();
	}
	
	private void parseParameters(JSONObject jsonRequest) {
		this.newSerialNumber = jsonRequest.getString(NEW_SERIAL_NUMBER_PARAMETER_NAME);
	}
	
	private void validateParameterSemantics() throws RequestParseException {
		if (StringUtils.isBlank(this.newSerialNumber)) {
			throw new RequestParseException("Parameter 'noserie' must not be blank.");
		}
	}
	
	public String getNewSerialNumber() {
		return this.newSerialNumber;
	}

}
