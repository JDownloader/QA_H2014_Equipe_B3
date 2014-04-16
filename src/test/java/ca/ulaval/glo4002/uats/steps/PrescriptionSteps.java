package ca.ulaval.glo4002.uats.steps;

import java.text.ParseException;

import javax.ws.rs.core.Response.Status;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.json.JSONObject;

import ca.ulaval.glo4002.uats.runners.JettyTestRunner;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class PrescriptionSteps {
	private static final String DATE_PARAMETER = "date";
	private static final String RENEWALS_PARAMETER = "renouvellements";
	private static final String DRUG_NAME_PARAMETER = "nom";
	private static final String STAFF_MEMBER_PARAMETER = "intervenant";
	private static final String DIN_PARAMETER = "din";
	
	private static final String DATE_VALUE = "2001-07-04T12:08:56";
	private static final int RENEWALS_VALUE = 2;
	private static final String DRUG_NAME_VALUE = "drug_name";
	private static final int STAFF_MEMBER_VALUE = 3;
	private static final String DIN_VALUE = "02240541";
	private static final String NON_EXISTING_DIN_VALUE = "025555";
	private static final int PATIENT_NUMBER_VALUE = 2;

	private Response response = null;
	JSONObject prescriptionJson;

	@BeforeScenario
	public void init() throws ParseException {
		response = null;
		prescriptionJson = new JSONObject();
	}

	@Given("une prescription valide avec des données manquantes")
	public void createValidPrescriptionWithMissingValues() {
		prescriptionJson.put(DATE_PARAMETER, DATE_VALUE);
		prescriptionJson.put(DRUG_NAME_PARAMETER, DRUG_NAME_VALUE);
		prescriptionJson.put(STAFF_MEMBER_PARAMETER, STAFF_MEMBER_VALUE);
	}
	
	@Given("une prescription valide avec DIN")
	public void createValidPrescriptionWithDin() {
		prescriptionJson.put(DATE_PARAMETER, DATE_VALUE);
		prescriptionJson.put(RENEWALS_PARAMETER , RENEWALS_VALUE);
		prescriptionJson.put(DIN_PARAMETER, DIN_VALUE);
		prescriptionJson.put(STAFF_MEMBER_PARAMETER, STAFF_MEMBER_VALUE);
	}
	
	@Given("que ce DIN n'existe pas")
	public void setPrescriptionWithNonExistingDin() {
		prescriptionJson.put(DIN_PARAMETER, NON_EXISTING_DIN_VALUE);
	}
	
	@Given("une prescription valide avec nom de médicament")
	public void createValidPrescriptionWithDrugName() {
		prescriptionJson.put(DATE_PARAMETER, DATE_VALUE);
		prescriptionJson.put(RENEWALS_PARAMETER , RENEWALS_VALUE);
		prescriptionJson.put(DRUG_NAME_PARAMETER, DRUG_NAME_VALUE);
		prescriptionJson.put(STAFF_MEMBER_PARAMETER, STAFF_MEMBER_VALUE);
	}

	@When("j'ajoute cette prescription au dossier du patient")
	public void addPrescription() {
		response = given().port(JettyTestRunner.JETTY_TEST_PORT)
				.body(prescriptionJson.toString())
				.contentType(ContentType.JSON)
				.when().post(String.format("patient/%d/prescriptions/", PATIENT_NUMBER_VALUE));
		
		ThreadLocalMap.putObject(HttpResponseSteps.RESPONSE_OBJECT_KEY, response);
	}
	
	@Then("cette prescription est conservée")
	public void prescriptionIsSaved() {
		response.then().
			statusCode(Status.CREATED.getStatusCode());
	}
}
