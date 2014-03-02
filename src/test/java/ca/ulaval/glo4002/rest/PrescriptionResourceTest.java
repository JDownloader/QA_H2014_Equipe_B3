package ca.ulaval.glo4002.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requestparsers.prescription.AddPrescriptionRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.prescription.AddPrescriptionRequestParserFactory;
import ca.ulaval.glo4002.services.prescription.PrescriptionService;

public class PrescriptionResourceTest {
	
	private static final String SAMPLE_JSON_REQUEST = "{attrib: value}";
	
	private PrescriptionService prescriptionServiceMock;
	private AddPrescriptionRequestParser addPrescriptionRequestMock;
	private AddPrescriptionRequestParserFactory addPrescriptionRequestFactoryMock;
	private PrescriptionResource prescriptionResource;
	
	@Before
	public void setup() throws Exception {
		prescriptionServiceMock = mock(PrescriptionService.class);
		addPrescriptionRequestMock = mock(AddPrescriptionRequestParser.class);
		addPrescriptionRequestFactoryMock = mock(AddPrescriptionRequestParserFactory.class);
		when(addPrescriptionRequestFactoryMock.createAddPrescriptionRequest(any(JSONObject.class), anyString())).thenReturn(addPrescriptionRequestMock);
		prescriptionResource = new PrescriptionResource(prescriptionServiceMock, addPrescriptionRequestFactoryMock);
	}
	
	@Test
	public void handlesPostRequestsCorrectly() throws BadRequestException {
		prescriptionResource.post(SAMPLE_JSON_REQUEST);
		verify(prescriptionServiceMock).addPrescription(addPrescriptionRequestMock);
	}
	
	@Test
	public void returnsCreatedResponse() throws BadRequestException {
		Response response = prescriptionResource.post(SAMPLE_JSON_REQUEST);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void returnsInvalidResponseWhenSpecifyingInvalidRequest() throws BadRequestException {
		doThrow(new BadRequestException()).when(prescriptionServiceMock).addPrescription(addPrescriptionRequestMock);
		
		Response response = prescriptionResource.post(SAMPLE_JSON_REQUEST);
		
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}
	
}
