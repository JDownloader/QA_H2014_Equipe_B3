package ca.ulaval.glo4002.uats.steps;

import static com.jayway.restassured.RestAssured.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jbehave.core.annotations.*;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.Assert.*;
import ca.ulaval.glo4002.uats.runners.JettyTestRunner;
import ca.ulaval.glo4002.uats.steps.contexts.ThreadLocalContext;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class DrugSteps {
	private static final String DIN_PARAMETER = "din";
	private static final String KEYWORD_PARAMETER = "nom";

	private static final String KEYWORD_WITH_TWO_CHARACTERS = "AB";
	private static final String SHARED_DRUG_NAME_KEYWORD = "ROSALIAC";
	private static final String SHARED_DESCRIPTION_KEYWORD = "0.8MG/HOUR";
	private static final String WILDCARD_KEYWORD = "ROSAL AC";
	
	private static final String ROSALIAC_UV_RICHE_DIN = "02330857";
	private static final String ROSALIAC_UV_LEGERE_DIN = "02330792";
	private static final String MYLAN_NITRO_PATCH_0_8_DIN = "02407477";
	private static final String NITRO_DUR_0_8_DIN = "02011271";
	
	private Response response;
	private ArrayList<String> expectedDins;
	JSONObject drugSearchJson;

	@BeforeScenario
	public void init() throws ParseException {
		response = null;
		drugSearchJson = new JSONObject();
	}

	@When("je cherche un médicaments avec moins de caractères que la limite requise")
	public void SearchUsingTwoCharacterKeyword() {
		drugSearchJson.put(KEYWORD_PARAMETER, KEYWORD_WITH_TWO_CHARACTERS);
		post(drugSearchJson);
	}

	@When("je cherche des médicaments avec un mot-clé qui se retrouve dans quelques noms de médicaments")
	public void searchUsingSharedDrugNameKeyword() {
		drugSearchJson.put(KEYWORD_PARAMETER, SHARED_DRUG_NAME_KEYWORD);
		post(drugSearchJson);
		expectedDins = new ArrayList<String>(Arrays.asList(ROSALIAC_UV_RICHE_DIN, ROSALIAC_UV_LEGERE_DIN));
	}
	
	@When("je cherche des médicaments avec un mot-clé qui se retrouve dans quelques descriptions de médicaments")
	public void searchUsingSharedDescriptionKeyword() {
		drugSearchJson.put(KEYWORD_PARAMETER, SHARED_DESCRIPTION_KEYWORD);
		post(drugSearchJson);
		expectedDins = new ArrayList<String>(Arrays.asList(MYLAN_NITRO_PATCH_0_8_DIN, NITRO_DUR_0_8_DIN));
	}
	
	@When("je cherche des médicaments avec un mot-clé qui contient un patron générique")
	public void searchDrugByDescription() {
		drugSearchJson.put(KEYWORD_PARAMETER, WILDCARD_KEYWORD);
		post(drugSearchJson);
		expectedDins = new ArrayList<String>(Arrays.asList(ROSALIAC_UV_RICHE_DIN, ROSALIAC_UV_LEGERE_DIN));
	}
	
	private void post(JSONObject jsonObject) {
		response = given().port(JettyTestRunner.JETTY_TEST_PORT)
				.body(drugSearchJson.toString())
				.contentType(ContentType.JSON).when()
				.post(String.format("medicaments/dins/"));
		
		ThreadLocalContext.putObject(HttpResponseSteps.RESPONSE_OBJECT_KEY, response);
	}

	@Then("la liste de médicaments retournée contient ceux-ci")
	public void returnDrugList() {
		String bodyString = response.getBody().asString();
		JSONArray actualDrugList = new JSONArray(bodyString);

		assertEquals(expectedDins.size(), actualDrugList.length());
		
		for (int i = 0; i < expectedDins.size(); i++) {
			String expectedDin = expectedDins.get(i);
			String actualDin = actualDrugList.getJSONObject(i).getString(DIN_PARAMETER);
			assertEquals(expectedDin, actualDin);
		}
	}
}
