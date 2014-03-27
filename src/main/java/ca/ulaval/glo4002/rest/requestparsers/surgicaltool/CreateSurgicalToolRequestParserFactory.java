package ca.ulaval.glo4002.rest.requestparsers.surgicaltool;

import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.RequestParseException;

public class CreateSurgicalToolRequestParserFactory {
	public SurgicalToolCreationRequestParser getParser(JSONObject jsonRequest) throws RequestParseException {
		return new SurgicalToolCreationRequestParser(jsonRequest);
	}
}
