package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.services.PatientService;
import ca.ulaval.glo4002.services.assemblers.PrescriptionAssembler;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDto;
import ca.ulaval.glo4002.services.dto.validators.PrescriptionCreationDtoValidator;

public class PatientResourceTest {

	private PatientService patientServiceMock;
	private PrescriptionCreationDto prescriptionCreationDtoMock;
	private PatientResource patientResource;

	@Before
	public void init() throws Exception {
		patientServiceMock = mock(PatientService.class);
		prescriptionCreationDtoMock = mock(PrescriptionCreationDto.class);
		patientResource = new PatientResource(patientServiceMock);
	}

	@Test
	public void verifyPrescriptionCreationCallsServiceMethodsCorrectly() throws Exception {
		patientResource.post(prescriptionCreationDtoMock);
		verify(patientServiceMock).createPrescription(eq(prescriptionCreationDtoMock), any(PrescriptionCreationDtoValidator.class), any(PrescriptionAssembler.class));
	}

	@Test
	public void verifyPrescriptionCreationReturnsCreatedResponse() throws Exception {
		Response response = patientResource.post(prescriptionCreationDtoMock);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyPrescriptionCreationReturnsBadRequestResponseWhenSpecifyingInvalidRequest() throws Exception {
		doThrow(new ServiceRequestException()).when(patientServiceMock).createPrescription(eq(prescriptionCreationDtoMock), any(PrescriptionCreationDtoValidator.class), any(PrescriptionAssembler.class));

		Response expectedResponse = Response.status(Status.BAD_REQUEST).build();
		Response receivedResponse = patientResource.post(prescriptionCreationDtoMock);

		assertEquals(expectedResponse.getStatus(), receivedResponse.getStatus());
	}

}
