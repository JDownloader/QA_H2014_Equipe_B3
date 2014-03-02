package ca.ulaval.glo4002.rest.requestparsers.prescription;

import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.RequestParseException;

public class AddPrescriptionRequestParserFactory {
	public AddPrescriptionRequestParser getParser(JSONObject jsonRequest, String patientNumberParameter) throws RequestParseException {
		return new AddPrescriptionRequestParser(jsonRequest, patientNumberParameter);
	}
}
