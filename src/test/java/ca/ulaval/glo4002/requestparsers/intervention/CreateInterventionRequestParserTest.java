package ca.ulaval.glo4002.requestparsers.intervention;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.intervention.InterventionStatus;
import ca.ulaval.glo4002.domain.intervention.InterventionType;
import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;

public class CreateInterventionRequestParserTest {
	
	private static final String SAMPLE_DATE_PARAMETER = "2001-07-04T12:08:56";
	private static final String SAMPLE_INVALID_DATE_PARAMETER = "2001-07-0412:08:56";
	private static final String SAMPLE_DESCRIPTION_PARAMETER = "description";
	private static final String SAMPLE_ROOM_PARAMETER = "room";
	private static final int SAMPLE_PATIENT_PARAMETER = 2;
	private static final int SAMPLE_SURGEON_PARAMETER = 101224;
	private static final String SAMPLE_STATUS_PARAMETER = "en_cours";
	private static final String SAMPLE_TYPE_PARAMETER = "moelle";
	private static final int MIN_PATIENT_PARAMETER = 2;
	private static final int MIN_SURGEON_PARAMETER = 101224;
	
	private CreateInterventionRequestParser createInterventionRequest;
	private JSONObject jsonRequest = new JSONObject();
	
	@Before
	public void setup() throws Exception {
		jsonRequest.put("description", SAMPLE_DESCRIPTION_PARAMETER);
		jsonRequest.put("chirurgien", SAMPLE_SURGEON_PARAMETER);
		jsonRequest.put("date", SAMPLE_DATE_PARAMETER);
		jsonRequest.put("salle", SAMPLE_ROOM_PARAMETER);
		jsonRequest.put("type", SAMPLE_TYPE_PARAMETER);
		jsonRequest.put("statut", SAMPLE_STATUS_PARAMETER);
		jsonRequest.put("patient", SAMPLE_PATIENT_PARAMETER);
		createRequestParser();
	}
	
	private void createRequestParser() throws Exception {
		createInterventionRequest = new CreateInterventionRequestParser(jsonRequest);
	}
	
	@Test
	public void validatesGoodRequestCorrectly() throws Exception {
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedDescriptionParameter() throws Exception {
		jsonRequest.remove("description");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsBlankDescriptionParameter() throws Exception {
		jsonRequest.put("description", "");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedSurgeonParameter() throws Exception {
		jsonRequest.remove("chirurgien");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedDateParameter() throws Exception {
		jsonRequest.remove("date");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedRoomParameter() throws Exception {
		jsonRequest.remove("salle");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsBlankRoomParameter() throws Exception {
		jsonRequest.put("salle", "");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedTypeParameter() throws Exception {
		jsonRequest.remove("type");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedPatientParameter() throws Exception {
		jsonRequest.remove("patient");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsNegativePatientParameter() throws Exception {
		jsonRequest.put("patient", -1);
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsNegativeSurgeonParameter() throws Exception {
		jsonRequest.put("chirurgien", -1);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsInvalidDateParameter() throws Exception {
		jsonRequest.put("date", SAMPLE_INVALID_DATE_PARAMETER);
		createRequestParser();
	}
	
	@Test
	public void allowsUnspecifiedStatusParameter() throws Exception {
		jsonRequest.remove("statut");
		createRequestParser();
	}
	
	@Test
	public void allowsMinimumPatientParameter() throws Exception {
		jsonRequest.put("patient", MIN_PATIENT_PARAMETER);
		createRequestParser();
	}
	
	@Test
	public void allowsMinimumSurgeonParameter() throws Exception {
		jsonRequest.put("chirurgien", MIN_SURGEON_PARAMETER);
		createRequestParser();
	}
	
	@Test
	public void returnsCorrectDescription() throws Exception {
		assertEquals(SAMPLE_DESCRIPTION_PARAMETER, createInterventionRequest.getDescription());
	}
	
	@Test
	public void returnsCorrectSurgeon() throws Exception {
		assertEquals(SAMPLE_SURGEON_PARAMETER, createInterventionRequest.getSurgeon());
	}
	
	@Test
	public void returnsCorrectDate() throws Exception {
		Date sampleDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2001-07-04T12:08:56");
		assertEquals(sampleDate, createInterventionRequest.getDate());
	}
	
	@Test
	public void returnsCorrectRoom() throws Exception {
		assertEquals(SAMPLE_ROOM_PARAMETER, createInterventionRequest.getRoom());
	}
	
	@Test
	public void returnsCorrectType() throws Exception {
		assertEquals(InterventionType.MOELLE, createInterventionRequest.getType());
	}
	
	@Test
	public void returnsCorrectStatus() throws Exception {
		assertEquals(InterventionStatus.EN_COURS, createInterventionRequest.getStatus());
	}
	
	@Test
	public void returnsCorrectPatient() throws Exception {
		assertEquals(SAMPLE_PATIENT_PARAMETER, createInterventionRequest.getPatient());
	}
}
