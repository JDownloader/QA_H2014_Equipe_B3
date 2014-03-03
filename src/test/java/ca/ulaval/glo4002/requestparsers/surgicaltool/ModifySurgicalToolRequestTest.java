package ca.ulaval.glo4002.requestparsers.surgicaltool;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.*;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;
import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.AbstractSurgicalToolRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.ModifySurgicalToolRequestParser;
public class ModifySurgicalToolRequestTest {
	
	private static final int SAMPLE_INTERVENTION_NUMBER = 3;
	private static final String SAMPLE_STATUS = "Inutilise";
	protected final String SAMPLE_SERIAL_NUMBER = "684518TF";
	private static final String SAMPLE_TYPE_CODE = "56465T";
	private static final String SAMPLE_NEW_TYPE_CODE = "56445T";
	private static final int MIN_INTERVENTION_NUMBER = 0;
	private static final String SAMPLE_NEW_SERIAL_NUMBER = "684518TF";
	
	private ModifySurgicalToolRequestParser surgicalToolRequest;
	private JSONObject jsonRequest = new JSONObject();
	
	@Before
	public void init() throws Exception {
		jsonRequest.put(AbstractSurgicalToolRequestParser.INTERVENTION_NUMBER_PARAMETER_NAME, SAMPLE_INTERVENTION_NUMBER);
		jsonRequest.put(AbstractSurgicalToolRequestParser.STATUS_PARAMETER_NAME, SAMPLE_STATUS);
		jsonRequest.put(AbstractSurgicalToolRequestParser.SERIAL_NUMBER_PARAMETER_NAME, SAMPLE_SERIAL_NUMBER);
		jsonRequest.put(AbstractSurgicalToolRequestParser.TYPECODE_PARAMETER_NAME, SAMPLE_TYPE_CODE);
		jsonRequest.put("nouveaunoserie", SAMPLE_NEW_SERIAL_NUMBER);
	}
	
	public void createRequestParser() throws Exception {
		surgicalToolRequest = new ModifySurgicalToolRequestParser(jsonRequest);
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
	public void disallowsChangingTypeCodeParameter() throws Exception {
		jsonRequest.put("nouveautypecode", SAMPLE_NEW_TYPE_CODE);
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
		createRequestParser();
		assertEquals(SAMPLE_TYPE_CODE, surgicalToolRequest.getTypeCode());
	}
	
	@Test
	public void returnsCorrectStatus() throws Exception {
		createRequestParser();
		assertEquals(SurgicalToolStatus.INUTILISE, surgicalToolRequest.getStatus());
	}
	
	@Test
	public void returnsCorrectSerialNumber() throws Exception {
		createRequestParser();
		assertEquals(SAMPLE_SERIAL_NUMBER, surgicalToolRequest.getSerialNumber());
	}
	
	@Test
	public void hasSerialNumberReturnsCorrectValue() throws Exception {
		createRequestParser();
		assertTrue(surgicalToolRequest.hasSerialNumber());
	}
	
	@Test
	public void returnsCorrectInterventionNumber() throws Exception {
		createRequestParser();
		assertEquals(SAMPLE_INTERVENTION_NUMBER, surgicalToolRequest.getInterventionNumber());
	}
	
	@Test
	public void returnsCorrectNewSerialNumber() throws Exception {
		createRequestParser();
		assertEquals(SAMPLE_NEW_SERIAL_NUMBER, surgicalToolRequest.getNewSerialNumber());
	}
}
