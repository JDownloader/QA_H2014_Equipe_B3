package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.prescription.PrescriptionCreationRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.prescription.PrescriptionCreationRequestParserFactory;
import ca.ulaval.glo4002.services.prescription.PrescriptionService;

public class PrescriptionResourceTest {

	private static final String SAMPLE_JSON_REQUEST = "{attrib: value}";

	private PrescriptionService prescriptionServiceMock;
	private PrescriptionCreationRequestParser addPrescriptionRequestParserMock;
	private PrescriptionCreationRequestParserFactory addPrescriptionRequestParserFactoryMock;
	private PrescriptionResource prescriptionResource;

	@Before
	public void init() throws Exception {
		createMocks();
		stubMethods();
		buildPrescriptionResource();
	}

	private void createMocks() {
		prescriptionServiceMock = mock(PrescriptionService.class);
		addPrescriptionRequestParserMock = mock(PrescriptionCreationRequestParser.class);
		addPrescriptionRequestParserFactoryMock = mock(PrescriptionCreationRequestParserFactory.class);
	}

	private void stubMethods() throws Exception {
		when(addPrescriptionRequestParserFactoryMock.createParser(any(JSONObject.class))).thenReturn(addPrescriptionRequestParserMock);
	}

	private void buildPrescriptionResource() {
		prescriptionResource = new PrescriptionResource(prescriptionServiceMock, addPrescriptionRequestParserFactoryMock);
	}

	@Test
	public void verifyAddPrescriptionCallsServiceMethodsCorrectly() throws Exception {
		prescriptionResource.post(SAMPLE_JSON_REQUEST);
		verify(prescriptionServiceMock).addPrescription(addPrescriptionRequestParserMock);
	}

	@Test
	public void verifyAddPrescriptionReturnsCreatedResponse() throws Exception {
		Response response = prescriptionResource.post(SAMPLE_JSON_REQUEST);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyAddPrescriptionReturnsBadRequestResponseWhenSpecifyingInvalidRequest() throws Exception {
		doThrow(new ServiceRequestException()).when(prescriptionServiceMock).addPrescription(addPrescriptionRequestParserMock);

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = prescriptionResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifyAddPrescriptionReturnsBadRequestResponseWhenSpecifyingInvalidRequestString() throws Exception {
		doThrow(new RequestParseException()).when(addPrescriptionRequestParserFactoryMock).createParser(any(JSONObject.class));

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = prescriptionResource.post(SAMPLE_JSON_REQUEST);
		
		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}


}
