package ca.ulaval.glo4002.rest.requestparsers.intervention;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import ca.ulaval.glo4002.domain.intervention.InterventionStatus;
import ca.ulaval.glo4002.domain.intervention.InterventionType;
import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.utils.DateParser;

public class CreateInterventionRequestParser {

	public static final String DESCRIPTION_PARAMETER_NAME = "description";
	public static final String SURGEON_PARAMETER_NAME = "chirurgien";
	public static final String DATE_PARAMETER_NAME = "date";
	public static final String ROOM_PARAMETER_NAME = "salle";
	public static final String TYPE_PARAMETER_NAME = "type";
	public static final String STATUS_PARAMETER_NAME = "statut";
	public static final String PATIENT_PARAMETER_NAME = "patient";
	
	private String description;
	private int surgeon;
	private Date date;
	private String room;
	private InterventionType type;
	private InterventionStatus status;
	private int patient;
	
	public CreateInterventionRequestParser(JSONObject jsonRequest) throws RequestParseException {
		try {
			parseParameters(jsonRequest);
		}
		catch(Exception e) {
			throw new RequestParseException("Invalid parameters were supplied to the request.");
		}
		
		validateParameterSemantics();
	}

	private void parseParameters(JSONObject jsonRequest) throws ParseException {
		this.description = jsonRequest.getString(DESCRIPTION_PARAMETER_NAME);
		this.surgeon = jsonRequest.getInt(SURGEON_PARAMETER_NAME);
		this.date = DateParser.parseDate(jsonRequest.getString(DATE_PARAMETER_NAME));
		this.room = jsonRequest.getString(ROOM_PARAMETER_NAME);
		this.type = InterventionType.fromString(jsonRequest.getString(TYPE_PARAMETER_NAME));
		String statusParameter = jsonRequest.optString(STATUS_PARAMETER_NAME);
		this.status = InterventionStatus.fromString(statusParameter);
		this.patient = jsonRequest.getInt(PATIENT_PARAMETER_NAME);
	}
	
	private void validateParameterSemantics() throws RequestParseException {
		if (this.surgeon < 0) {
			throw new RequestParseException("Parameter 'chirurgien' must be greater or equal to 0.");
		} else if (this.patient < 0) {
			throw new RequestParseException("Parameter 'patient' must be greater or equal to 0.");
		} else if (StringUtils.isBlank(this.description)) {
			throw new RequestParseException("Parameter 'description' must not be blank.");
		} else if (StringUtils.isBlank(this.room)) {
			throw new RequestParseException("Parameter 'salle' must not be blank.");
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
