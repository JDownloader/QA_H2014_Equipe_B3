package ca.ulaval.glo4002.rest.requests;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

public class AddPrescriptionRequestFactory {
	public AddPrescriptionRequest createAddprescriptionRequest(JSONObject jsonRequest) throws JSONException, ParseException {
		return new AddPrescriptionRequest(jsonRequest);
	}
}
