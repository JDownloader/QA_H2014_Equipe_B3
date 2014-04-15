package ca.ulaval.glo4002.uats.steps;

import java.text.ParseException;

import javax.ws.rs.core.Response.Status;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.json.JSONObject;
import org.junit.Assert;

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
	private static final String DIN_VALUE = "098423";
	private static final int PATIENT_NUMBER_VALUE = 3;

	private Response response;
	JSONObject prescriptionJson;

	@BeforeScenario
	public void clearResults() throws ParseException {
		response = null;
	}

	@Given("une prescription valide avec des données manquantes")
	public void createValidPrescriptionWithMissingValues() {
		prescriptionJson = new JSONObject();
		prescriptionJson.put(DATE_PARAMETER, DATE_VALUE);
		prescriptionJson.put(DRUG_NAME_PARAMETER, DRUG_NAME_VALUE);
		prescriptionJson.put(STAFF_MEMBER_PARAMETER, STAFF_MEMBER_VALUE);
	}

	@When("j'ajoute cette prescription au dossier du patient")
	public void addPrescription() {
		response = given().port(JettyTestRunner.JETTY_TEST_PORT)
				.body(prescriptionJson.toString())
				.contentType(ContentType.JSON)
				.when().post(String.format("patient/%d/prescriptions/", PATIENT_NUMBER_VALUE));
	}
	
	@Then("une erreur est retournée")
	public void returnsAnError() {
		response.then().
			statusCode(Status.BAD_REQUEST.getStatusCode());
	}
	
	@Then("cette erreur a le code \"PRES001\"")
	public void errorIsPRES001() {
		String bodyString = response.getBody().asString();
		JSONObject jsonObject = new JSONObject(bodyString);
		Assert.assertEquals("PRES001", jsonObject.get("code"));
	}
	
}
