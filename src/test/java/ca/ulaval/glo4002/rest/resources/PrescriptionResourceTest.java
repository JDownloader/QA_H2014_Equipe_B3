package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.drug.DrugDoesntHaveDinException;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.prescription.AddPrescriptionRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.prescription.AddPrescriptionRequestParserFactory;
import ca.ulaval.glo4002.services.prescription.PrescriptionService;

public class PrescriptionResourceTest {

	private static final String SAMPLE_JSON_REQUEST = "{attrib: value}";

	private PrescriptionService prescriptionServiceMock;
	private AddPrescriptionRequestParser addPrescriptionRequestParserMock;
	private AddPrescriptionRequestParserFactory addPrescriptionRequestParserFactoryMock;
	private PrescriptionResource prescriptionResource;

	@Before
	public void init() throws Exception {
		createMocks();
		stubMethods();
		buildPrescriptionResource();
	}

	private void createMocks() {
		prescriptionServiceMock = mock(PrescriptionService.class);
		addPrescriptionRequestParserMock = mock(AddPrescriptionRequestParser.class);
		addPrescriptionRequestParserFactoryMock = mock(AddPrescriptionRequestParserFactory.class);
	}

	private void stubMethods() throws Exception {
		when(addPrescriptionRequestParserFactoryMock.getParser(any(JSONObject.class))).thenReturn(addPrescriptionRequestParserMock);
	}

	private void buildPrescriptionResource() {
		prescriptionResource = new PrescriptionResource(prescriptionServiceMock, addPrescriptionRequestParserFactoryMock);
	}

	@Test
	public void verifyAddPrescriptionCallsServiceMethodsCorrectly() throws ServiceRequestException, DrugDoesntHaveDinException {
		prescriptionResource.post(SAMPLE_JSON_REQUEST);
		verify(prescriptionServiceMock).addPrescription(addPrescriptionRequestParserMock);
	}

	@Test
	public void verifyAddPrescriptionReturnsCreatedResponse() throws ServiceRequestException {
		Response response = prescriptionResource.post(SAMPLE_JSON_REQUEST);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyAddPrescriptionReturnsInvalidResponseWhenSpecifyingInvalidRequest() throws ServiceRequestException, DrugDoesntHaveDinException {
		doThrow(new ServiceRequestException()).when(prescriptionServiceMock).addPrescription(addPrescriptionRequestParserMock);

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = prescriptionResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

}
