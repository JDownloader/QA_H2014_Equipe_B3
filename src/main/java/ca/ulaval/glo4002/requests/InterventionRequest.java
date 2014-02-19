package ca.ulaval.glo4002.requests;

import org.json.JSONObject;

public class InterventionRequest implements Request {
	//TODO complete class
	private static final String DESCRIPTION_PARAMETER = "description";
	private static final String SURGEON_PARAMETER = "chirurgien";
	private static final String DATE_PARAMETER = "date";
	private static final String ROOM_PARAMETER = "salle";
	private static final String TYPE_PARAMETER = "type";
	private static final String STATUS_PARAMETER = "statut";
	private static final String PATIENT_PARAMETER = "patient";
	
	private static final String INVALID_DESCRIPTION = "";
	private static final int INVALID_SURGEON = -5;
	private static final String INVALID_DATE = "";
	private static final String INVALID_ROOM = "";
	private static final String INVALID_TYPE = "";
	private static final String INVALID_STATUS = "";
	private static final int INVALID_PATIENT = -5;
	
	private String description;
	private int surgeon;
	private String date;
	private String room;
	private String type;
	private String status;
	private int patient;
	
	//Constructor
	public InterventionRequest(JSONObject jsonRequest){
		this.description = (jsonRequest.has(DESCRIPTION_PARAMETER)) ? jsonRequest.getString(DESCRIPTION_PARAMETER) : INVALID_DESCRIPTION;
		this.surgeon = (jsonRequest.has(SURGEON_PARAMETER)) ? jsonRequest.getInt(SURGEON_PARAMETER) : INVALID_SURGEON;
		this.date = (jsonRequest.has(DATE_PARAMETER)) ? jsonRequest.getString(DATE_PARAMETER) : INVALID_DATE;
		this.room = (jsonRequest.has(ROOM_PARAMETER)) ? jsonRequest.getString(ROOM_PARAMETER) : INVALID_ROOM;
		this.type = (jsonRequest.has(TYPE_PARAMETER)) ? jsonRequest.getString(TYPE_PARAMETER) : INVALID_TYPE;
		this.status = (jsonRequest.has(STATUS_PARAMETER)) ? jsonRequest.getString(STATUS_PARAMETER) : INVALID_STATUS;
		this.patient = (jsonRequest.has(PATIENT_PARAMETER)) ? jsonRequest.getInt(PATIENT_PARAMETER) : INVALID_PATIENT;
	}
	
	public boolean isValid() {
		//TODO make this function... functionnal.
		return true;
	}
}
