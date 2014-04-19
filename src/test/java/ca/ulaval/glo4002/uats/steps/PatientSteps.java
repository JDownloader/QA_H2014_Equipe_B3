package ca.ulaval.glo4002.uats.steps;

import org.jbehave.core.annotations.Given;

import ca.ulaval.glo4002.uats.steps.contexts.ThreadLocalContext;

public class PatientSteps {
	private static final int NEW_PATIENT_ID = 6;
	public static final String PATIENT_ID_KEY = "patient_value";
	
	@Given("un patient existant")
	public void setExistingPatientNumber() {
		//Patient already created by demo repository filling
		ThreadLocalContext.putObject(PATIENT_ID_KEY, 1);
	}
	
	@Given("un patient inexistant")
	public void setNonExistingPatientNumber() {
		//Patient already created by demo repository filling
		ThreadLocalContext.putObject(PATIENT_ID_KEY, NEW_PATIENT_ID);
	}
}
