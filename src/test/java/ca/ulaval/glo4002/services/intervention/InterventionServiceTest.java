package ca.ulaval.glo4002.services.intervention;

import java.util.Date;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import org.junit.*;
import org.mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.*;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolRepository;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;

@RunWith(MockitoJUnitRunner.class)
public class InterventionServiceTest {
	
	private static final Date SAMPLE_DATE = new Date();
	private static final String SAMPLE_DESCRIPTION = "description";
	private static final String SAMPLE_ROOM = "room";
	private static final InterventionType SAMPLE_TYPE = InterventionType.MOELLE;
	private static final InterventionStatus SAMPLE_STATUS = InterventionStatus.EN_COURS;
	private static final int SAMPLE_PATIENT = 2;
	private static final int SAMPLE_SURGEON = 101224;
	
	private InterventionRepository interventionRepositoryMock;
	private PatientRepository patientRepositoryMock;
	private SurgicalToolRepository surgicalToolRepositoryMock;
	private EntityTransaction entityTransactionMock;
	private Patient patientMock;
	private InterventionService interventionService;
	
	private CreateInterventionRequestParser createInterventionRequestParserMock;
	
	@Before
	public void setup()  {
		createMocks();
		buildInterventionService();
		stubCreateInterventionRequestMockMethods();
		stubRepositoryMethods();
		stubEntityTransactionsMethods();
	}
	
	private void createMocks() {
		interventionRepositoryMock = mock(InterventionRepository.class);
		patientRepositoryMock = mock(PatientRepository.class);
		surgicalToolRepositoryMock = mock(SurgicalToolRepository.class);
		patientMock = mock(Patient.class);
		entityTransactionMock = mock(EntityTransaction.class);
		createInterventionRequestParserMock = mock(CreateInterventionRequestParser.class);
	}
	
	private void buildInterventionService() {
		InterventionServiceBuilder interventionServiceBuilder = new InterventionServiceBuilder();
		interventionServiceBuilder.interventionRepository(interventionRepositoryMock);
		interventionServiceBuilder.patientRepository(patientRepositoryMock);
		interventionServiceBuilder.surgicalToolRepository(surgicalToolRepositoryMock);
		interventionServiceBuilder.entityTransaction(entityTransactionMock);
		interventionService = interventionServiceBuilder.build();
	}
	
	private void stubCreateInterventionRequestMockMethods() {
		when(createInterventionRequestParserMock.getDate()).thenReturn(SAMPLE_DATE);
		when(createInterventionRequestParserMock.getDescription()).thenReturn(SAMPLE_DESCRIPTION);
		when(createInterventionRequestParserMock.getPatient()).thenReturn(SAMPLE_PATIENT);
		when(createInterventionRequestParserMock.getRoom()).thenReturn(SAMPLE_ROOM);
		when(createInterventionRequestParserMock.getStatus()).thenReturn(SAMPLE_STATUS);
		when(createInterventionRequestParserMock.getSurgeon()).thenReturn(SAMPLE_SURGEON);
		when(createInterventionRequestParserMock.getType()).thenReturn(SAMPLE_TYPE);
	}
	
	private void stubRepositoryMethods() {
		when(patientRepositoryMock.getById(anyInt())).thenReturn(patientMock);
	}
	
	private void stubEntityTransactionsMethods() {
		when(entityTransactionMock.isActive()).thenReturn(true);
	}
	
	@Test
	public void verifyCreateInterventionCallsCorrectRepositoryMethods() throws ServiceRequestException {
		interventionService.createIntervention(createInterventionRequestParserMock);
		
		verify(patientRepositoryMock).getById(SAMPLE_PATIENT);
		verify(interventionRepositoryMock).create(any(Intervention.class));
	}
	
	@Test
	public void verifyCreateInterventionTransactionHandling() throws ServiceRequestException {
		interventionService.createIntervention(createInterventionRequestParserMock);
		InOrder inOrder = inOrder(entityTransactionMock);
		
		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}
	
	@Test(expected = ServiceRequestException.class)
	public void verifyCreateInterventionThrowsWhenSpecifyingNonExistingPatientNumber() throws ServiceRequestException {
		when(patientRepositoryMock.getById(anyInt())).thenThrow(new EntityNotFoundException());
		
		interventionService.createIntervention(createInterventionRequestParserMock);

		verify(entityTransactionMock).rollback();
	}

//	@Rule public ExpectedException thrown=ExpectedException.none();
//	
//	@Test
//	public void sendInAValidMarkNewInstrumentRequest() {
//		assertTrue(false);
//	}
//	
//	@Test
//	public void sendRequestForAlreadyFiledSerialNumber() {
//		assertTrue(false);
//	}
//	
//	@Test
//	public void sendInAValidMarkExistingRequest() {
//		assertTrue(false);
//	}
}
