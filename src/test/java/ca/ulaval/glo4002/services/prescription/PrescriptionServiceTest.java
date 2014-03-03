package ca.ulaval.glo4002.services.prescription;

import java.util.Date;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import org.junit.*;
import org.mockito.*;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugDontHaveDinExeption;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.prescription.Prescription;
import ca.ulaval.glo4002.domain.prescription.PrescriptionRepository;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.prescription.AddPrescriptionRequestParser;

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
	private AddPrescriptionRequestParser addPrescriptionRequestParserMock;
	
	@Before
	//TODO verify logic, init should'nt thow
	public void init() throws EntityNotFoundException, DrugDontHaveDinExeption  {
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
		addPrescriptionRequestParserMock = mock(AddPrescriptionRequestParser.class);
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
	
	private void stubRepositoryMethods() throws EntityNotFoundException, DrugDontHaveDinExeption {
		when(drugRepositoryMock.getByDin(any(Din.class))).thenReturn(drugMock);
		when(patientRepositoryMock.getById(anyInt())).thenReturn(patientMock);
	}
	
	private void stubEntityTransactionsMethods() {
		when(entityTransactionMock.isActive()).thenReturn(true);
	}
	
	@Test
	public void verifyAddPrescriptionCallsCorrectRepositoryMethods() throws ServiceRequestException, DrugDontHaveDinExeption {
		prescriptionService.addPrescription(addPrescriptionRequestParserMock);
		
		verify(drugRepositoryMock).getByDin(any(Din.class));
		verify(prescriptionRepositoryMock).create(any(Prescription.class));
		verify(patientRepositoryMock).update(patientMock);
	}
	
	@Test
	public void verifyAddPrescriptionWithNoDinCallsCorrectRepositoryMethods() throws ServiceRequestException, DrugDontHaveDinExeption {
		stubAddPrescriptionRequestMockMethodsWithNoDin();
		
		prescriptionService.addPrescription(addPrescriptionRequestParserMock);
		
		verify(drugRepositoryMock, never()).getByDin(any(Din.class));
		verify(prescriptionRepositoryMock).create(any(Prescription.class));
		verify(patientRepositoryMock).update(patientMock);
	}
	
	@Test
	public void verifyAddPrescriptionTransactionHandling() throws ServiceRequestException, DrugDontHaveDinExeption {
		prescriptionService.addPrescription(addPrescriptionRequestParserMock);
		InOrder inOrder = inOrder(entityTransactionMock);
		
		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}
	
	@Test(expected = ServiceRequestException.class)
	public void verifyAddPrescriptionThrowsWhenSpecifyingNonExistingDrugDin() throws ServiceRequestException, DrugDontHaveDinExeption {
		when(drugRepositoryMock.getByDin(any(Din.class))).thenThrow(new EntityNotFoundException());
		
		prescriptionService.addPrescription(addPrescriptionRequestParserMock);

		verify(entityTransactionMock).rollback();
	}
	
	@Test(expected = ServiceRequestException.class)
	public void verifyAddPrescriptionThrowsWhenSpecifyingNonExistingPatientNumber() throws ServiceRequestException, DrugDontHaveDinExeption {
		when(patientRepositoryMock.getById(anyInt())).thenThrow(new EntityNotFoundException());
		
		prescriptionService.addPrescription(addPrescriptionRequestParserMock);

		verify(entityTransactionMock).rollback();
	}
}
