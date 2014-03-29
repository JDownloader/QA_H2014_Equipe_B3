package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.ulaval.glo4002.domain.prescription.PrescriptionFactory;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.dto.PrescriptionCreationDto;
import ca.ulaval.glo4002.services.patient.PatientService;

public class PatientResourceTest {

	private static final String SAMPLE_JSON_REQUEST = "{attrib: value}";

	private PatientService patientServiceMock;
	private PrescriptionCreationDto prescriptionCreationDtoMock;
	private ObjectMapper objectMapperMock;
	private PatientResource patientResource;

	@Before
	public void init() throws Exception {
		patientServiceMock = mock(PatientService.class);
		prescriptionCreationDtoMock = mock(PrescriptionCreationDto.class);
		objectMapperMock = mock(ObjectMapper.class);
		patientResource = new PatientResource(patientServiceMock, objectMapperMock);
		when(objectMapperMock.readValue(anyString(), eq(PrescriptionCreationDto.class))).thenReturn(prescriptionCreationDtoMock);
	}

	@Test
	public void verifyAddPrescriptionCallsServiceMethodsCorrectly() throws Exception {
		patientResource.post(SAMPLE_JSON_REQUEST);
		verify(patientServiceMock).createPrescription(eq(prescriptionCreationDtoMock), any(PrescriptionFactory.class));
	}

	@Test
	public void verifyAddPrescriptionReturnsCreatedResponse() throws Exception {
		Response response = patientResource.post(SAMPLE_JSON_REQUEST);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyAddPrescriptionReturnsBadRequestResponseWhenSpecifyingInvalidRequest() throws Exception {
		doThrow(new ServiceRequestException()).when(patientServiceMock).createPrescription(eq(prescriptionCreationDtoMock), any(PrescriptionFactory.class));

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = patientResource.post(SAMPLE_JSON_REQUEST);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifyAddPrescriptionReturnsBadRequestResponseWhenSpecifyingInvalidJsonRequestString() throws Exception {
		doThrow(new JsonMappingException("")).when(objectMapperMock).readValue(anyString(), eq(PrescriptionCreationDto.class));

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = patientResource.post(SAMPLE_JSON_REQUEST);
		
		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}


}
