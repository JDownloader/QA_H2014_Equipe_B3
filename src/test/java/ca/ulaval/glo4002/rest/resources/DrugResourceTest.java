package ca.ulaval.glo4002.rest.resources;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.*;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.drug.DrugSearchRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.drug.DrugSearchRequestParserFactory;
import ca.ulaval.glo4002.services.drug.DrugService;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DrugResourceTest {
private static final String SAMPLE_JSON_REQUEST = "{attrib: value}";
	
	private DrugService drugServiceMock;
	private DrugSearchRequestParser drugSearchRequestParserMock;
	private DrugSearchRequestParserFactory drugSearchRequestParserFactoryMock;
	private DrugResource drugResource;
	
	@Before
	public void init() throws Exception {
		createMocks();
		stubMethods();
		buildPrescriptionResource();
	}
	
	private void createMocks() {
		drugServiceMock = mock(DrugService.class);
		drugSearchRequestParserMock = mock(DrugSearchRequestParser.class);
		drugSearchRequestParserFactoryMock = mock(DrugSearchRequestParserFactory.class);
	}
	
	private void stubMethods() throws Exception {
		when(drugSearchRequestParserFactoryMock.getParser(any(JSONObject.class))).thenReturn(drugSearchRequestParserMock);
	}
	
	private void buildPrescriptionResource() {
		drugResource = new DrugResource(drugServiceMock, drugSearchRequestParserFactoryMock);
	}
	
	@Test
	public void verifySearchDrugCallsServiceMethodsCorrectly() throws Exception {
		drugResource.post(SAMPLE_JSON_REQUEST);
		verify(drugServiceMock).searchDrug(drugSearchRequestParserMock);
	}
	
	@Test
	public void verifySearchDrugReturnsCreatedResponse() throws ServiceRequestException {
		Response response = drugResource.post(SAMPLE_JSON_REQUEST);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void verifySearchDrugReturnsInvalidResponseWhenSpecifyingInvalidRequest() throws Exception {
		doThrow(new ServiceRequestException()).when(drugServiceMock).searchDrug(drugSearchRequestParserMock);
		
		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = drugResource.post(SAMPLE_JSON_REQUEST);
		
		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
}
