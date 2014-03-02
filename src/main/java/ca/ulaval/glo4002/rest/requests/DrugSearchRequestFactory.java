package ca.ulaval.glo4002.rest.requests;

import java.text.ParseException;

import org.json.*;

public class DrugSearchRequestFactory {
	public DrugSearchRequest createDrugSearchRequest(JSONObject jsonRequest) throws JSONException, ParseException {
		return new DrugSearchRequest(jsonRequest);
	}
}
