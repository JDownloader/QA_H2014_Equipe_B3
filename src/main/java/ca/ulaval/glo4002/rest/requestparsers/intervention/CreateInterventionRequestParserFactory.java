package ca.ulaval.glo4002.rest.requestparsers.intervention;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateInterventionRequestParserFactory {
	public CreateInterventionRequestParser getParser(JSONObject jsonRequest) throws JSONException, ParseException {
		return new CreateInterventionRequestParser(jsonRequest);
	}
}
