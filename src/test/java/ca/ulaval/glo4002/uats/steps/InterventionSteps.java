package ca.ulaval.glo4002.uats.steps;

import java.text.ParseException;

import javax.ws.rs.core.Response.Status;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.json.JSONObject;

import ca.ulaval.glo4002.domain.intervention.InterventionStatus;
import ca.ulaval.glo4002.domain.intervention.InterventionType;
import ca.ulaval.glo4002.uats.steps.contexts.ThreadLocalContext;
import ca.ulaval.glo4002.uats.steps.utils.HttpLocationParser;

import com.jayway.restassured.response.Response;

public class InterventionSteps {
	public static final String LAST_INTERVENTION_ID_KEY = "intervention_id_key";
	
	private static final String DESCRIPTION_PARAMETER = "description";
	private static final String SURGEON_PARAMETER = "chirurgien";
	private static final String ROOM_PARAMETER = "salle";
	private static final String DATE_PARAMETER = "date";
	private static final String TYPE_PARAMETER = "type";
	private static final String STATUS_PARAMETER = "statut";
	private static final String PATIENT_PARAMETER = "patient";

	private static final String SAMPLE_DESCRIPTION = "Cataracte à l'oeil gauche";
	private static final String SAMPLE_SURGEON = "101224";
	private static final String SAMPLE_ROOM = "blocB";
	private static final String SAMPLE_DATE = "2001-07-04T12:08:56";
	private static final String SAMPLE_TYPE = InterventionType.OTHER.getValue();
	private static final String SAMPLE_STATUS = InterventionStatus.IN_PROGRESS.getValue();
	private static final String SAMPLE_INVALID_STATUS = "invalide";

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
	@Composite(steps = {
			"Given un patient inexistant"})
	public void createInterventionWithNonExistingPatient() {
		createDefaultInterventionJsonObject();
	}
	
	@Given("une intervention avec un statut invalide")
	public void createInterventionWithInvalidStatus() {
		createDefaultInterventionJsonObject();
		interventionJson.put(STATUS_PARAMETER, SAMPLE_INVALID_STATUS);
	}
	
	@Given("une intervention autorisant les instruments anonymes")
	public void createInterventioAuthorizingAnonymousInstruments() {
		createDefaultInterventionJsonObject();
		interventionJson.put(TYPE_PARAMETER, InterventionType.ONCOLOGIC.getValue());
	}
	
	@Given("une intervention interdisant les instruments anonymes")
	public void createInterventioProhibitingAnonymousInstruments() {
		createDefaultInterventionJsonObject();
		interventionJson.put(TYPE_PARAMETER, InterventionType.EYE.getValue());
	}
	
	@When("j'ajoute cette intervention au dossier d'un patient")
	@Composite(steps = {
			"Given un patient existant",
			"When j'ajoute cette intervention au dossier de ce patient"})
	public void addInterventionToExistingPatient() {
		//Nothing left do to here after composite steps have been run
	}
	
	@When("j'ajoute cette intervention au dossier de ce patient")
	public void createIntervention() {
		interventionJson.put(PATIENT_PARAMETER, ThreadLocalContext.getObject(PatientSteps.LAST_PATIENT_ID_KEY));
		
		response = HttpResponseSteps.getDefaultRequestSepcification(interventionJson)
				.post("interventions/");
		
		saveInterventionCreationResponseContext();
	}

	private void saveInterventionCreationResponseContext() {
		try {
			Integer interventionId = HttpLocationParser.parseInterventionIdFromHeader(response);
			ThreadLocalContext.putObject(InterventionSteps.LAST_INTERVENTION_ID_KEY, interventionId);
		} catch (IllegalArgumentException e) {
			//Intervention ID does not need to be saved into context if it is invalid.
		}
		ThreadLocalContext.putObject(HttpResponseSteps.LAST_RESPONSE_OBJECT_KEY, response);
	}

	@Then("cette intervention est conservée")
	public void interventionIsSaved() {
		response.then().statusCode(Status.CREATED.getStatusCode());
	}
	
	private void createDefaultInterventionJsonObject() {
		interventionJson.put(DESCRIPTION_PARAMETER, SAMPLE_DESCRIPTION);
		interventionJson.put(SURGEON_PARAMETER, SAMPLE_SURGEON);
		interventionJson.put(DATE_PARAMETER, SAMPLE_DATE);
		interventionJson.put(ROOM_PARAMETER, SAMPLE_ROOM);
		interventionJson.put(TYPE_PARAMETER, SAMPLE_TYPE);
		interventionJson.put(STATUS_PARAMETER, SAMPLE_STATUS);
	}
}
