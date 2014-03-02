package ca.ulaval.glo4002.rest.requestparsers.drug;

import java.text.ParseException;

import org.json.*;

public class DrugSearchRequestParserFactory {
	public DrugSearchRequestParser createDrugSearchRequest(JSONObject jsonRequest) throws JSONException, ParseException {
		return new DrugSearchRequestParser(jsonRequest);
	}
}
