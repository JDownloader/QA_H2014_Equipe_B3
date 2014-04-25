package ca.ulaval.glo4002.uats.steps;

import java.text.ParseException;

import javax.ws.rs.core.Response.Status;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.json.JSONObject;

import ca.ulaval.glo4002.uats.runners.JettyTestRunner;
import ca.ulaval.glo4002.uats.steps.contexts.ThreadLocalContext;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.response.Response;

public class PrescriptionSteps {
	private static final String DATE_PARAMETER = "date";
	private static final String RENEWALS_PARAMETER = "renouvellements";
	private static final String DRUG_NAME_PARAMETER = "nom";
	private static final String STAFF_MEMBER_PARAMETER = "intervenant";
	private static final String DIN_PARAMETER = "din";
	
	private static final String DATE_VALUE = "2001-07-04T12:08:56";
	private static final int RENEWALS_VALUE = 2;
	private static final int INVALID_RENEWALS_VALUE = -1;
	private static final String DRUG_NAME_VALUE = "drug_name";
	private static final int STAFF_MEMBER_VALUE = 3;
	private static final String ROSALIAC_UV_RICHE_DIN = "02330857";
	private static final String NON_EXISTING_DIN_VALUE = "025555";
	private static final String WHITE_SPACE = " ";

	private Response response = null;
	JSONObject prescriptionJson;

	@BeforeScenario
	public void init() throws ParseException {
		response = null;
		prescriptionJson = new JSONObject();
		createDefaultPrescription();
	}
	
	private void createDefaultPrescription() {
		prescriptionJson.put(DATE_PARAMETER, DATE_VALUE);
		prescriptionJson.put(RENEWALS_PARAMETER, RENEWALS_VALUE);
		prescriptionJson.put(DIN_PARAMETER, ROSALIAC_UV_RICHE_DIN);
		prescriptionJson.put(STAFF_MEMBER_PARAMETER, STAFF_MEMBER_VALUE);
	}

	@Given("une prescription avec des données manquantes")
	public void createPrescriptionWithMissingValues() {
		prescriptionJson.remove(RENEWALS_PARAMETER);
	}
	
	@Given("une prescription avec des données invalides")
	public void createPrescriptionWithInvalidValues() {
		prescriptionJson.put(RENEWALS_PARAMETER, INVALID_RENEWALS_VALUE);
	}
	
	@Given("une prescription avec un champ obligatoire qui ne contient que des espaces")
	public void createPrescriptionWithWhiteSpaceField() {
		prescriptionJson.put(STAFF_MEMBER_PARAMETER, WHITE_SPACE);
	}
	
	@Given("une prescription valide avec DIN")
	public void createValidPrescriptionWithDin() {
		prescriptionJson.remove(DRUG_NAME_PARAMETER);
		prescriptionJson.put(DIN_PARAMETER, ROSALIAC_UV_RICHE_DIN);
	}
	
	@Given("que ce DIN n'existe pas")
	public void setPrescriptionWithNonExistingDin() {
		prescriptionJson.put(DIN_PARAMETER, NON_EXISTING_DIN_VALUE);
	}
	
	@Given("une prescription valide avec nom de médicament")
	public void createValidPrescriptionWithDrugName() {
		prescriptionJson.remove(DIN_PARAMETER);
		prescriptionJson.put(DRUG_NAME_PARAMETER, DRUG_NAME_VALUE);
	}
	
	@Given("une prescription avec DIN et un nom de médicament")
	public void createValidPrescriptionWithDinAndDrugName() {
		prescriptionJson.put(DRUG_NAME_PARAMETER, DRUG_NAME_VALUE);
	}

	@When("j'ajoute cette prescription au dossier du patient")
	public void addPrescription() {
		Integer patientId = (Integer) ThreadLocalContext.getObject(PatientSteps.PATIENT_ID_KEY);
		
		response = given().port(JettyTestRunner.JETTY_TEST_PORT)
				.body(prescriptionJson.toString())
				.contentType("application/json; charset=UTF-8")
				.when()
				.post(String.format("patient/%d/prescriptions/", patientId));
		
		ThreadLocalContext.putObject(HttpResponseSteps.RESPONSE_OBJECT_KEY, response);
	}
	
	@Then("cette prescription est conservée")
	public void prescriptionIsSaved() {
		response.then().
			statusCode(Status.CREATED.getStatusCode());
	}
}
