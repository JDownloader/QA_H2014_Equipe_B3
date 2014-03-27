package ca.ulaval.glo4002.services.prescription;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.drug.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.prescription.Prescription;
import ca.ulaval.glo4002.domain.prescription.PrescriptionRepository;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.prescription.PrescriptionCreationRequestParser;

@RunWith(MockitoJUnitRunner.class)
public class PrescriptionServiceTest {

	 @Mock private PrescriptionRepository prescriptionRepository;
	 @Mock private DrugRepository drugRepository;
	 @Mock private PatientRepository patientRepository;
	 @Mock private EntityTransaction entityTransaction;
	 @InjectMocks private PrescriptionService prescriptionService;
	
	
	private static final Date SAMPLE_DATE = new Date();
	private static final int SAMPLE_RENEWALS_PARAMETER = 2;
	private static final String SAMPLE_DRUG_NAME_PARAMETER = "drug_name";
	private static final int SAMPLE_STAFF_MEMBER_PARAMETER = 3;
	private static final int SAMPLE_DIN_PARAMETER = 3;
	private static final int SAMPLE_PATIENT_NUMBER_PARAMETER = 3;

	private Drug drugMock;
	private Patient patientMock;
	private PrescriptionCreationRequestParser addPrescriptionRequestParserMock;

	@Before
	// TODO verify logic, init shouldn't thow
	public void init() throws Exception {
		createMocks();
		stubAddPrescriptionRequestMockMethods();
		stubRepositoryMethods();
	}

	private void createMocks() {
		drugMock = mock(Drug.class);
		patientMock = mock(Patient.class);
		addPrescriptionRequestParserMock = mock(PrescriptionCreationRequestParser.class);
	}

	private void stubAddPrescriptionRequestMockMethods() {
		when(addPrescriptionRequestParserMock.hasDin()).thenReturn(true);
		when(addPrescriptionRequestParserMock.getDin()).thenReturn(SAMPLE_DIN_PARAMETER);
		when(addPrescriptionRequestParserMock.getStaffMember()).thenReturn(SAMPLE_STAFF_MEMBER_PARAMETER);
		when(addPrescriptionRequestParserMock.getPatientNumber()).thenReturn(SAMPLE_PATIENT_NUMBER_PARAMETER);
		when(addPrescriptionRequestParserMock.getRenewals()).thenReturn(SAMPLE_RENEWALS_PARAMETER);
		when(addPrescriptionRequestParserMock.getDate()).thenReturn(SAMPLE_DATE);
	}

	private void stubAddPrescriptionRequestMockMethodsWithNoDin() {
		when(addPrescriptionRequestParserMock.hasDin()).thenReturn(false);
		when(addPrescriptionRequestParserMock.getDrugName()).thenReturn(SAMPLE_DRUG_NAME_PARAMETER);
	}

	private void stubRepositoryMethods() throws Exception {
		when(drugRepository.getByDin(any(Din.class))).thenReturn(drugMock);
		when(patientRepository.getById(anyInt())).thenReturn(patientMock);
	}

	@Test
	public void verifyAddPrescriptionCallsCorrectRepositoryMethods() throws Exception {
		prescriptionService.addPrescription(addPrescriptionRequestParserMock);

		verify(drugRepository).getByDin(any(Din.class));
		verify(prescriptionRepository).persist(any(Prescription.class));
		verify(patientRepository).update(patientMock);
	}

	@Test
	public void verifyAddPrescriptionWithNoDinCallsCorrectRepositoryMethods() throws Exception {
		stubAddPrescriptionRequestMockMethodsWithNoDin();

		prescriptionService.addPrescription(addPrescriptionRequestParserMock);

		verify(drugRepository, never()).getByDin(any(Din.class));
		verify(prescriptionRepository).persist(any(Prescription.class));
		verify(patientRepository).update(patientMock);
	}

	@Test
	public void verifyAddPrescriptionBeginsAndCommitsTransaction() throws Exception {
		prescriptionService.addPrescription(addPrescriptionRequestParserMock);
		InOrder inOrder = inOrder(entityTransaction);

		inOrder.verify(entityTransaction).begin();
		inOrder.verify(entityTransaction).commit();
	}

	@Test(expected = ServiceRequestException.class)
	public void verifyAddPrescriptionThrowsWhenSpecifyingNonExistingDrugDin() throws Exception {
		when(drugRepository.getByDin(any(Din.class))).thenThrow(new EntityNotFoundException());

		prescriptionService.addPrescription(addPrescriptionRequestParserMock);
	}

	@Test(expected = ServiceRequestException.class)
	public void verifyAddPrescriptionThrowsWhenSpecifyingNonExistingPatientNumber() throws Exception {
		when(patientRepository.getById(anyInt())).thenThrow(new EntityNotFoundException());

		prescriptionService.addPrescription(addPrescriptionRequestParserMock);
	}
	
	@Test
	public void verifyAddPrescriptionRollsbackOnException() throws Exception {
		when(entityTransaction.isActive()).thenReturn(true);
		when(patientRepository.getById(anyInt())).thenThrow(new EntityNotFoundException());

		try {
			prescriptionService.addPrescription(addPrescriptionRequestParserMock);
		} catch(ServiceRequestException e) {
			verify(entityTransaction).rollback();
			return;
		}
	}
	
	@Test
	public void verifyAddPrescriptionDoesNotRollbackOnSuccessfulCommit() throws Exception {
		when(entityTransaction.isActive()).thenReturn(false);

		prescriptionService.addPrescription(addPrescriptionRequestParserMock);
		
		verify(entityTransaction).commit();
		verify(entityTransaction, never()).rollback();
	}
}
