package ca.ulaval.glo4002.rest.requestparsers.intervention;

import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.RequestParseException;

public class CreateInterventionRequestParserFactory {
	public CreateInterventionRequestParser getParser(JSONObject jsonRequest) throws RequestParseException {
		return new CreateInterventionRequestParser(jsonRequest);
	}
}
