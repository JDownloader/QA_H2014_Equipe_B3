package ca.ulaval.glo4002.rest.requestparsers.drug;

import org.json.*;

import ca.ulaval.glo4002.exceptions.RequestParseException;

public class DrugSearchRequestParserFactory {
	public DrugSearchRequestParser getParser(JSONObject jsonRequest) throws RequestParseException {
		return new DrugSearchRequestParser(jsonRequest);
	}
}
