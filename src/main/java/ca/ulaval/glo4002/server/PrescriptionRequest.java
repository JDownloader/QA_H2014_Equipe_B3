package ca.ulaval.glo4002.server;

import org.json.JSONObject;

public class PrescriptionRequest extends Request {

	private static final String DIN_PARAMETER = "din";
	private static final String STAFF_MEMBER_PARAMETER = "intervenant";
	private static final String NAME_PARAMETER = "nom";
	private static final String DATE_PARAMETER = "date";
	private static final String RENEWAL_PARAMETER = "renouvellements";

	private Integer din;
	private String name;
	private Integer staffMember;
	private Integer renewals;
	private String date;

	// Constructor
	public PrescriptionRequest(JSONObject jsonRequest) {
		this.din = jsonRequest.getInt(DIN_PARAMETER);
		this.name = jsonRequest.getString(NAME_PARAMETER);
		this.staffMember = jsonRequest.getInt(STAFF_MEMBER_PARAMETER);
		this.renewals = jsonRequest.getInt(RENEWAL_PARAMETER);
		this.date = jsonRequest.getString(DATE_PARAMETER);
	}

	// Getters
	public Integer getDin() {
		return this.din;
	}

	public String getName() {
		return this.name;
	}

	public Integer getStaffMember() {
		return this.staffMember;
	}

	public Integer getrenewals() {
		return this.renewals;
	}

	public String getDate() {
		return this.date;
	}

	// Other methods
	public boolean isValid() {
		// TODO complete the isValid() method
		if (!this.validateDinAndName() && this.staffMember == null
				&& this.renewals == null && !this.validateDate())
			return false;
		return true;
	}

	private boolean validateDinAndName() {
		if ((!this.name.isEmpty() && !(this.din == null))
				|| (this.name.isEmpty() && (this.din != null)))
			return false;
		return true;
	}

	private boolean validateDate() {
		// TODO complete the validateDate() method; must verify the format.
		if (this.date == null)
			return false;
		return true;
	}
}
