package ca.ulaval.glo4002.requests;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.json.JSONException;
import org.junit.*;

import ca.ulaval.glo4002.rest.requests.DrugSearchRequest;

public class DrugSearchRequestTest {
	private static final String SAMPLE_NAME_PARAMETER = "drug_name";
	
	private DrugSearchRequest drugSearchRequest;
	private JSONObject jsonRequest = new JSONObject();
	
	@Before
	public void setup() throws Exception {
		jsonRequest.put("nom", SAMPLE_NAME_PARAMETER);
		createDrugSearchRequest();
	}
	
	private void createDrugSearchRequest() throws Exception {
		drugSearchRequest = new DrugSearchRequest(jsonRequest);
	}
	
	@Test
	public void validatesGoodRequestCorrectly() throws Exception {
		createDrugSearchRequest();
	}
	
	@Test(expected = JSONException.class)
	public void disallowsUnspecifiedNameParameter() throws Exception {
		jsonRequest.remove("nom");
		createDrugSearchRequest();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void disallowsBlankNameParameter() throws Exception {
		jsonRequest.put("nom", "");
		createDrugSearchRequest();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void disallowsNameWith2CharactersParameter() throws Exception {
		jsonRequest.put("nom", "ab");
		createDrugSearchRequest();
	}
	
	@Test
	public void allowsNameWith3CharactersParameter() throws Exception {
		jsonRequest.put("nom", "abc");
		createDrugSearchRequest();
	}
	
	@Test
	public void returnsCorrectName() throws Exception {
		assertEquals(SAMPLE_NAME_PARAMETER, drugSearchRequest.getName());
	}

}
