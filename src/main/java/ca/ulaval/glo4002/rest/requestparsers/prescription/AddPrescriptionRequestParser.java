package ca.ulaval.glo4002.rest.requestparsers.prescription;

import java.text.ParseException;
import java.util.Date;

import org.h2.util.StringUtils;
import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.utils.CustomDateParser;

public class AddPrescriptionRequestParser {

	private static final int UNSPECIFIED_VALUE = -1;
	private static final String DIN_PARAMETER_NAME = "din";
	private static final String STAFF_MEMBER_PARAMETER_NAME = "intervenant";
	private static final String DRUG_NAME_PARAMETER_NAME = "nom";
	private static final String DATE_PARAMETER_NAME = "date";
	private static final String RENEWAL_PARAMETER_NAME = "renouvellements";

	private int din;
	private String drugName;
	private int staffMember;
	private int renewals;
	private Date date;
	private int patientNumber;

	public AddPrescriptionRequestParser(JSONObject jsonRequest, String patientNumberParameter) throws RequestParseException {
		try {
			parseParameters(jsonRequest, patientNumberParameter);
		}
		catch(Exception e) {
			throw new RequestParseException("Invalid parameters were supplied to the request.");
		}
		
		validateParameterSemantics();
	}

	private void parseParameters(JSONObject jsonRequest, String patientNumberParameter) throws ParseException {
		this.din = jsonRequest.optInt(DIN_PARAMETER_NAME, UNSPECIFIED_VALUE);
		this.drugName = jsonRequest.optString(DRUG_NAME_PARAMETER_NAME);
		this.staffMember = jsonRequest.getInt(STAFF_MEMBER_PARAMETER_NAME);
		this.renewals = jsonRequest.getInt(RENEWAL_PARAMETER_NAME);
		this.date = CustomDateParser.parseDate(jsonRequest.getString(DATE_PARAMETER_NAME));
		this.patientNumber = Integer.parseInt(patientNumberParameter);
	}

	private void validateParameterSemantics() throws RequestParseException {
		if (this.staffMember < 0) {
			throw new RequestParseException ("Parameter 'intervenant' must be greater or equal to 0.");
		} else if (this.renewals < 0) {
			throw new RequestParseException ("Parameter 'renouvellements' must be greater or equal to 0.");
		} else if (this.renewals < 0) {
			throw new RequestParseException ("Path parameter '$NO_PATIENT$' must be greater or equal to 0.");
		} else if (!this.validateDinAndName()) {
			throw new RequestParseException ("Either parameter 'din' or 'nom' must be specified, but not both.");
		}
	}

	private boolean validateDinAndName() {
		return StringUtils.isNullOrEmpty(this.drugName) ^ (this.din < 0);
	}

	public boolean hasDin() {
		return this.din != UNSPECIFIED_VALUE;
	}

	public int getDin() {
		return this.din;
	}

	public String getDrugName() {
		return this.drugName;
	}

	public int getStaffMember() {
		return this.staffMember;
	}

	public int getPatientNumber() {
		return this.patientNumber;
	}

	public int getRenewals() {
		return this.renewals;
	}

	public Date getDate() {
		return this.date;
	}
}
