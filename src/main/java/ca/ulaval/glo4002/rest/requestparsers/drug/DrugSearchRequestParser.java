package ca.ulaval.glo4002.rest.requestparsers.drug;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.RequestParseException;

public class DrugSearchRequestParser {
	private static final String NAME_PARAMETER = "nom";
	
	private String name;
	
	public DrugSearchRequestParser(JSONObject jsonRequest) throws RequestParseException {
		try {
			parseParameters(jsonRequest);
		}
		catch(Exception e) {
			throw new RequestParseException("Invalid parameters were supplied to the request.");
		}
		
		validateParameterSemantics();
	}

	private void parseParameters(JSONObject jsonRequest) {
		this.name = jsonRequest.getString(NAME_PARAMETER);
	}
	
	private void validateParameterSemantics() throws RequestParseException {
		if (StringUtils.isBlank(this.name) || this.name.length() < 3) {
			throw new RequestParseException("Search criteria must not be less than 3 characters.");
		}
	}
	
	public String getName() {
		return this.name;
	}

}
