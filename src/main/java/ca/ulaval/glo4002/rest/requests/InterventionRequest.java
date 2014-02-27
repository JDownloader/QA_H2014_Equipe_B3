package ca.ulaval.glo4002.rest.requests;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.utils.CustomDateParser;

public class InterventionRequest {

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
	private String type;
	private String status;
	private int patient;
	
	public InterventionRequest(JSONObject jsonRequest) throws JSONException, ParseException{
		this.description = jsonRequest.getString(DESCRIPTION_PARAMETER);
		this.surgeon = jsonRequest.getInt(SURGEON_PARAMETER);
		this.date = CustomDateParser.parseDate(jsonRequest.getString(DATE_PARAMETER));
		this.room = jsonRequest.getString(ROOM_PARAMETER);
		this.type = jsonRequest.getString(TYPE_PARAMETER);
		this.status = jsonRequest.optString(STATUS_PARAMETER);
		this.patient = jsonRequest.getInt(PATIENT_PARAMETER);
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public int getSurgeon(){
		return this.surgeon;
	}
	
	public Date getDate(){
		return this.date;
	}
	
	public String getRoom(){
		return this.room;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public int getPatient(){
		return this.patient;
	}
	
	public void validateType(){
		if(this.type != "OEIL" || this.type != "COEUR" || this.type != "ONCOLOGIQUE" || this.type != "AUTRE"){
			throw new IllegalArgumentException("The intervention type is invalid.");
		}
	}
	
	public void validateStatus(){
		if(this.status != "PLANIFIEE" || this.status != "EN_COURS" || this.status != "TERMINEE" || this.status != "ANNULEE" || this.status != "REPORTEE"){
			throw new IllegalArgumentException("The intervention status is invalid.");
		}
	}
	
	PatientRepository patientRepository;
	
	public boolean validatePatientId(){
		if(!patientRepository.equals(patient)){
			return false;
		}
		else{
			return true;
		}
	}
}
