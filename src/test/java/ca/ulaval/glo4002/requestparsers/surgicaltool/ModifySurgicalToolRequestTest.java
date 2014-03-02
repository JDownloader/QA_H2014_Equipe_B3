package ca.ulaval.glo4002.requestparsers.surgicaltool;

import static org.junit.Assert.*;

import org.junit.*;

import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.ModifySurgicalToolRequestParser;
public class ModifySurgicalToolRequestTest extends CreateSurgicalToolRequestTest {

	private static final String SAMPLE_NEW_SERIAL_NUMBER = "684518TF";
	
	private ModifySurgicalToolRequestParser modifySurgicalToolRequest;
	
	@Before
	@Override
	public void setup() throws Exception {
		jsonRequest.put("nouveaunoserie", SAMPLE_NEW_SERIAL_NUMBER);
		jsonRequest.put("serialNumberPathParameter", super.SAMPLE_SERIAL_NUMBER);
		super.setup();
	}
	
	@Override
	public void createRequestParser() throws Exception {
		modifySurgicalToolRequest = new ModifySurgicalToolRequestParser(jsonRequest);
		createSurgicalToolRequest = modifySurgicalToolRequest;
	}
	
	@Test
	public void validatesGoodRequestCorrectly() throws Exception {
		createRequestParser();
	}
	
	@Test
	public void returnsCorrectNewSerialNumber() throws Exception {
		assertEquals(SAMPLE_NEW_SERIAL_NUMBER, modifySurgicalToolRequest.getNewSerialNumber());
	}
}
