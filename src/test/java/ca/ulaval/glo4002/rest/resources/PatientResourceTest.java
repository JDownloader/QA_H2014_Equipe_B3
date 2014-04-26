package ca.ulaval.glo4002.rest.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.services.PatientService;
import ca.ulaval.glo4002.services.ServiceException;
import ca.ulaval.glo4002.services.assemblers.PrescriptionAssembler;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.PrescriptionCreationDTOValidator;

@RunWith(MockitoJUnitRunner.class)
public class PatientResourceTest {

	private PatientService patientServiceMock;
	private PrescriptionCreationDTO prescriptionCreationDTOMock;
	private PatientResource patientResource;

	@Before
	public void init() throws Exception {
		patientServiceMock = mock(PatientService.class);
		prescriptionCreationDTOMock = mock(PrescriptionCreationDTO.class);
		patientResource = new PatientResource(patientServiceMock);
	}

	@Test
	public void verifyPrescriptionCreationCallsServiceMethodsCorrectly() throws Exception {
		createPrescription();
		verify(patientServiceMock).createPrescription(eq(prescriptionCreationDTOMock), any(PrescriptionCreationDTOValidator.class),
				any(PrescriptionAssembler.class));
	}

	@Test
	public void verifyPrescriptionCreationReturnsCreatedResponse() throws Exception {
		Response response = createPrescription();
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void verifyPrescriptionCreationReturnsBadRequestResponseOnServiceException() throws Exception {
		doThrow(new ServiceException()).when(patientServiceMock).createPrescription(eq(prescriptionCreationDTOMock),
				any(PrescriptionCreationDTOValidator.class), any(PrescriptionAssembler.class));
		Response receivedResponse = createPrescription();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}

	private Response createPrescription() throws Exception {
		return patientResource.createPrescription(prescriptionCreationDTOMock);
	}
}
