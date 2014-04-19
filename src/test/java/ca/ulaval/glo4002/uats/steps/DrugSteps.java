package ca.ulaval.glo4002.uats.steps;

import static com.jayway.restassured.RestAssured.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jbehave.core.annotations.*;
import org.json.JSONArray;

import static org.junit.Assert.*;
import ca.ulaval.glo4002.uats.runners.JettyTestRunner;
import ca.ulaval.glo4002.uats.steps.contexts.ThreadLocalContext;

import com.jayway.restassured.response.Response;

public class DrugSteps {
	private static final String DIN_PARAMETER = "din";
	
	private static final String KEYWORD_WITH_TWO_CHARACTERS = "AB";
	private static final String DRUG_NAME_KEYWORD = "ROSALIAC";
	private static final String NON_EXISTING_DRUG_NAME_KEYWORD = "AHSDSS";
	private static final String DESCRIPTION_KEYWORD = "0.8MG/HOUR";
	private static final String NON_EXISTING_DESCRIPTION_KEYWORD = "OIJFS";
	private static final String WILDCARD_KEYWORD = "ROSAL AC";
	
	private static final String ROSALIAC_UV_RICHE_DIN = "02330857";
	private static final String ROSALIAC_UV_LEGERE_DIN = "02330792";
	private static final String MYLAN_NITRO_PATCH_0_8_DIN = "02407477";
	private static final String NITRO_DUR_0_8_DIN = "02011271";
	
	private static final int EMPTY_SIZE = 0;
	
	private Response response;
	private ArrayList<String> expectedDins;
	private String drugName;

	@BeforeScenario
	public void init() throws ParseException {
		response = null;
		drugName = "";
	}

	@When("je cherche un médicaments avec moins de caractères que la limite requise")
	public void SearchUsingTwoCharacterKeyword() {
		drugName = KEYWORD_WITH_TWO_CHARACTERS;
		doHttpGet();
	}

	@When("je cherche des médicaments avec un mot-clé qui se retrouve dans quelques noms de médicaments")
	public void searchUsingDrugNameKeyword() {
		drugName = DRUG_NAME_KEYWORD;
		doHttpGet();
		expectedDins = new ArrayList<String>(Arrays.asList(ROSALIAC_UV_RICHE_DIN, ROSALIAC_UV_LEGERE_DIN));
	}
	
	@When("je cherche des médicaments avec un mot-clé qui se retrouve dans quelques descriptions de médicaments")
	public void searchUsingDescriptionKeyword() {
		drugName = DESCRIPTION_KEYWORD;
		doHttpGet();
		expectedDins = new ArrayList<String>(Arrays.asList(MYLAN_NITRO_PATCH_0_8_DIN, NITRO_DUR_0_8_DIN));
	}
	
	@When("je cherche des médicaments avec un mot-clé qui ne se retrouve pas dans aucun nom de médicaments")
	public void searchUsingNonExistingDrugNameKeyword() {
		drugName = NON_EXISTING_DRUG_NAME_KEYWORD;
		doHttpGet();
	}
	
	@When("je cherche des médicaments avec un mot-clé qui ne se retrouve pas dans aucune description de médicaments")
	public void searchUsingNonExistinDescriptionKeyword() {
		drugName = NON_EXISTING_DESCRIPTION_KEYWORD;
		doHttpGet();
	}
	
	@When("je cherche des médicaments avec un mot-clé qui contient un patron générique")
	public void searchDrugByDescription() {
		drugName = WILDCARD_KEYWORD;
		doHttpGet();
		expectedDins = new ArrayList<String>(Arrays.asList(ROSALIAC_UV_RICHE_DIN, ROSALIAC_UV_LEGERE_DIN));
	}
	
	private void doHttpGet() {
		response = given().port(JettyTestRunner.JETTY_TEST_PORT)
				.when()
				.get(String.format("medicaments/dins/?nom=%s", drugName));
		
		ThreadLocalContext.putObject(HttpResponseSteps.RESPONSE_OBJECT_KEY, response);
	}

	@Then("la liste de médicaments retournée contient ceux-ci")
	public void isReturnedDrugListMatching() {
		String bodyString = response.getBody().asString();
		JSONArray actualDrugList = new JSONArray(bodyString);

		assertEquals(expectedDins.size(), actualDrugList.length());
		
		for (int i = 0; i < expectedDins.size(); i++) {
			String expectedDin = expectedDins.get(i);
			String actualDin = actualDrugList.getJSONObject(i).getString(DIN_PARAMETER);
			assertEquals(expectedDin, actualDin);
		}
	}
	
	@Then("la liste de médicaments retournée est vide")
	public void isReturnedDrugListEmpty() {
		String bodyString = response.getBody().asString();
		JSONArray actualDrugList = new JSONArray(bodyString);

		assertEquals(EMPTY_SIZE, actualDrugList.length());
	}
}
