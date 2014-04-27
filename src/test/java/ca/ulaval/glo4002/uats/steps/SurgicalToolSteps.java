package ca.ulaval.glo4002.uats.steps;

import static com.jayway.restassured.RestAssured.given;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response.Status;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.json.JSONObject;

import ca.ulaval.glo4002.uats.runners.JettyTestRunner;
import ca.ulaval.glo4002.uats.steps.contexts.ThreadLocalContext;

import com.jayway.restassured.response.Response;

public class SurgicalToolSteps {
	public static final String SURGICAL_TOOL_ID_KEY = "instrument_id_key";
	public static final String SURGICAL_TOOL_TYPECODE_KEY = "instrument_typecode_key";
	
	private static final String TYPECODE_PARAMETER = "typecode";
	private static final String STATUS_PARAMETER = "statut";
	private static final String SERIAL_NUMBER_PARAMETER = "noserie";
	
	private static final String TYPECODE_VALUE = "description";
	private static final String STATUS_VALUE = "UTILISE_PATIENT";
	private static final String NEW_STATUS_VALUE = "SOUILLE";
	private static final String SERIAL_NUMBER_VALUE = "23562543-3635345";
	private static final String NEW_SERIAL_NUMBER_VALUE = "23562543-3635346";
	
	private Response response;
	JSONObject surgicalToolCreationJson;
	JSONObject surgicalToolModificationJson;
	
	@BeforeScenario
	public void init() throws ParseException {
		response = null;
		surgicalToolCreationJson = new JSONObject();
	}
	
	@Given("un instrument avec des informations valides")
	public void createValidSurgicalTool() {
		createDefaultSurgicalToolCreationJsonObject();
	}
	
	@Given("un instrument avec des informations manquantes")
	public void createSurgicalToolWithMissingInformation() {
		createDefaultSurgicalToolCreationJsonObject();
		surgicalToolCreationJson.remove(STATUS_PARAMETER);
	}
	
	@Given("un instrument avec un numéro de série déjà utilisé")
	public void createSurgicalToolWithDuplicateSerialNumber() {
		//Here we assume previous stories have been successfully run
		createDefaultSurgicalToolCreationJsonObject();
	}
	
	@Given("un instrument dans une intervention")
	@Composite(steps = {
            "Given un instrument avec des informations valides", 
            "When j'ajoute cet instrument à une intervention" }) 
	public void createInstrumentInIntervention() {
		//Nothing left do to here after composite steps have been run
	}
	
	@Given("un instrument dans une intervention interdisant les instruments anonymes")
	@Composite(steps = {
            "Given un instrument avec des informations valides", 
            "When j'ajoute cet instrument à une intervention interdisant les instruments anonymes" }) 
	public void createInstrumentInInterventionProhibitingAnonymousSirgucalTools() {
		//Nothing left do to here after composite steps have been run
	}
	
	@When("j'ajoute cet instrument à une intervention")
	@Composite(steps = { "Given un patient existant", 
            "Given une intervention avec des informations valide", 
            "When j'ajoute cette intervention au dossier du patient" }) 
	public void addInstrumentToIntervention() {
		addInstrument();
	}
	
	@When("j'ajoute cet instrument à une intervention interdisant les instruments anonymes")
	@Composite(steps = { "Given un patient existant", 
            "Given une intervention interdisant les instruments anonymes", 
            "When j'ajoute cette intervention au dossier du patient" }) 
	public void addInstrumentToInterventionProhibitingAnonymousSirgucalTools() {
		addInstrument();
	}
	
	@When("je modifie cet instrument avec des informations valides")
	public void modifyInstrumentWithValidInformation() {
		createDefaultSurgicalToolModificationJsonObject();
		modifyInstrument();
	}
	
	@When("je modifie le code de cet instrument")
	public void modifyInstrumentTypeCode() {
		createDefaultSurgicalToolModificationJsonObject();
		surgicalToolModificationJson.put(TYPECODE_PARAMETER, TYPECODE_VALUE);
		modifyInstrument();
	}
	
	@When("je modifie le le numéro de série de cet instrument par un numéro de série existant ")
	public void modifyInstrumentToDuplicateSerialNumber() {
		createDefaultSurgicalToolModificationJsonObject();
		surgicalToolModificationJson.put(SERIAL_NUMBER_PARAMETER, SERIAL_NUMBER_VALUE);
		modifyInstrument();
	}
	
	@When("je rend l'instrument anonyme")
	public void modifyInstrumentToAnonymous() {
		createDefaultSurgicalToolModificationJsonObject();
		surgicalToolModificationJson.remove(SERIAL_NUMBER_PARAMETER);
		modifyInstrument();
	}
	
	@Then("cet instrument est conservé")
	public void interventionIsSaved() {
		response.then().statusCode(Status.CREATED.getStatusCode());
	}
	
	@Then("cet instrument est modifié")
	public void interventionIsModified() {
		response.then().statusCode(Status.OK.getStatusCode());
	}
	
	private void addInstrument() {
		Integer interventionId = (Integer) ThreadLocalContext.getObject(InterventionSteps.INTERVENTION_ID_KEY);
		
		response = given().port(JettyTestRunner.JETTY_TEST_PORT)
				.body(surgicalToolCreationJson.toString())
				.contentType("application/json; charset=UTF-8")
				.when()
				.post(String.format("interventions/%d/instruments/", interventionId));
		
		int surgicalToolId = parseSurgicalToolIdFromLocationURIString(response.getHeader("location"));
		ThreadLocalContext.putObject(SURGICAL_TOOL_ID_KEY, surgicalToolId);
		ThreadLocalContext.putObject(SURGICAL_TOOL_TYPECODE_KEY, surgicalToolCreationJson.getString(TYPECODE_PARAMETER));
		ThreadLocalContext.putObject(HttpResponseSteps.RESPONSE_OBJECT_KEY, response);
	}
	
	private int parseSurgicalToolIdFromLocationURIString(String locationURI) {
		Pattern pattern = Pattern.compile("/interventions/\\d+/instruments/\\S+/(\\d)+");
		Matcher matcher = pattern.matcher(locationURI);
		return Integer.parseInt(matcher.group(1));
	}
	
	private void modifyInstrument() {
		Integer interventionId = (Integer) ThreadLocalContext.getObject(InterventionSteps.INTERVENTION_ID_KEY);
		String surgicalToolTypeCode = (String) ThreadLocalContext.getObject(SURGICAL_TOOL_TYPECODE_KEY);
		Integer surgicalToolId = (Integer) ThreadLocalContext.getObject(SURGICAL_TOOL_ID_KEY);
		
		response = given().port(JettyTestRunner.JETTY_TEST_PORT)
				.body(surgicalToolModificationJson.toString())
				.contentType("application/json; charset=UTF-8")
				.when()
				.post(String.format("interventions/%d/instruments/%d/%s/%d", interventionId, surgicalToolTypeCode, surgicalToolId));
		
		ThreadLocalContext.putObject(HttpResponseSteps.RESPONSE_OBJECT_KEY, response);
	}
	
	private void createDefaultSurgicalToolCreationJsonObject() {
		surgicalToolCreationJson.put(TYPECODE_PARAMETER, TYPECODE_VALUE);
		surgicalToolCreationJson.put(STATUS_PARAMETER, STATUS_VALUE);
		surgicalToolCreationJson.put(SERIAL_NUMBER_PARAMETER, SERIAL_NUMBER_VALUE);
	}
	
	private void createDefaultSurgicalToolModificationJsonObject()
	{
		surgicalToolModificationJson.put(STATUS_PARAMETER, NEW_STATUS_VALUE);
		surgicalToolModificationJson.put(SERIAL_NUMBER_PARAMETER, NEW_SERIAL_NUMBER_VALUE);
	}
 }
