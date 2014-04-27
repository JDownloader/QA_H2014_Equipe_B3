package ca.ulaval.glo4002.uats.steps;

import org.jbehave.core.annotations.Given;

import ca.ulaval.glo4002.uats.steps.contexts.ThreadLocalContext;

public class PatientSteps {
	private static final int NEW_PATIENT_ID = 6;
	public static final String PATIENT_ID_KEY = "patient_id_key";
	
	@Given("un patient existant")
	public void createValidPatient() {
		//Patients already created by demo repository filling
		ThreadLocalContext.putObject(PATIENT_ID_KEY, 1);
	}
	
	@Given("un patient inexistant")
	public void setNonExistingPatientID() {
		//Patients already created by demo repository filling
		ThreadLocalContext.putObject(PATIENT_ID_KEY, NEW_PATIENT_ID);
	}
}
