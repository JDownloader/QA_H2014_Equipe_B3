package ca.ulaval.glo4002.uats.steps;

import org.jbehave.core.annotations.Given;

import ca.ulaval.glo4002.uats.steps.contexts.ThreadLocalContext;

public class PatientSteps {
	private static final Integer DEFAULT_PATIENT_ID = 1;
	private static final Integer NON_EXISTING_PATIENT_ID = 6;
	public static final String PATIENT_ID_KEY = "patient_id_key";
	
	@Given("un patient existant")
	public void createValidPatient() {
		//Patients already created by demo repository filling
		ThreadLocalContext.putObject(PATIENT_ID_KEY, DEFAULT_PATIENT_ID);
	}
	
	@Given("un patient inexistant")
	public void setNonExistingPatientID() {
		//Patients already created by demo repository filling
		ThreadLocalContext.putObject(PATIENT_ID_KEY, NON_EXISTING_PATIENT_ID);
	}
}
