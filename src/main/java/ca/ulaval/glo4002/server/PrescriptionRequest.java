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

	public PrescriptionRequest(JSONObject jsonRequest) {
		this.din = jsonRequest.getInt(DIN_PARAMETER);
		this.name = jsonRequest.getString(NAME_PARAMETER);
		this.staffMember = jsonRequest.getInt(STAFF_MEMBER_PARAMETER);
		this.renewals = jsonRequest.getInt(RENEWAL_PARAMETER);
		this.date = jsonRequest.getString(DATE_PARAMETER);
	}

	public boolean isValid() {
		if (validateDinAndName() && !STAFF_MEMBER_PARAMETER.isEmpty()
				&& !DATE_PARAMETER.isEmpty() && !RENEWAL_PARAMETER.isEmpty())
			return true;
		return false;
	}

	private boolean validateDinAndName() {
		if ((!DIN_PARAMETER.isEmpty() && !NAME_PARAMETER.isEmpty())
				|| (DIN_PARAMETER.isEmpty() && NAME_PARAMETER.isEmpty()))
			return false;
		return true;
	}
}
