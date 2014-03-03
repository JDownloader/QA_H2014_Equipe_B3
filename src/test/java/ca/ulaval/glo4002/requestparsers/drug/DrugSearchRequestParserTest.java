package ca.ulaval.glo4002.requestparsers.drug;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.rest.requestparsers.drug.DrugSearchRequestParser;

public class DrugSearchRequestParserTest {
	private static final String SAMPLE_NAME_PARAMETER = "drug_name";

	private DrugSearchRequestParser drugSearchRequest;
	private JSONObject jsonRequest = new JSONObject();

	@Before
	public void init() throws Exception {
		jsonRequest.put("nom", SAMPLE_NAME_PARAMETER);
		createRequestParser();
	}

	private void createRequestParser() throws Exception {
		drugSearchRequest = new DrugSearchRequestParser(jsonRequest);
	}

	@Test
	public void validatesGoodRequestCorrectly() throws Exception {
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsUnspecifiedNameParameter() throws Exception {
		jsonRequest.remove(DrugSearchRequestParser.NAME_PARAMETER);
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsBlankNameParameter() throws Exception {
		jsonRequest.put(DrugSearchRequestParser.NAME_PARAMETER, "");
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsNameParameterWithLessThan3Length() throws Exception {
		jsonRequest.put(DrugSearchRequestParser.NAME_PARAMETER, "ab");
		createRequestParser();
	}

	@Test(expected = RequestParseException.class)
	public void disallowsNameParameterWithLessThan3LengthIncludingWildcards() throws Exception {
		jsonRequest.put(DrugSearchRequestParser.NAME_PARAMETER, "ab ");
		createRequestParser();
	}

	@Test
	public void allowsNameWith3CharactersParameter() throws Exception {
		jsonRequest.put(DrugSearchRequestParser.NAME_PARAMETER, "abc");
		createRequestParser();
	}

	@Test
	public void returnsCorrectName() throws Exception {
		assertEquals(SAMPLE_NAME_PARAMETER, drugSearchRequest.getName());
	}

}
