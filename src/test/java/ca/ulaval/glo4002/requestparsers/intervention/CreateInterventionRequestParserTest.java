package ca.ulaval.glo4002.requestparsers.intervention;

import static org.junit.Assert.*;

import java.util.Date;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.intervention.InterventionStatus;
import ca.ulaval.glo4002.domain.intervention.InterventionType;
import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParserFactory;
import ca.ulaval.glo4002.utils.DateParser;

public class CreateInterventionRequestParserTest {

	private static final String SAMPLE_DATE_PARAMETER = "2001-07-04T12:08:56";
	private static final String SAMPLE_INVALID_DATE_PARAMETER = "2001-07-0412:08:56";
	private static final String SAMPLE_DESCRIPTION_PARAMETER = "description";
	private static final String SAMPLE_ROOM_PARAMETER = "room";
	private static final int SAMPLE_PATIENT_PARAMETER = 2;
	private static final int SAMPLE_SURGEON_PARAMETER = 101224;
	private static final String SAMPLE_STATUS_PARAMETER = "EN_COURS";
	private static final String INVALID_STATUS_PARAMETER = "INVALIDE";
	private static final String SAMPLE_TYPE_PARAMETER = "MOELLE";
	private static final String INVALID_TYPE_PARAMETER = "INVALIDE";
	private static final int MIN_PATIENT_PARAMETER = 2;
	private static final int MIN_SURGEON_PARAMETER = 101224;

	private CreateInterventionRequestParser createInterventionRequest;
	private JSONObject jsonRequest = new JSONObject();

	@Before
	public void init() throws Exception {
		jsonRequest.put(CreateInterventionRequestParser.DESCRIPTION_PARAMETER_NAME, SAMPLE_DESCRIPTION_PARAMETER);
		jsonRequest.put(CreateInterventionRequestParser.SURGEON_PARAMETER_NAME, SAMPLE_SURGEON_PARAMETER);
		jsonRequest.put(CreateInterventionRequestParser.DATE_PARAMETER_NAME, SAMPLE_DATE_PARAMETER);
		jsonRequest.put(CreateInterventionRequestParser.ROOM_PARAMETER_NAME, SAMPLE_ROOM_PARAMETER);
		jsonRequest.put(CreateInterventionRequestParser.TYPE_PARAMETER_NAME, SAMPLE_TYPE_PARAMETER);
		jsonRequest.put(CreateInterventionRequestParser.STATUS_PARAMETER_NAME, SAMPLE_STATUS_PARAMETER);
		jsonRequest.put(CreateInterventionRequestParser.PATIENT_PARAMETER_NAME, SAMPLE_PATIENT_PARAMETER);
		createRequestParser();
	}

	private void createRequestParser() throws Exception {
		createInterventionRequest = new CreateInterventionRequestParserFactory().getParser(jsonRequest);
	}

	@Test
	public void validatesGoodRequestCorrectly() throws Exception {
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedDescriptionParameter() throws Exception {
		jsonRequest.remove(CreateInterventionRequestParser.DESCRIPTION_PARAMETER_NAME);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsBlankDescriptionParameter() throws Exception {
		jsonRequest.put(CreateInterventionRequestParser.DESCRIPTION_PARAMETER_NAME, "");
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedSurgeonParameter() throws Exception {
		jsonRequest.remove(CreateInterventionRequestParser.SURGEON_PARAMETER_NAME);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedDateParameter() throws Exception {
		jsonRequest.remove(CreateInterventionRequestParser.DATE_PARAMETER_NAME);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedRoomParameter() throws Exception {
		jsonRequest.remove(CreateInterventionRequestParser.ROOM_PARAMETER_NAME);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsBlankRoomParameter() throws Exception {
		jsonRequest.put(CreateInterventionRequestParser.ROOM_PARAMETER_NAME, "");
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedTypeParameter() throws Exception {
		jsonRequest.remove(CreateInterventionRequestParser.TYPE_PARAMETER_NAME);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsInvalidTypeParameter() throws Exception {
		jsonRequest.put(CreateInterventionRequestParser.TYPE_PARAMETER_NAME, INVALID_TYPE_PARAMETER);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsInvalidStatusParameter() throws Exception {
		jsonRequest.put(CreateInterventionRequestParser.STATUS_PARAMETER_NAME, INVALID_STATUS_PARAMETER);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedPatientParameter() throws Exception {
		jsonRequest.remove(CreateInterventionRequestParser.PATIENT_PARAMETER_NAME);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsNegativePatientParameter() throws Exception {
		jsonRequest.put(CreateInterventionRequestParser.PATIENT_PARAMETER_NAME, -1);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsNegativeSurgeonParameter() throws Exception {
		jsonRequest.put(CreateInterventionRequestParser.SURGEON_PARAMETER_NAME, -1);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsInvalidDateParameter() throws Exception {
		jsonRequest.put(CreateInterventionRequestParser.DATE_PARAMETER_NAME, SAMPLE_INVALID_DATE_PARAMETER);
		createRequestParser();
	}

	@Test
	public void allowsUnspecifiedStatusParameter() throws Exception {
		jsonRequest.remove(CreateInterventionRequestParser.STATUS_PARAMETER_NAME);
		createRequestParser();
	}

	@Test
	public void allowsMinimumPatientParameter() throws Exception {
		jsonRequest.put(CreateInterventionRequestParser.PATIENT_PARAMETER_NAME, MIN_PATIENT_PARAMETER);
		createRequestParser();
	}

	@Test
	public void allowsMinimumSurgeonParameter() throws Exception {
		jsonRequest.put(CreateInterventionRequestParser.SURGEON_PARAMETER_NAME, MIN_SURGEON_PARAMETER);
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
		Date sampleDate = DateParser.parseDate(SAMPLE_DATE_PARAMETER);
		assertEquals(sampleDate, createInterventionRequest.getDate());
	}

	@Test
	public void returnsCorrectRoom() throws Exception {
		assertEquals(SAMPLE_ROOM_PARAMETER, createInterventionRequest.getRoom());
	}

	@Test
	public void returnsCorrectType() throws Exception {
		assertEquals(InterventionType.MARROW, createInterventionRequest.getType());
	}

	@Test
	public void returnsCorrectStatus() throws Exception {
		assertEquals(InterventionStatus.IN_PROGRESS, createInterventionRequest.getStatus());
	}

	@Test
	public void returnsCorrectPatient() throws Exception {
		assertEquals(SAMPLE_PATIENT_PARAMETER, createInterventionRequest.getPatient());
	}
}
