package ca.ulaval.glo4002.rest.requestparsers.prescription;

import java.text.ParseException;
import java.util.Date;

import org.h2.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.utils.CustomDateParser;

public class AddPrescriptionRequestParser {

	private static final int UNSPECIFIED_VALUE = -1;
	private static final String DIN_PARAMETER = "din";
	private static final String STAFF_MEMBER_PARAMETER = "intervenant";
	private static final String DRUG_NAME_PARAMETER = "nom";
	private static final String DATE_PARAMETER = "date";
	private static final String RENEWAL_PARAMETER = "renouvellements";

	private int din;
	private String drugName;
	private int staffMember;
	private int renewals;
	private Date date;
	private int patientNumber;

	public AddPrescriptionRequestParser(JSONObject jsonRequest, String patientNumberParameter) throws JSONException, ParseException, IllegalArgumentException {
		this.din = jsonRequest.optInt(DIN_PARAMETER, UNSPECIFIED_VALUE);
		this.drugName = jsonRequest.optString(DRUG_NAME_PARAMETER);
		this.staffMember = jsonRequest.getInt(STAFF_MEMBER_PARAMETER);
		this.renewals = jsonRequest.getInt(RENEWAL_PARAMETER);
		this.date = CustomDateParser.parseDate(jsonRequest.getString(DATE_PARAMETER));
		this.patientNumber = Integer.parseInt(patientNumberParameter);
		validateRequestParameters();
	}

	private void validateRequestParameters() {
		if (this.staffMember < 0) {
			throw new IllegalArgumentException("Parameter 'intervenant' must be greater or equal to 0.");
		} else if (this.renewals < 0) {
			throw new IllegalArgumentException("Parameter 'renouvellements' must be greater or equal to 0.");
		} else if (this.renewals < 0) {
			throw new IllegalArgumentException("Path parameter '$NO_PATIENT$' must be greater or equal to 0.");
		} else if (!this.validateDinAndName()) {
			throw new IllegalArgumentException("Either parameter 'din' or 'nom' must be specified, but not both.");
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
