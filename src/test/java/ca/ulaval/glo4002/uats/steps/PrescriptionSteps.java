package ca.ulaval.glo4002.uats.steps;

import static com.jayway.restassured.RestAssured.*;

import java.text.ParseException;

import javax.ws.rs.core.Response.Status;

import org.jbehave.core.annotations.*;
import org.json.JSONObject;

import ca.ulaval.glo4002.uats.runners.JettyTestRunner;
import ca.ulaval.glo4002.uats.steps.contexts.ThreadLocalContext;

import com.jayway.restassured.response.Response;

public class PrescriptionSteps {
	private static final String DATE_PARAMETER = "date";
	private static final String RENEWALS_PARAMETER = "renouvellements";
	private static final String DRUG_NAME_PARAMETER = "nom";
	private static final String STAFF_MEMBER_PARAMETER = "intervenant";
	private static final String DIN_PARAMETER = "din";
	
	private static final String SAMPLE_DATE = "2001-07-04T12:08:56";
	private static final int SAMPLE_RENEWALS = 2;
	private static final int SAMPLE_INVALID_RENEWALS = -1;
	private static final String SAMPLE_DRUG_NAME = "drug_name";
	private static final int SAMPLE_STAFF_MEMBER = 3;
	private static final String ROSALIAC_UV_RICHE_DIN = "02330857";
	private static final String STANHEXIDINE_DIN = "01938983";
	private static final String STANHEXIDINE_NAME = "STANHEXIDINE";
	private static final String NATURALYTE_H201_DIN = "02330024";
	private static final String SAMPLE_NON_EXISTING_DIN = "025555";
	private static final String WHITE_SPACE = " ";

	private Response response = null;
	JSONObject prescriptionJson;

	@BeforeScenario
	public void init() throws ParseException {
		response = null;
		prescriptionJson = new JSONObject();
	}

	@Given("une prescription avec des données manquantes")
	public void createPrescriptionWithMissingValues() {
		createDefaultPrescriptionJsonObject();
		prescriptionJson.remove(RENEWALS_PARAMETER);
	}
	
	@Given("une prescription avec des données invalides")
	public void createPrescriptionWithInvalidValues() {
		createDefaultPrescriptionJsonObject();
		prescriptionJson.put(RENEWALS_PARAMETER, SAMPLE_INVALID_RENEWALS);
	}
	
	@Given("une prescription avec un champ obligatoire qui ne contient que des espaces")
	public void createPrescriptionWithWhiteSpaceField() {
		createDefaultPrescriptionJsonObject();
		prescriptionJson.put(STAFF_MEMBER_PARAMETER, WHITE_SPACE);
	}
	
	@Given("une prescription valide avec DIN")
	public void createValidPrescriptionWithDin() {
		createDefaultPrescriptionJsonObject();
		prescriptionJson.remove(DRUG_NAME_PARAMETER);
		prescriptionJson.put(DIN_PARAMETER, ROSALIAC_UV_RICHE_DIN);
	}
	
	@Given("que ce DIN n'existe pas")
	public void setPrescriptionWithNonExistingDin() {
		prescriptionJson.put(DIN_PARAMETER, SAMPLE_NON_EXISTING_DIN);
	}
	
	@Given("une prescription valide avec nom de médicament")
	public void createValidPrescriptionWithDrugName() {
		createDefaultPrescriptionJsonObject();
		prescriptionJson.remove(DIN_PARAMETER);
		prescriptionJson.put(DRUG_NAME_PARAMETER, SAMPLE_DRUG_NAME);
	}
	
	@Given("une prescription avec DIN et un nom de médicament")
	public void createValidPrescriptionWithDinAndDrugName() {
		createDefaultPrescriptionJsonObject();
		prescriptionJson.put(DRUG_NAME_PARAMETER, SAMPLE_DRUG_NAME);
	}
	
	@Given("une prescription associée à ce patient")
	public void linkPrescriptionToPatient() {
		createDefaultPrescriptionJsonObject();
		createPrescription();
	}
	
	@When("j'ajoute une prescription pour laquelle il y a une interaction")
	public void addPrescriptionWithInteraction() {
		createDefaultPrescriptionJsonObject();
		prescriptionJson.put(DIN_PARAMETER, STANHEXIDINE_DIN);
		createPrescription();
	}
	
	@When("j'ajoute une prescription pour laquelle il n'y a pas d'interaction")
	public void addPrescriptionWithNoInteraction() {
		createDefaultPrescriptionJsonObject();
		prescriptionJson.put(DIN_PARAMETER, NATURALYTE_H201_DIN);
		createPrescription();
	}
	
	@When("j'ajoute une prescription avec nom de médicament pour laquelle il y a une interaction")
	public void addPrescriptionWithInteractiveDrugName() {
		createDefaultPrescriptionJsonObject();
		
		prescriptionJson.remove(DIN_PARAMETER);
		prescriptionJson.put(DRUG_NAME_PARAMETER, STANHEXIDINE_NAME);
		
		createPrescription();
	}
	
	@When("j'ajoute cette prescription au dossier du patient")
	public void createPrescription() {
		Integer patientId = (Integer) ThreadLocalContext.getObject(PatientSteps.LAST_PATIENT_ID_KEY);
		
		response = given().port(JettyTestRunner.JETTY_TEST_PORT)
				.body(prescriptionJson.toString())
				.contentType("application/json; charset=UTF-8")
				.when()
				.post(String.format("patient/%d/prescriptions/", patientId));
		
		ThreadLocalContext.putObject(HttpResponseSteps.LAST_RESPONSE_OBJECT_KEY, response);
	}
	
	@Then("cette prescription est conservée")
	public void prescriptionIsSaved() {
		response.then().
			statusCode(Status.CREATED.getStatusCode());
	}
	
	private void createDefaultPrescriptionJsonObject() {
		prescriptionJson.put(DATE_PARAMETER, SAMPLE_DATE);
		prescriptionJson.put(RENEWALS_PARAMETER, SAMPLE_RENEWALS);
		prescriptionJson.put(DIN_PARAMETER, ROSALIAC_UV_RICHE_DIN);
		prescriptionJson.put(STAFF_MEMBER_PARAMETER, SAMPLE_STAFF_MEMBER);
	}
}
