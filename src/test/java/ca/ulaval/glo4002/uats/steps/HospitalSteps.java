package ca.ulaval.glo4002.uats.steps;

import java.text.ParseException;

import javax.ws.rs.core.Response.Status;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.json.JSONObject;
import org.junit.Assert;

import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;
import ca.ulaval.glo4002.uats.runners.JettyTestRunner;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.response.Response;

public class HospitalSteps {
	private static final String DATE_PARAMETER = "date";
	private static final String RENEWALS_PARAMETER = "renouvellements";
	private static final String DRUG_NAME_PARAMETER = "nom";
	private static final String STAFF_MEMBER_PARAMETER = "intervenant";
	private static final String DIN_PARAMETER = "din";
	
	private static final String DATE_VALUE = "2001-07-04T12:08:56";
	private static final int RENEWALS_VALUE = 2;
	private static final String DRUG_NAME_VALUE = "drug_name";
	private static final int STAFF_MEMBER_VALUE = 3;
	private static final Din DIN_VALUE = new Din("098423");
	private static final int PATIENT_NUMBER_VALUE = 3;

	private Response response;
	public PrescriptionCreationDTO prescriptionCreationDTO;

	@BeforeScenario
	public void clearResults() throws ParseException {
		response = null;
	}

	@Given("un patient existant")
	public void createPatient() {
		//Patient already created by demo repository filling
	}

	@Given("une prescription valide avec des données manquantes")
	public void createValidPrescriptionWithMissingValues() {
		//TODO: ???
	}

	@When("j'ajoute cette prescription au dossier du patient")
	public void addPrescription() {
		response = given().port(JettyTestRunner.JETTY_TEST_PORT)
				.parameters(DATE_PARAMETER, DATE_VALUE)
				.parameters(DRUG_NAME_PARAMETER, DRUG_NAME_VALUE)
				.parameters(STAFF_MEMBER_PARAMETER, STAFF_MEMBER_VALUE)
				.when().get(String.format("patient/%d/prescriptions/", PATIENT_NUMBER_VALUE));
	}
	
	@Then("une erreur est retournée")
	public void returnsAnError() {
		response.then().
			statusCode(Status.BAD_REQUEST.getStatusCode());
	}
	
	@Then("cette erreur a le code \"PRES001\"")
	public void errorIsPRES001() {
		String bodyString = response.body().toString();
		JSONObject jsonObject = new JSONObject(bodyString);
		Assert.assertEquals("PRES001", jsonObject.get("code"));
	}
	
}
