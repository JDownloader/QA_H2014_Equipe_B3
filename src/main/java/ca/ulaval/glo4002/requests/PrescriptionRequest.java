package ca.ulaval.glo4002.requests;

import org.json.JSONObject;

public class PrescriptionRequest implements Request {

	private static final String DIN_PARAMETER = "din";
	private static final String STAFF_MEMBER_PARAMETER = "intervenant";
	private static final String NAME_PARAMETER = "nom";
	private static final String DATE_PARAMETER = "date";
	private static final String RENEWAL_PARAMETER = "renouvellements";

	private static final int INVALID_DIN = -5;
	private static final String INVALID_NAME = "";
	private static final int INVALID_STAFF_MEMBER = -5;
	private static final int INVALID_RENEWAL = -5;
	private static final String INVALID_DATE = "";
	
	private int din;
	private String name;
	private int staffMember;
	private int renewals;
	private String date;

	// Constructor
	public PrescriptionRequest(JSONObject jsonRequest) {
		
		this.din = (jsonRequest.has(DIN_PARAMETER)) ? jsonRequest.getInt(DIN_PARAMETER) : INVALID_DIN;
		this.name = (jsonRequest.has(NAME_PARAMETER)) ? jsonRequest.getString(NAME_PARAMETER) : INVALID_NAME;
		this.staffMember = (jsonRequest.has(STAFF_MEMBER_PARAMETER)) ? jsonRequest.getInt(STAFF_MEMBER_PARAMETER) : INVALID_STAFF_MEMBER;
		this.renewals = (jsonRequest.has(RENEWAL_PARAMETER)) ? jsonRequest.getInt(RENEWAL_PARAMETER) : INVALID_RENEWAL;
		this.date = (jsonRequest.has(DATE_PARAMETER)) ? jsonRequest.getString(DATE_PARAMETER) : INVALID_DATE;
	}

	// Getters
	public int getDin() {
		return this.din;
	}

	public String getName() {
		return this.name;
	}

	public int getStaffMember() {
		return this.staffMember;
	}

	public int getRenewals() {
		return this.renewals;
	}

	public String getDate() {
		return this.date;
	}

	// Other methods
	public boolean isValid() {
		if (this.validateDinAndName() && this.staffMember >= 0
				&& this.renewals >= 0 && this.validateDate())
			return false;
		return true;
	}

	public boolean validateDinAndName() {
		if ((!this.name.isEmpty() && (this.din < 0))
				|| (this.name.isEmpty() && !(this.din < 0)))
			return false;
		return true;
	}

	public boolean validateDate() {
		// TODO complete the validateDate() method; must verify the format. 
		// We'll probably need a date object since we're gonna use this everywhere.
		if (this.date == null)
			return false;
		return true;
	}
}
