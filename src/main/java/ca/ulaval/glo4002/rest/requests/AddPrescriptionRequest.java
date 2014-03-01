package ca.ulaval.glo4002.rest.requests;

import java.text.ParseException;
import java.util.Date;

import org.h2.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.utils.CustomDateParser;

public class AddPrescriptionRequest {

	private static final int UNSPECIFIED_VALUE = -1;
	private static final String DIN_PARAMETER = "din";
	private static final String STAFF_MEMBER_PARAMETER = "intervenant";
	private static final String DRUG_NAME_PARAMETER = "nom";
	private static final String DATE_PARAMETER = "date";
	private static final String RENEWAL_PARAMETER = "renouvellements";
	private static final String PATIENT_PARAMETER = "patient";

	private int din;
	private String drugName;
	private int staffMember;
	private int renewals;
	private Date date;
	private int patientNumber;

	public AddPrescriptionRequest(JSONObject jsonRequest) throws JSONException, ParseException {
		this.din = jsonRequest.optInt(DIN_PARAMETER, UNSPECIFIED_VALUE);
		this.drugName = jsonRequest.optString(DRUG_NAME_PARAMETER);
		this.staffMember = jsonRequest.getInt(STAFF_MEMBER_PARAMETER);
		this.renewals = jsonRequest.getInt(RENEWAL_PARAMETER);
		this.date = CustomDateParser.parseDate(jsonRequest.getString(DATE_PARAMETER));
		this.patientNumber = jsonRequest.getInt(PATIENT_PARAMETER);
	}

	public void validateRequestParameters() {
		if (!this.validateDinAndName() || this.staffMember < 0 || this.renewals < 0 || this.patientNumber < 0) {
			throw new IllegalArgumentException("Invalid parameters were supplied to the request.");
		}
	}

	public boolean validateDinAndName() {
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
