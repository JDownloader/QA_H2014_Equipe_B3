package ca.ulaval.glo4002.rest.requests;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.domain.intervention.InterventionStatus;
import ca.ulaval.glo4002.domain.intervention.InterventionType;
import ca.ulaval.glo4002.utils.CustomDateParser;

public class CreateInterventionRequest {

	private static final String DESCRIPTION_PARAMETER = "description";
	private static final String SURGEON_PARAMETER = "chirurgien";
	private static final String DATE_PARAMETER = "date";
	private static final String ROOM_PARAMETER = "salle";
	private static final String TYPE_PARAMETER = "type";
	private static final String STATUS_PARAMETER = "statut";
	private static final String PATIENT_PARAMETER = "patient";
	
	private String description;
	private int surgeon;
	private Date date;
	private String room;
	private InterventionType type;
	private InterventionStatus status;
	private int patient;
	
	public CreateInterventionRequest(JSONObject jsonRequest) throws JSONException, ParseException{
		this.description = jsonRequest.getString(DESCRIPTION_PARAMETER);
		this.surgeon = jsonRequest.getInt(SURGEON_PARAMETER);
		this.date = CustomDateParser.parseDate(jsonRequest.getString(DATE_PARAMETER));
		this.room = jsonRequest.getString(ROOM_PARAMETER);
		this.type = InterventionType.fromString(jsonRequest.getString(TYPE_PARAMETER));
		String statusParameter = jsonRequest.optString(STATUS_PARAMETER);
		this.status = InterventionStatus.fromString(statusParameter);
		this.patient = jsonRequest.getInt(PATIENT_PARAMETER);
		validateRequestParameters();
	}
	
	private void validateRequestParameters() {
		if (StringUtils.isBlank(this.description) || this.surgeon < 0 || StringUtils.isBlank(this.room) || this.patient < 0) {
			throw new IllegalArgumentException("Invalid parameters were supplied to the request.");
		}
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getSurgeon() {
		return this.surgeon;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public String getRoom() {
		return this.room;
	}
	
	public InterventionType getType() {
		return this.type;
	}
	
	public InterventionStatus getStatus() {
		return this.status;
	}
	
	public int getPatient() {
		return this.patient;
	}
}
