package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.drug.DrugSearchRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.drug.DrugSearchRequestParserFactory;
import ca.ulaval.glo4002.services.drug.DrugService;

public class DrugResourceTest {
	private static final Din SAMPLE_DIN = new Din("0498");
	private static final String SAMPLE_DRUG_NAME = "Drug";
	private static final String SAMPLE_DESCRIPTION = "Description";
	private static final String SAMPLE_JSON_REQUEST = "{attrib: value}";

	private DrugService drugServiceMock;
	private DrugSearchRequestParser drugSearchRequestParserMock;
	private DrugSearchRequestParserFactory drugSearchRequestParserFactoryMock;
	private DrugResource drugResource;
	private Drug drugMock;

	@Before
	public void init() throws Exception {
		createMocks();
		stubDrugMethods();
		stubDrugServiceMethods();
		stubRequestParserMethods();
		buildPrescriptionResource();
	}

	private void createMocks() {
		drugMock = mock(Drug.class);
		drugServiceMock = mock(DrugService.class);
		drugSearchRequestParserMock = mock(DrugSearchRequestParser.class);
		drugSearchRequestParserFactoryMock = mock(DrugSearchRequestParserFactory.class);
	}

	private void stubDrugMethods() throws NoSuchFieldException {
		when(drugMock.getDin()).thenReturn(SAMPLE_DIN);
		when(drugMock.getDescription()).thenReturn(SAMPLE_DESCRIPTION);
		when(drugMock.getName()).thenReturn(SAMPLE_DRUG_NAME);
	}
	
	private void stubDrugServiceMethods() throws ServiceRequestException, Exception {
		ArrayList<Drug> drugMocks = new ArrayList<Drug>(Arrays.asList(drugMock));
		when(drugServiceMock.searchDrug(drugSearchRequestParserMock)).thenReturn(drugMocks);
	}
	
	private void stubRequestParserMethods() throws Exception {
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
	public void verifySearchDrugReturnsCreatedResponse() throws Exception {
		Response response = drugResource.post(SAMPLE_JSON_REQUEST);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}



	@Test
	public void verifySearchDrugReturnsBadRequestResponseWhenSpecifyingInvalidRequest() throws Exception {
		doThrow(new ServiceRequestException()).when(drugServiceMock).searchDrug(drugSearchRequestParserMock);

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = drugResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifySearchDrugReturnsBadRequestResponseWhenSpecifyingInvalidRequestString() throws Exception {
		doThrow(new RequestParseException()).when(drugSearchRequestParserFactoryMock).getParser(any(JSONObject.class));

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = drugResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifySearchDrugReturnsInternalServerErrorResponseOnUnhandledException() throws Exception {
		doThrow(new Exception()).when(drugServiceMock).searchDrug(drugSearchRequestParserMock);

		Response expectedResponse = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		Response receivedResponse = drugResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
}
