package ca.ulaval.glo4002.uats.steps;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response.Status;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.json.JSONObject;

import ca.ulaval.glo4002.domain.intervention.InterventionStatus;
import ca.ulaval.glo4002.domain.intervention.InterventionType;
import ca.ulaval.glo4002.uats.runners.JettyTestRunner;
import ca.ulaval.glo4002.uats.steps.contexts.ThreadLocalContext;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.response.Response;

public class InterventionSteps {
	public static final String INTERVENTION_ID_KEY = "intervention_id_key";
	
	private static final String DESCRIPTION_PARAMETER = "description";
	private static final String SURGEON_PARAMETER = "chirurgien";
	private static final String ROOM_PARAMETER = "salle";
	private static final String DATE_PARAMETER = "date";
	private static final String TYPE_PARAMETER = "type";
	private static final String STATUS_PARAMETER = "statut";
	private static final String PATIENT_PARAMETER = "patient";

	private static final String DESCRIPTION_VALUE = "Cataracte à l'oeil gauche";
	private static final String SURGEON_VALUE = "101224";
	private static final String ROOM_VALUE = "blocB";
	private static final String DATE_VALUE = "2001-07-04T12:08:56";
	private static final String TYPE_VALUE = InterventionType.OTHER.getValue();
	private static final String STATUS_VALUE = InterventionStatus.IN_PROGRESS.getValue();
	private static final String INVALID_STATUS_VALUE = "invalide";
	private static final String INVALID_PATIENT_VALUE = "500";


	private Response response;
	JSONObject interventionJson;

	@BeforeScenario
	public void init() throws ParseException {
		response = null;
		interventionJson = new JSONObject();
	}

	@Given("une intervention avec des informations valide")
	public void createValidIntervention() {
		createDefaultInterventionJsonObject();
	}
	
	@Given("une intervention avec des informations manquantes")
	public void createInterventionWithMissingInformation() {
		createDefaultInterventionJsonObject();
		interventionJson.remove(DATE_PARAMETER);
	}

	@Given("une intervention valide avec un statut indéterminé")
	public void createInterventionWithNonExistingStatus() {
		createDefaultInterventionJsonObject();
		interventionJson.remove(STATUS_PARAMETER);
	}

	@Given("une intervention avec un patient inexistant")
	public void createInterventionWithNonExistingPatient() {
		createDefaultInterventionJsonObject();
		interventionJson.put(PATIENT_PARAMETER, INVALID_PATIENT_VALUE);
	}
	
	@Given("une intervention avec un statut invalide")
	public void createInterventionWithInvalidStatus() {
		createDefaultInterventionJsonObject();
		interventionJson.put(STATUS_PARAMETER, INVALID_STATUS_VALUE);
	}
	
	@Given("Given une intervention interdisant les instruments anonymes")
	public void createInterventioRequiringANonymousInstruments() {
		createDefaultInterventionJsonObject();
		interventionJson.put(TYPE_PARAMETER, InterventionType.EYE.getValue());
	}

	@When("j'ajoute cette intervention au dossier du patient")
	public void addIntervention() {
		response = given().port(JettyTestRunner.JETTY_TEST_PORT) //TODO: Make method shared
				.body(interventionJson.toString())
				.contentType("application/json; charset=UTF-8")
				.when()
				.post("interventions/");

		int interventionId = parseInterventionIdFromLocationURIString(response.getHeader("location"));
		ThreadLocalContext.putObject(InterventionSteps.INTERVENTION_ID_KEY, interventionId);
		ThreadLocalContext.putObject(HttpResponseSteps.RESPONSE_OBJECT_KEY, response);
	}
	
	private int parseInterventionIdFromLocationURIString(String locationURI) {
		Pattern pattern = Pattern.compile("/interventions/(\\d)+");
		Matcher matcher = pattern.matcher(locationURI);
		return Integer.parseInt(matcher.group(1));
	}

	@Then("cette intervention est conservée")
	public void interventionIsSaved() {
		response.then().statusCode(Status.CREATED.getStatusCode());
	}
	
	private void createDefaultInterventionJsonObject() {
		interventionJson.put(DESCRIPTION_PARAMETER, DESCRIPTION_VALUE);
		interventionJson.put(SURGEON_PARAMETER, SURGEON_VALUE);
		interventionJson.put(DATE_PARAMETER, DATE_VALUE);
		interventionJson.put(ROOM_PARAMETER, ROOM_VALUE);
		interventionJson.put(TYPE_PARAMETER, TYPE_VALUE);
		interventionJson.put(STATUS_PARAMETER, STATUS_VALUE);
		interventionJson.put(PATIENT_PARAMETER, ThreadLocalContext.getObject(PatientSteps.PATIENT_ID_KEY));
	}
}
