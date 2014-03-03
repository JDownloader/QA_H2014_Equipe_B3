package ca.ulaval.glo4002.rest.requestparsers.prescription;

import java.text.ParseException;
import java.util.Date;

import org.h2.util.StringUtils;
import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.utils.DateParser;

public class AddPrescriptionRequestParser {

	private static final int UNSPECIFIED_VALUE = -1;
	public static final String PATIENT_NUMBER_PARAMETER_NAME = "nopatient";
	public static final String DIN_PARAMETER_NAME = "din";
	public static final String STAFF_MEMBER_PARAMETER_NAME = "intervenant";
	public static final String DRUG_NAME_PARAMETER_NAME = "nom";
	public static final String DATE_PARAMETER_NAME = "date";
	public static final String RENEWAL_PARAMETER_NAME = "renouvellements";

	private int din;
	private String drugName;
	private int staffMember;
	private int renewals;
	private Date date;
	private int patientNumber;

	public AddPrescriptionRequestParser(JSONObject jsonRequest) throws RequestParseException {
		try {
			parseParameters(jsonRequest);
		}
		catch(Exception e) {
			throw new RequestParseException("Invalid parameters were supplied to the request.");
		}
		
		validateParameterSemantics();
	}

	private void parseParameters(JSONObject jsonRequest) throws ParseException {
		this.din = jsonRequest.optInt(DIN_PARAMETER_NAME, UNSPECIFIED_VALUE);
		this.drugName = jsonRequest.optString(DRUG_NAME_PARAMETER_NAME);
		this.staffMember = jsonRequest.getInt(STAFF_MEMBER_PARAMETER_NAME);
		this.renewals = jsonRequest.getInt(RENEWAL_PARAMETER_NAME);
		this.date = DateParser.parseDate(jsonRequest.getString(DATE_PARAMETER_NAME));
		this.patientNumber = jsonRequest.getInt(PATIENT_NUMBER_PARAMETER_NAME);
	}

	private void validateParameterSemantics() throws RequestParseException {
		if (this.staffMember < 0) {
			throw new RequestParseException ("Parameter 'intervenant' must be greater or equal to 0.");
		} else if (this.renewals < 0) {
			throw new RequestParseException ("Parameter 'renouvellements' must be greater or equal to 0.");
		} else if (this.patientNumber < 0) {
			throw new RequestParseException ("Path parameter '$NO_PATIENT$' must be greater or equal to 0.");
		}
		validateDinAndName();
	}

	private void validateDinAndName() throws RequestParseException {
		boolean drugNameValid = !StringUtils.isNullOrEmpty(this.drugName);
		boolean isDinValid = this.din >= 0;
		
		if (drugNameValid && isDinValid) {
			throw new RequestParseException ("Either parameter 'din' or 'nom' must be specified, but not both.");	
		} else if (!drugNameValid && !isDinValid) {
			throw new RequestParseException ("Parameter 'din' or 'nom' must be specified.");
		}
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
