package ca.ulaval.glo4002.services.prescription;

import java.util.Date;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import org.junit.*;
import org.mockito.*;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.prescription.Prescription;
import ca.ulaval.glo4002.domain.prescription.PrescriptionRepository;
import ca.ulaval.glo4002.exceptions.BadRequestException;
import ca.ulaval.glo4002.rest.requests.AddPrescriptionRequest;

public class PrescriptionServiceTest {
	
	private static final Date SAMPLE_DATE = new Date();
	private static final int SAMPLE_RENEWALS_PARAMETER = 2;
	private static final String SAMPLE_DRUG_NAME_PARAMETER = "drug_name";
	private static final int SAMPLE_STAFF_MEMBER_PARAMETER = 3;
	private static final int SAMPLE_DIN_PARAMETER = 3;
	private static final int SAMPLE_PATIENT_NUMBER_PARAMETER = 3;
	
	private PrescriptionRepository prescriptionRepositoryMock;
	private DrugRepository drugRepositoryMock;
	private PatientRepository patientRepositoryMock;
	private EntityTransaction entityTransactionMock;
	private Drug drugMock;
	private Patient patientMock;
	private PrescriptionService prescriptionService;
	private AddPrescriptionRequest addPrescriptionRequestMock;
	
	@Before
	public void setup()  {
		createMocks();
		buildPrescriptionService(); 
		stubAddPrescriptionRequestMockMethods();
		stubRepositoryMethods();
		stubEntityTransactionsMethods();
	}
	
	private void createMocks() {
		prescriptionRepositoryMock = mock(PrescriptionRepository.class);
		drugRepositoryMock = mock(DrugRepository.class);
		patientRepositoryMock = mock(PatientRepository.class);
		drugMock = mock(Drug.class);
		patientMock = mock(Patient.class);
		entityTransactionMock = mock(EntityTransaction.class);
		addPrescriptionRequestMock = mock(AddPrescriptionRequest.class);
	}
	
	private void buildPrescriptionService() {
		PrescriptionServiceBuilder prescriptionServiceBuilder = new PrescriptionServiceBuilder();
		prescriptionServiceBuilder.prescriptionRepository(prescriptionRepositoryMock);
		prescriptionServiceBuilder.drugRepository(drugRepositoryMock);
		prescriptionServiceBuilder.patientRepository(patientRepositoryMock);
		prescriptionServiceBuilder.entityTransaction(entityTransactionMock);
		prescriptionService = prescriptionServiceBuilder.build();
	}
	
	private void stubAddPrescriptionRequestMockMethods() {
		when(addPrescriptionRequestMock.hasDin()).thenReturn(true);
		when(addPrescriptionRequestMock.getDin()).thenReturn(SAMPLE_DIN_PARAMETER);
		when(addPrescriptionRequestMock.getStaffMember()).thenReturn(SAMPLE_STAFF_MEMBER_PARAMETER);
		when(addPrescriptionRequestMock.getPatientNumber()).thenReturn(SAMPLE_PATIENT_NUMBER_PARAMETER);
		when(addPrescriptionRequestMock.getRenewals()).thenReturn(SAMPLE_RENEWALS_PARAMETER);
		when(addPrescriptionRequestMock.getDate()).thenReturn(SAMPLE_DATE);
	}
	
	private void stubAddPrescriptionRequestMockMethodsWithNoDin() {
		when(addPrescriptionRequestMock.hasDin()).thenReturn(false);
		when(addPrescriptionRequestMock.getDrugName()).thenReturn(SAMPLE_DRUG_NAME_PARAMETER);
	}
	
	private void stubRepositoryMethods() {
		when(drugRepositoryMock.getByDin(any(Din.class))).thenReturn(drugMock);
		when(patientRepositoryMock.getById(anyInt())).thenReturn(patientMock);
	}
	
	private void stubEntityTransactionsMethods() {
		when(entityTransactionMock.isActive()).thenReturn(true);
	}
	
	@Test
	public void verifyAddPrescriptionWithDinCallsCorrectRepositoryMethods() throws BadRequestException {
		prescriptionService.addPrescription(addPrescriptionRequestMock);
		
		verify(drugRepositoryMock).getByDin(any(Din.class));
		verify(prescriptionRepositoryMock).create(any(Prescription.class));
		verify(patientRepositoryMock).update(patientMock);
	}
	
	@Test
	public void verifyAddPrescriptionReturnsCorrectResponse() throws BadRequestException {
		prescriptionService.addPrescription(addPrescriptionRequestMock);
	}
	
	@Test
	public void verifyAddPrescriptionWithNoDinCallsCorrectRepositoryMethods() throws BadRequestException {
		stubAddPrescriptionRequestMockMethodsWithNoDin();
		
		prescriptionService.addPrescription(addPrescriptionRequestMock);
		
		verify(drugRepositoryMock, never()).getByDin(any(Din.class));
		verify(prescriptionRepositoryMock).create(any(Prescription.class));
		verify(patientRepositoryMock).update(patientMock);
	}
	
	@Test
	public void verifyTransactionHandling() throws BadRequestException {
		prescriptionService.addPrescription(addPrescriptionRequestMock);
		InOrder inOrder = inOrder(entityTransactionMock);
		
		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}
	
	@Test(expected = BadRequestException.class)
	public void throwsWhenSpecifyingNonExistingDrugDin() throws BadRequestException {
		when(drugRepositoryMock.getByDin(any(Din.class))).thenThrow(new EntityNotFoundException());
		
		prescriptionService.addPrescription(addPrescriptionRequestMock);

		verify(entityTransactionMock).rollback();
	}
	
	@Test(expected = BadRequestException.class)
	public void throwsWhenSpecifyingNonExistingPatientNumber() throws BadRequestException {
		when(patientRepositoryMock.getById(anyInt())).thenThrow(new EntityNotFoundException());
		
		prescriptionService.addPrescription(addPrescriptionRequestMock);

		verify(entityTransactionMock).rollback();
	}
}
