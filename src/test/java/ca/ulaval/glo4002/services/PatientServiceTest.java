package ca.ulaval.glo4002.services;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.drug.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientNotFoundException;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.prescription.Prescription;
import ca.ulaval.glo4002.domain.prescription.PrescriptionRepository;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.services.PatientService;
import ca.ulaval.glo4002.services.assemblers.PrescriptionAssembler;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.PrescriptionCreationDTOValidator;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {
	private PatientService patientService;
	
	private PrescriptionRepository prescriptionRepositoryMock;
	private DrugRepository drugRepositoryMock;
	private PatientRepository patientRepositoryMock;
	private EntityManager entityManagerMock;
	private EntityTransaction entityTransactionMock;

	private Patient patientMock;
	private Prescription prescriptionMock;
	private PrescriptionCreationDTO prescriptionCreationDTO = new PrescriptionCreationDTO();
	private PrescriptionCreationDTOValidator prescriptionCreationDTOValidatorMock;
	private PrescriptionAssembler prescriptionAssemblerMock;

	@Before
	public void init() {
		createMocks();
		stubMethods();
		patientService = new PatientService(patientRepositoryMock, prescriptionRepositoryMock, drugRepositoryMock, entityManagerMock);
	}

	private void createMocks() {
		patientMock = mock(Patient.class);
		prescriptionMock = mock(Prescription.class);
		prescriptionRepositoryMock = mock(PrescriptionRepository.class);
		drugRepositoryMock = mock(DrugRepository.class);
		patientRepositoryMock = mock(PatientRepository.class);
		entityManagerMock = mock(EntityManager.class);
		entityTransactionMock = mock(EntityTransaction.class);;
		prescriptionCreationDTOValidatorMock = mock(PrescriptionCreationDTOValidator.class);
		prescriptionAssemblerMock = mock(PrescriptionAssembler.class);
	}

	private void stubMethods() {
		when(patientRepositoryMock.getById(anyInt())).thenReturn(patientMock);
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
	}

	@Test
	public void verifyPrescriptionCreationCallsCorrectRepositoryMethods() throws Exception {
		patientService.createPrescription(prescriptionCreationDTO, prescriptionCreationDTOValidatorMock, prescriptionAssemblerMock);
		
		verify(prescriptionRepositoryMock).persist(any(Prescription.class));
		verify(patientRepositoryMock).getById(anyInt());
		verify(patientRepositoryMock).update(patientMock);
	}
	
	@Test
	public void verifyPrescriptionCreationCallsCorrectDomainMethods() throws Exception {
		when(prescriptionAssemblerMock.assembleFromDTO(prescriptionCreationDTO, drugRepositoryMock)).thenReturn(prescriptionMock);
		patientService.createPrescription(prescriptionCreationDTO, prescriptionCreationDTOValidatorMock, prescriptionAssemblerMock);
		verify(patientMock).addPrescription(prescriptionMock);
	}

	@Test
	public void verifyPrescriptionCreationBeginsAndCommitsTransaction() throws Exception {
		patientService.createPrescription(prescriptionCreationDTO, prescriptionCreationDTOValidatorMock, prescriptionAssemblerMock);
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}
	
	@Test
	public void verifyPrescriptionCreationRollsbackOnException() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(true);
		when(patientRepositoryMock.getById(anyInt())).thenThrow(new PatientNotFoundException());

		try {
			patientService.createPrescription(prescriptionCreationDTO, prescriptionCreationDTOValidatorMock, prescriptionAssemblerMock);
		} catch(ServiceRequestException e) {
			verify(entityTransactionMock).rollback();
			return;
		}
	}
	
	@Test
	public void verifyPrescriptionCreationDoesNotRollbackOnSuccessfulCommit() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(false);

		patientService.createPrescription(prescriptionCreationDTO, prescriptionCreationDTOValidatorMock, prescriptionAssemblerMock);
		
		verify(entityTransactionMock).commit();
		verify(entityTransactionMock, never()).rollback();
	}
}
