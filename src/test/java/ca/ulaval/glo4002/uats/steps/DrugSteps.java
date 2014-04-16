package ca.ulaval.glo4002.uats.steps;

import static com.jayway.restassured.RestAssured.*;

import java.text.ParseException;

import javax.ws.rs.core.Response.Status;

import org.jbehave.core.annotations.*;
import org.json.JSONObject;
import org.junit.Assert;

import ca.ulaval.glo4002.uats.runners.JettyTestRunner;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class DrugSteps {

	private static final String DRUG_NAME_PARAMETER = "nom";

	private static final String DRUG_NAME_VALUE_WITHOUT_ENOUGH_CHARACTER = "AD";
	private static final String DRUG_NAME_VALUE_WITH_ENOUGH_CHARACTER = "EMCYT";
	private static final String DRUG_DESCRIPTION_VALUE = "FOR COMMERCIAL ";

	private Response response;
	JSONObject drugSearchJson;

	@BeforeScenario
	public void clearResults() throws ParseException {
		response = null;
	}

	@When("je cherche un médicaments avec moins de caractères que la limite requise")
	public void SearchDrugWithoutEnoughCharacters() {
		drugSearchJson = new JSONObject();
		drugSearchJson.put(DRUG_NAME_PARAMETER, DRUG_NAME_VALUE_WITHOUT_ENOUGH_CHARACTER);
		response = given().port(JettyTestRunner.JETTY_TEST_PORT).body(drugSearchJson.toString()).contentType(ContentType.JSON).when()
				.post(String.format("medicaments/dins/"));
	}

	// TODO @Then en double ,avec prescription, ajouté un * pour le moment pour
	// faire une difference. Faire une classe ExceptionSteps.java ?
	@Then("une erreur est retournée*")
	public void returnsAnError() {
		response.then().statusCode(Status.BAD_REQUEST.getStatusCode());
	}

	@Then("cette erreur a le code \"DIN001\"")
	public void errorIsDIN001() {
		String bodyString = response.getBody().asString();
		JSONObject jsonObject = new JSONObject(bodyString);
		Assert.assertEquals("DIN001", jsonObject.get("code"));
	}

	@When("je cherche des médicaments avec un mot-clé qui se retrouve dans quelques noms de médicaments")
	public void searchDrugByName() {
		drugSearchJson = new JSONObject();
		drugSearchJson.put(DRUG_NAME_PARAMETER, DRUG_NAME_VALUE_WITH_ENOUGH_CHARACTER);
		response = given().port(JettyTestRunner.JETTY_TEST_PORT).body(drugSearchJson.toString()).contentType(ContentType.JSON).when()
				.post(String.format("medicaments/dins/"));
	}

	// TODO Verifie juste que ca retourne quelque chose, non ce qui est
	// retourner. Pas capable de voir ce qui est dans le Json, seulement ce qui
	// est dans l'Array contenant le JSON
	@Then("la liste de médicaments retournée contient ceux-ci")
	public void returnDrugList() {
		byte[] bodyArray = response.getBody().asByteArray();
		JSONObject jsonObject = new JSONObject(bodyArray);
		Assert.assertEquals("{}", jsonObject.toString());
	}

	@When("je cherche des médicaments avec un mot-clé qui se retrouve dans quelques descriptions de médicaments")
	public void searchDrugByDescription() {
		drugSearchJson = new JSONObject();
		drugSearchJson.put(DRUG_NAME_PARAMETER, DRUG_DESCRIPTION_VALUE);
		response = given().port(JettyTestRunner.JETTY_TEST_PORT).body(drugSearchJson.toString()).contentType(ContentType.JSON).when()
				.post(String.format("medicaments/dins/"));
	}
}
