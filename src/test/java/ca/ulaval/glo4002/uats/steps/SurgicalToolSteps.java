package ca.ulaval.glo4002.uats.steps;

import static com.jayway.restassured.RestAssured.given;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
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
	private static final int FIRST_GENERATED_SERIALNUMBER = 0;
	private static final String LOCATION_HEADER_NAME = "location";
	public static final String SURGICAL_TOOL_ID_KEY = "instrument_id_key";
	public static final String SURGICAL_TOOL_TYPECODE_KEY = "instrument_typecode_key";
	
	private static final String TYPECODE_PARAMETER = "typecode";
	private static final String STATUS_PARAMETER = "statut";
	private static final String SERIAL_NUMBER_PARAMETER = "noserie";
	
	private static final String TYPECODE_VALUE = "IT33434";
	private static final String STATUS_VALUE = "UTILISE_PATIENT";
	private static final String NEW_STATUS_VALUE = "SOUILLE";

	private Integer serialNumberIndexCounter = 0;
	
	private Response response;
	JSONObject surgicalToolCreationJson;
	JSONObject surgicalToolModificationJson;
	
	@BeforeScenario
	public void init() throws ParseException {
		response = null;
		surgicalToolCreationJson = new JSONObject();
		surgicalToolModificationJson = new JSONObject();
	}
	
	@Given("un instrument avec des informations valides")
	public void createValidSurgicalTool() {
		createDefaultSurgicalToolCreationJsonObject();
	}
	
	@Given("un instrument anonyme avec des informations valides")
	public void createValidAnonymousSurgicalTool() {
		createDefaultSurgicalToolCreationJsonObject();
		surgicalToolCreationJson.remove(SERIAL_NUMBER_PARAMETER);
	}
	
	@Given("un instrument avec des informations manquantes")
	public void createSurgicalToolWithMissingInformation() {
		createDefaultSurgicalToolCreationJsonObject();
		surgicalToolCreationJson.remove(STATUS_PARAMETER);
	}
	
	@Given("un instrument avec un numéro de série déjà utilisé")
	public void createSurgicalToolWithDuplicateSerialNumber() {
		createDefaultSurgicalToolCreationJsonObject();
		surgicalToolCreationJson.put(SERIAL_NUMBER_PARAMETER, generateSerialNumberFromIndex(FIRST_GENERATED_SERIALNUMBER));
	}
	
	@When("j'ajoute cet instrument à une intervention")
	@Composite(steps = {
            "Given une intervention avec des informations valide", 
            "When j'ajoute cette intervention au dossier d'un patient",
            "When j'ajoute cet instrument à cette intervention"}) 
	public void addInstrumentToAnIntervention() {
		//Nothing left do to here after composite steps have been run
	}

	@When("j'ajoute cet instrument à une intervention interdisant les instruments anonymes")
	@Composite(steps = {
            "Given une intervention interdisant les instruments anonymes", 
            "When j'ajoute cette intervention au dossier d'un patient",
	 		"When j'ajoute cet instrument à cette intervention"}) 
	public void addInstrumentToInterventionProhibitingAnonymousSirgucalTools() {
		//Nothing left do to here after composite steps have been run
	}
	
	@When("j'ajoute cet instrument à cette intervention")
	public void addInstrumentToIntervention() {
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
	
	@When("je modifie le le numéro de série de cet instrument par un numéro de série existant")
	public void modifyInstrumentToDuplicateSerialNumber() {
		createDefaultSurgicalToolModificationJsonObject();
		surgicalToolModificationJson.put(SERIAL_NUMBER_PARAMETER, generateSerialNumberFromIndex(FIRST_GENERATED_SERIALNUMBER));
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
		
		Integer surgicalToolId = tryParseSurgicalToolIdFromLocationURIString(response.getHeader(LOCATION_HEADER_NAME));
		ThreadLocalContext.putObject(SURGICAL_TOOL_ID_KEY, surgicalToolId);
		ThreadLocalContext.putObject(SURGICAL_TOOL_TYPECODE_KEY, surgicalToolCreationJson.getString(TYPECODE_PARAMETER));
		ThreadLocalContext.putObject(HttpResponseSteps.RESPONSE_OBJECT_KEY, response);
	}
	
	private Integer tryParseSurgicalToolIdFromLocationURIString(String locationURI) {
		if (!StringUtils.isBlank(locationURI)) {
    		Pattern pattern = Pattern.compile("\\S+/interventions/\\d+/instruments/\\S+/(\\d+)");
    		Matcher matcher = pattern.matcher(locationURI);
    		if (matcher.find()) {
    			return Integer.parseInt(matcher.group(1));
    		}
		}
		return null;
	}
	
	private void modifyInstrument() {
		Integer interventionId = (Integer) ThreadLocalContext.getObject(InterventionSteps.INTERVENTION_ID_KEY);
		String surgicalToolTypeCode = (String) ThreadLocalContext.getObject(SURGICAL_TOOL_TYPECODE_KEY);
		Integer surgicalToolId = (Integer) ThreadLocalContext.getObject(SURGICAL_TOOL_ID_KEY);
		
		response = given().port(JettyTestRunner.JETTY_TEST_PORT)
				.body(surgicalToolModificationJson.toString())
				.contentType("application/json; charset=UTF-8")
				.when()
				.put(String.format("/interventions/%d/instruments/%s/%d", interventionId, surgicalToolTypeCode, surgicalToolId));
		
		ThreadLocalContext.putObject(HttpResponseSteps.RESPONSE_OBJECT_KEY, response);
	}
	
	private void createDefaultSurgicalToolCreationJsonObject() {
		surgicalToolCreationJson.put(TYPECODE_PARAMETER, TYPECODE_VALUE);
		surgicalToolCreationJson.put(STATUS_PARAMETER, STATUS_VALUE);
		surgicalToolCreationJson.put(SERIAL_NUMBER_PARAMETER, generateSerialNumberFromIndex(serialNumberIndexCounter++));
	}
	
	private void createDefaultSurgicalToolModificationJsonObject() {
		surgicalToolModificationJson.put(STATUS_PARAMETER, NEW_STATUS_VALUE);
		surgicalToolModificationJson.put(SERIAL_NUMBER_PARAMETER, generateSerialNumberFromIndex(serialNumberIndexCounter++));
	}
	
	private String generateSerialNumberFromIndex(Integer serialNumberIndexCounter) {
		return serialNumberIndexCounter.toString();
	}
 }
