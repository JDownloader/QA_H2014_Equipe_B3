package ca.ulaval.glo4002.rest.requestparsers.surgicaltool;

import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.RequestParseException;

public class CreateSurgicalToolRequestParserFactory {
	public CreateSurgicalToolRequestParser getParser(JSONObject jsonRequest) throws RequestParseException {
		return new CreateSurgicalToolRequestParser(jsonRequest);
	}
}
