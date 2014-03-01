package ca.ulaval.glo4002.rest;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.rest.requests.AddPrescriptionRequest;
import ca.ulaval.glo4002.rest.requests.AddPrescriptionRequestFactory;
import ca.ulaval.glo4002.services.prescription.PrescriptionService;

public class PrescriptionResourceTest {
	
	private static final String SAMPLE_JSON_REQUEST = "{attrib: value}";
	
	private PrescriptionService prescriptionServiceMock;
	private AddPrescriptionRequest addPrescriptionRequestMock;
	private AddPrescriptionRequestFactory addPrescriptionRequestFactoryMock;
	private PrescriptionResource prescriptionResource;
	
	@Before
	public void setup() throws Exception {
		prescriptionServiceMock = mock(PrescriptionService.class);
		addPrescriptionRequestMock = mock(AddPrescriptionRequest.class);
		addPrescriptionRequestFactoryMock = mock(AddPrescriptionRequestFactory.class);
		when(addPrescriptionRequestFactoryMock.createAddprescriptionRequest(any(JSONObject.class), anyString())).thenReturn(addPrescriptionRequestMock);
		prescriptionResource = new PrescriptionResource(prescriptionServiceMock, addPrescriptionRequestFactoryMock);
	}
	
	@Test
	public void handlesPostRequestsCorrectly() {
		prescriptionResource.post(SAMPLE_JSON_REQUEST);
		verify(prescriptionServiceMock).addPrescription(addPrescriptionRequestMock);
	}
	
}
