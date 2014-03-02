package ca.ulaval.glo4002.requestparsers.surgicaltool;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.*;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;
import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.CreateSurgicalToolRequestParser;

public class CreateSurgicalToolRequestTest {
	
	private static final int SAMPLE_INTERVENTION_NUMBER = 3;
	private static final String SAMPLE_STATUS = "Terminee";
	protected final String SAMPLE_SERIAL_NUMBER = "684518TF";
	private static final String SAMPLE_TYPE_CODE = "56465T";
	private static final int MIN_INTERVENTION_NUMBER = 0;
	
	protected CreateSurgicalToolRequestParser createSurgicalToolRequest;
	protected JSONObject jsonRequest = new JSONObject();
	
	@Before
	public void setup() throws Exception {
		jsonRequest.put("nointervention", SAMPLE_INTERVENTION_NUMBER);
		jsonRequest.put("statut", SAMPLE_STATUS);
		jsonRequest.put("noserie", SAMPLE_SERIAL_NUMBER);
		jsonRequest.put("typecode", SAMPLE_TYPE_CODE);
		createRequestParser();
	}
	
	public void createRequestParser() throws Exception {
		createSurgicalToolRequest = new CreateSurgicalToolRequestParser(jsonRequest);
	}
	
	@Test
	public void validatesGoodRequestCorrectly() throws Exception {
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedTypeCode() throws Exception {
		jsonRequest.remove("typecode");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsEmptyTypeCode() throws Exception {
		jsonRequest.put("typecode", "");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedInterventionNumberParameter() throws Exception {
		jsonRequest.remove("nointervention");
		createRequestParser();
	}
	
	@Test(expected = RequestParseException.class)
	public void disallowsNegativeInterventionNumberParameter() throws Exception {
		jsonRequest.put("nointervention", "-1");
		createRequestParser();
	}
	
	@Test
	public void allowsMinimumInterventionNumberParameter() throws Exception {
		jsonRequest.put("din", MIN_INTERVENTION_NUMBER);
		createRequestParser();
	}
	
	@Test
	public void returnsCorrectTypeCode() throws Exception {
		assertEquals(SAMPLE_TYPE_CODE, createSurgicalToolRequest.getTypeCode());
	}
	
	@Test
	public void returnsCorrectStatus() throws Exception {
		assertEquals(SurgicalToolStatus.TERMINEE, createSurgicalToolRequest.getStatus());
	}
	
	@Test
	public void returnsCorrectSerialNumber() throws Exception {
		assertEquals(SAMPLE_SERIAL_NUMBER, createSurgicalToolRequest.getSerialNumber());
	}
	
	@Test
	public void hasSerialNumberReturnsCorrectValue() throws Exception {
		assertTrue(createSurgicalToolRequest.hasSerialNumber());
	}
	
	@Test
	public void returnsCorrectInterventionNumber() throws Exception {
		assertEquals(SAMPLE_INTERVENTION_NUMBER, createSurgicalToolRequest.getInterventionNumber());
	}
	
}
