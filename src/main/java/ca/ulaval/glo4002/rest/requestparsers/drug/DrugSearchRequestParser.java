package ca.ulaval.glo4002.rest.requestparsers.drug;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class DrugSearchRequestParser {
	private static final String NAME_PARAMETER = "nom";
	
	private String name;
	
	public DrugSearchRequestParser(JSONObject jsonRequest) throws JSONException, ParseException{
		this.name = jsonRequest.getString(NAME_PARAMETER);
		validateRequestParameters();
	}
	
	private void validateRequestParameters() {
		if (StringUtils.isBlank(this.name) || this.name.length() < 3) {
			throw new IllegalArgumentException("Search criteria must not be less than 3 characters.");
		}
	}
	
	public String getName() {
		return this.name;
	}

}
