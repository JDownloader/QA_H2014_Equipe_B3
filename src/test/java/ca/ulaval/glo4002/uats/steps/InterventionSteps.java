package ca.ulaval.glo4002.uats.steps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.ws.rs.core.Response.Status;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.json.JSONObject;

import ca.ulaval.glo4002.domain.intervention.InterventionStatus;
import ca.ulaval.glo4002.domain.intervention.InterventionType;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;
import ca.ulaval.glo4002.uats.runners.JettyTestRunner;
import ca.ulaval.glo4002.uats.steps.contexts.ThreadLocalContext;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class InterventionSteps {
	
	private static final String DESCRIPTION_VALUE = "Cataracte à l'oeil gauche";
	private static final Surgeon SURGEON_VALUE = new Surgeon("101224");
	private static final String ROOM_VALUE = "blocB";
	private static final InterventionType TYPE_VALUE = InterventionType.EYE;
	private static final InterventionStatus STATUS_VALUE = InterventionStatus.IN_PROGRESS;
	//private static final InterventionStatus INVALID_STATUS_VALUE = InterventionStatus.fromString("INVALIDE");
	private static final int PATIENT_VALUE = 1;
	
	private static final SimpleDateFormat customFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CANADA);	
	private static Date DATE_VALUE;
	
	private InterventionCreationDTO interventionCreationDTO = new InterventionCreationDTO();
	
	private Response response = null;
	JSONObject interventionJson;
	
	@BeforeScenario
	public void init() throws ParseException {
		response = null;
		interventionJson = new JSONObject();
		DATE_VALUE = (Date) customFormat.parse("2014-09-19T12:01:00");
		createDefaultIntervention();
	}
	
	private void createDefaultIntervention(){
		interventionCreationDTO.date = (Date) DATE_VALUE;
		interventionCreationDTO.description = DESCRIPTION_VALUE;
		interventionCreationDTO.patientNumber = PATIENT_VALUE;
		interventionCreationDTO.room = ROOM_VALUE;
		interventionCreationDTO.status = STATUS_VALUE;
		interventionCreationDTO.surgeon = (Surgeon) SURGEON_VALUE;
		interventionCreationDTO.type = TYPE_VALUE;
	}
	
	@Given("une intervention avec des informations manquantes")
	public void createInterventionWithMissingInformation(){
		interventionCreationDTO.description = null;
	}
	
	@Given("une intervention valide avec un statut indéterminé")
	public void createInterventionWithNonExistingStatus(){
		interventionCreationDTO.status = null;
	}

	@Given("une intervention avec un patient inexistant")
	public void createInterventionWithNonExistingPatient(){
		interventionCreationDTO.patientNumber = 500;
	}
	
	@When("j'ajoute cette intervention au dossier du patient")
	public void addIntervention() {
		response = given().port(JettyTestRunner.JETTY_TEST_PORT)
				.body(interventionJson.toString())
				.contentType(ContentType.JSON)
				.when()
				.post("interventions/");
		
		ThreadLocalContext.putObject(HttpResponseSteps.RESPONSE_OBJECT_KEY, response);
	}
	
	@Then("cette intervention est conservée")
	public void interventionIsSaved() {
		response.then().
			statusCode(Status.CREATED.getStatusCode());
	}
}
