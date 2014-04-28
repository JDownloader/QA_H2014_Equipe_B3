package ca.ulaval.glo4002.uats.steps;

import java.text.ParseException;

import javax.ws.rs.core.Response.Status;

import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.json.JSONObject;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;
import ca.ulaval.glo4002.uats.steps.contexts.ThreadLocalContext;
import ca.ulaval.glo4002.uats.steps.utils.HttpLocationParser;

import com.jayway.restassured.response.Response;

public class SurgicalToolSteps {
	private static final int FIRST_GENERATED_SERIALNUMBER_INDEX = 0;
	public static final String LAST_SURGICAL_TOOL_ID_KEY = "instrument_id_key";
	public static final String LAST_SURGICAL_TOOL_TYPECODE_KEY = "instrument_typecode_key";
	
	private static final String TYPECODE_PARAMETER = "typecode";
	private static final String STATUS_PARAMETER = "statut";
	private static final String SERIAL_NUMBER_PARAMETER = "noserie";
	
	private static final String SAMPLE_TYPECODE = "IT33434";
	private static final String SAMPLE_STATUS = SurgicalToolStatus.PATIENT_USED.getValue();
	private static final String ANOTHER_SAMPLE_STATUS = SurgicalToolStatus.CONTAMINATED.getValue();

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
	
	@Given("un instrument non-utilisé")
	public void createUsedSurgicalTool() {
		createDefaultSurgicalToolCreationJsonObject();
		surgicalToolModificationJson.put(STATUS_PARAMETER, SurgicalToolStatus.UNUSED.getValue());
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
		surgicalToolCreationJson.put(SERIAL_NUMBER_PARAMETER, generateSerialNumberFromIndex(FIRST_GENERATED_SERIALNUMBER_INDEX));
	}
	
	@When("j'ajoute cet instrument à une intervention")
	@Composite(steps = {
            "Given une intervention avec des informations valide", 
            "When j'ajoute cette intervention au dossier d'un patient",
            "When j'ajoute cet instrument à cette intervention"}) 
	public void addInstrumentToAnIntervention() {
		//Nothing left do to here after composite steps have been run
	}

	@When("j'ajoute cet instrument à une intervention autorisant les instruments anonymes")
	@Composite(steps = {
            "Given une intervention autorisant les instruments anonymes", 
            "When j'ajoute cette intervention au dossier d'un patient",
	 		"When j'ajoute cet instrument à cette intervention"}) 
	public void addInstrumentToInterventionAuthorizingAnonymousSirgucalTools() {
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
		createSurgicalTool();
	}
	
	@When("je modifie cet instrument avec des informations valides")
	public void modifyInstrumentWithValidInformation() {
		createDefaultSurgicalToolModificationJsonObject();
		modifySurgicalTool();
	}
	
	@When("je modifie le code de cet instrument")
	public void modifyInstrumentTypeCode() {
		createDefaultSurgicalToolModificationJsonObject();
		surgicalToolModificationJson.put(TYPECODE_PARAMETER, SAMPLE_TYPECODE);
		modifySurgicalTool();
	}
	
	@When("j'utilise cet instrument")
	@Aliases(values = {
			"j'utilise cet instrument une autre fois",
			"je modifie le statut de cet instrument"})
	public void modifyInstrumentStatus() {
		createDefaultSurgicalToolModificationJsonObject();
		surgicalToolModificationJson.put(STATUS_PARAMETER, ANOTHER_SAMPLE_STATUS);
		modifySurgicalTool();
	}
	
	@When("je modifie le le numéro de série de cet instrument par un numéro de série existant")
	public void modifyInstrumentToDuplicateSerialNumber() {
		createDefaultSurgicalToolModificationJsonObject();
		surgicalToolModificationJson.put(SERIAL_NUMBER_PARAMETER, generateSerialNumberFromIndex(FIRST_GENERATED_SERIALNUMBER_INDEX));
		modifySurgicalTool();
	}
	
	@When("je rend l'instrument anonyme")
	public void modifyInstrumentToAnonymous() {
		createDefaultSurgicalToolModificationJsonObject();
		surgicalToolModificationJson.remove(SERIAL_NUMBER_PARAMETER);
		modifySurgicalTool();
	}
	
	@Then("cet instrument est conservé")
	public void interventionIsSaved() {
		response.then().statusCode(Status.CREATED.getStatusCode());
	}
	
	@Then("cet instrument est modifié")
	public void interventionIsModified() {
		response.then().statusCode(Status.OK.getStatusCode());
	}
	
	private void createSurgicalTool() {
		Integer interventionId = (Integer) ThreadLocalContext.getObject(InterventionSteps.LAST_INTERVENTION_ID_KEY);
		
		response = HttpResponseSteps.getDefaultRequestSepcification(surgicalToolCreationJson)
				.post(String.format("interventions/%d/instruments/", interventionId));
		
		saveSurgicalToolCreationResponseContext();
	}

	private void saveSurgicalToolCreationResponseContext() {
		try {
			Integer surgicalToolId = HttpLocationParser.parseSurgicalToolIdFromHeader(response);
			ThreadLocalContext.putObject(LAST_SURGICAL_TOOL_ID_KEY, surgicalToolId);
		} catch (IllegalArgumentException e) {
			//Surgical tool ID does not need to be saved into context if it is invalid.
		}
		ThreadLocalContext.putObject(LAST_SURGICAL_TOOL_TYPECODE_KEY, surgicalToolCreationJson.getString(TYPECODE_PARAMETER));
		ThreadLocalContext.putObject(HttpResponseSteps.LAST_RESPONSE_OBJECT_KEY, response);
	}
	
	private void modifySurgicalTool() {
		Integer interventionId = (Integer) ThreadLocalContext.getObject(InterventionSteps.LAST_INTERVENTION_ID_KEY);
		String surgicalToolTypeCode = (String) ThreadLocalContext.getObject(LAST_SURGICAL_TOOL_TYPECODE_KEY);
		Integer surgicalToolId = (Integer) ThreadLocalContext.getObject(LAST_SURGICAL_TOOL_ID_KEY);
		
		response = HttpResponseSteps.getDefaultRequestSepcification(surgicalToolModificationJson)
				.put(String.format("/interventions/%d/instruments/%s/%d", interventionId, surgicalToolTypeCode, surgicalToolId));
		
		ThreadLocalContext.putObject(HttpResponseSteps.LAST_RESPONSE_OBJECT_KEY, response);
	}
	
	private void createDefaultSurgicalToolCreationJsonObject() {
		surgicalToolCreationJson.put(TYPECODE_PARAMETER, SAMPLE_TYPECODE);
		surgicalToolCreationJson.put(STATUS_PARAMETER, SAMPLE_STATUS);
		surgicalToolCreationJson.put(SERIAL_NUMBER_PARAMETER, generateSerialNumberFromIndex(serialNumberIndexCounter++));
	}
	
	private void createDefaultSurgicalToolModificationJsonObject() {
		surgicalToolModificationJson.put(STATUS_PARAMETER, ANOTHER_SAMPLE_STATUS);
		surgicalToolModificationJson.put(SERIAL_NUMBER_PARAMETER, generateSerialNumberFromIndex(serialNumberIndexCounter++));
	}
	
	private String generateSerialNumberFromIndex(Integer serialNumberIndexCounter) {
		return serialNumberIndexCounter.toString();
	}
 }
