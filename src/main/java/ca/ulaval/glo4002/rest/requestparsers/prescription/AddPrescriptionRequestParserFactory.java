package ca.ulaval.glo4002.rest.requestparsers.prescription;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

public class AddPrescriptionRequestParserFactory {
	public AddPrescriptionRequestParser createAddPrescriptionRequest(JSONObject jsonRequest, String patientNumberParameter) throws JSONException, ParseException {
		return new AddPrescriptionRequestParser(jsonRequest, patientNumberParameter);
	}
}
