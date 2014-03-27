package ca.ulaval.glo4002.rest.requestparsers.surgicaltool;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;
import ca.ulaval.glo4002.exceptions.RequestParseException;

public class SurgicalToolCreationRequestParser extends AbstractSurgicalToolRequestParser implements SurgicalToolRequestParser {

	public SurgicalToolCreationRequestParser(JSONObject jsonRequest) throws RequestParseException {
		try {
			parseParameters(jsonRequest);
		} catch (Exception e) {
			throw new RequestParseException("Invalid parameters were supplied to the request.");
		}

		validateParameterSemantics();
	}

	private void parseParameters(JSONObject jsonRequest) {
		this.status = SurgicalToolStatus.fromString(jsonRequest.getString(STATUS_PARAMETER_NAME));
		this.typeCode = jsonRequest.getString(TYPECODE_PARAMETER_NAME);
		this.serialNumber = jsonRequest.optString(SERIAL_NUMBER_PARAMETER_NAME); //optional: returns "" if not present
		this.interventionNumber = jsonRequest.getInt(INTERVENTION_NUMBER_PARAMETER_NAME);
	}

	private void validateParameterSemantics() throws RequestParseException {
		if (this.interventionNumber < 0) {
			throw new RequestParseException("Parameter '$NO_INTERVENTION$' must be greater or equal to 0.");
		} else if (StringUtils.isBlank(this.typeCode)) {
			throw new RequestParseException("Parameter 'typecode' must not be blank.");
		}
	}
}
