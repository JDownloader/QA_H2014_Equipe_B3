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

import ca.ulaval.glo4002.domain.drug.DrugNotFoundException;
import ca.ulaval.glo4002.domain.patient.DrugInteractionException;
import ca.ulaval.glo4002.domain.patient.PatientNotFoundException;
import ca.ulaval.glo4002.services.PatientService;
import ca.ulaval.glo4002.services.assemblers.PrescriptionAssembler;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.DTOValidationException;
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
		verify(patientServiceMock, times(1)).createPrescription(eq(prescriptionCreationDTOMock), any(PrescriptionCreationDTOValidator.class),
				any(PrescriptionAssembler.class));
	}

	@Test
	public void verifyPrescriptionCreationReturnsCreatedResponseOnSuccess() throws Exception {
		Response response = createPrescription();
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void verifyPrescriptionCreationReturnsBadRequestResponseOnDTOValidationException() {
		doThrow(new DTOValidationException()).when(patientServiceMock).createPrescription(eq(prescriptionCreationDTOMock),
				any(PrescriptionCreationDTOValidator.class), any(PrescriptionAssembler.class));
		Response receivedResponse = createPrescription();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}

	@Test
	public void verifyPrescriptionCreationReturnsBadRequestResponseOnPatientNotFoundException() {
		doThrow(new PatientNotFoundException()).when(patientServiceMock).createPrescription(eq(prescriptionCreationDTOMock),
				any(PrescriptionCreationDTOValidator.class), any(PrescriptionAssembler.class));
		Response receivedResponse = createPrescription();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}

	@Test
	public void verifyPrescriptionCreationReturnsBadRequestResponseOnDrugNotNotFoundException() {
		doThrow(new DrugNotFoundException()).when(patientServiceMock).createPrescription(eq(prescriptionCreationDTOMock),
				any(PrescriptionCreationDTOValidator.class), any(PrescriptionAssembler.class));
		Response receivedResponse = createPrescription();
		assertEquals(Status.BAD_REQUEST.getStatusCode(), receivedResponse.getStatus());
	}
	
	@Test
	public void verifyPrescriptionCreationReturnsConflictResponseOnDrugInteractionException() {
		doThrow(new DrugInteractionException()).when(patientServiceMock).createPrescription(eq(prescriptionCreationDTOMock),
				any(PrescriptionCreationDTOValidator.class), any(PrescriptionAssembler.class));
		Response receivedResponse = createPrescription();
		assertEquals(Status.CONFLICT.getStatusCode(), receivedResponse.getStatus());
	}

	private Response createPrescription() {
		return patientResource.createPrescription(prescriptionCreationDTOMock);
	}
}
