package ca.ulaval.glo4002.services.intervention;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import javax.persistence.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.surgicaltool.*;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.*;

@RunWith(MockitoJUnitRunner.class)
public class InterventionServiceTest {

	private static final Date SAMPLE_DATE = new Date();
	private static final String SAMPLE_DESCRIPTION = "description";
	private static final String SAMPLE_ROOM = "room";
	private static final InterventionType SAMPLE_TYPE = InterventionType.MOELLE;
	private static final InterventionStatus SAMPLE_INTERVENTION_STATUS = InterventionStatus.EN_COURS;
	private static final int SAMPLE_PATIENT = 2;
	private static final int SAMPLE_SURGEON = 101224;
	private static final int SAMPLE_INTERVENTION_NUMBER = 3;
	private static final String SAMPLE_SERIAL_NUMBER = "684518TF";
	private static final String SAMPLE_NEW_SERIAL_NUMBER = "684518TF";
	private static final String SAMPLE_TYPE_CODE = "56465T";
	private static final String ANOTHER_SAMPLE_TYPE_CODE = "56462T";
	private static final SurgicalToolStatus SAMPLE_SURGICAL_TOOL_STATUS = SurgicalToolStatus.INUTILISE;

	private InterventionRepository interventionRepositoryMock;
	private PatientRepository patientRepositoryMock;
	private SurgicalToolRepository surgicalToolRepositoryMock;
	private EntityTransaction entityTransactionMock;
	private Patient patientMock;
	private SurgicalTool surgicalToolMock;
	private Intervention interventionMock;
	private InterventionService interventionService;

	private CreateInterventionRequestParser createInterventionRequestParserMock;
	private CreateSurgicalToolRequestParser createSurgicalToolRequestParserMock;
	private ModifySurgicalToolRequestParser modifySurgicalToolRequestParserMock;

	@Before
	public void init() {
		createMocks();
		buildInterventionService();
		stubCreateInterventionRequestMockMethods();
		stubSurgicalToolRequestMockMethods(createSurgicalToolRequestParserMock);
		stubSurgicalToolRequestMockMethods(modifySurgicalToolRequestParserMock);
		stubModifySurgicalToolRequestMockMethods(modifySurgicalToolRequestParserMock);
		stubRepositoryMethods();
		stubEntityTransactionsMethods();
	}

	private void createMocks() {
		interventionRepositoryMock = mock(InterventionRepository.class);
		patientRepositoryMock = mock(PatientRepository.class);
		surgicalToolRepositoryMock = mock(SurgicalToolRepository.class);
		patientMock = mock(Patient.class);
		interventionMock = mock(Intervention.class);
		surgicalToolMock = mock(SurgicalTool.class);
		entityTransactionMock = mock(EntityTransaction.class);
		createInterventionRequestParserMock = mock(CreateInterventionRequestParser.class);
		createSurgicalToolRequestParserMock = mock(CreateSurgicalToolRequestParser.class);
		modifySurgicalToolRequestParserMock = mock(ModifySurgicalToolRequestParser.class);
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
		when(createInterventionRequestParserMock.getStatus()).thenReturn(SAMPLE_INTERVENTION_STATUS);
		when(createInterventionRequestParserMock.getSurgeon()).thenReturn(SAMPLE_SURGEON);
		when(createInterventionRequestParserMock.getType()).thenReturn(SAMPLE_TYPE);
	}

	private void stubSurgicalToolRequestMockMethods(SurgicalToolRequestParser surgicalTooolRequestParserMock) {
		when(surgicalTooolRequestParserMock.getSerialNumber()).thenReturn(SAMPLE_SERIAL_NUMBER);
		when(surgicalTooolRequestParserMock.hasSerialNumber()).thenReturn(true);
		when(surgicalTooolRequestParserMock.getTypeCode()).thenReturn(SAMPLE_TYPE_CODE);
		when(surgicalTooolRequestParserMock.getStatus()).thenReturn(SAMPLE_SURGICAL_TOOL_STATUS);
		when(surgicalTooolRequestParserMock.getInterventionNumber()).thenReturn(SAMPLE_INTERVENTION_NUMBER);
	}

	private void stubModifySurgicalToolRequestMockMethods(ModifySurgicalToolRequestParser modifySurgicalTooolRequestParserMock) {
		when(modifySurgicalToolRequestParserMock.getNewSerialNumber()).thenReturn(SAMPLE_NEW_SERIAL_NUMBER);
	}

	private void stubRepositoryMethods() {
		when(patientRepositoryMock.getById(anyInt())).thenReturn(patientMock);
		when(interventionRepositoryMock.getById(anyInt())).thenReturn(interventionMock);
		when(surgicalToolRepositoryMock.getBySerialNumber(anyString())).thenReturn(surgicalToolMock);
		when(surgicalToolMock.getTypeCode()).thenReturn(SAMPLE_TYPE_CODE);
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

	@Test
	public void verifyCreateSurgicalToolCallsCorrectRepositoryMethods() throws ServiceRequestException {
		doThrow(new EntityNotFoundException()).when(surgicalToolRepositoryMock).getBySerialNumber(anyString());

		interventionService.createSurgicalTool(createSurgicalToolRequestParserMock);

		verify(interventionRepositoryMock).getById(SAMPLE_INTERVENTION_NUMBER);
		verify(interventionRepositoryMock).update(any(Intervention.class));
		verify(surgicalToolRepositoryMock).getBySerialNumber(anyString());
		verify(surgicalToolRepositoryMock).create(any(SurgicalTool.class));
	}

	@Test
	public void verifyCreateSurgicalToolTransactionHandling() throws ServiceRequestException {
		doThrow(new EntityNotFoundException()).when(surgicalToolRepositoryMock).getBySerialNumber(anyString());

		interventionService.createSurgicalTool(createSurgicalToolRequestParserMock);
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}

	@Test(expected = ServiceRequestException.class)
	public void verifyCreateSurgicalToolThrowsWhenSerialNumberAlreadyExists() throws ServiceRequestException {
		doThrow(new EntityExistsException()).when(surgicalToolRepositoryMock).create(any(SurgicalTool.class));

		interventionService.createSurgicalTool(createSurgicalToolRequestParserMock);

		verify(entityTransactionMock).rollback();
	}

	@Test(expected = ServiceRequestException.class)
	public void verifyCreateSurgicalToolThrowsWhenOmittingRequiredSerialNumber() throws ServiceRequestException {
		when(modifySurgicalToolRequestParserMock.hasSerialNumber()).thenReturn(false);
		when(interventionMock.getType()).thenReturn(InterventionType.COEUR);

		interventionService.createSurgicalTool(createSurgicalToolRequestParserMock);

		verify(entityTransactionMock).rollback();
	}

	@Test
	public void verifyModifySurgicalToolCallsCorrectRepositoryMethods() throws ServiceRequestException {
		interventionService.modifySurgicalTool(modifySurgicalToolRequestParserMock);

		verify(surgicalToolRepositoryMock).getBySerialNumber(SAMPLE_SERIAL_NUMBER);
		verify(interventionRepositoryMock).getById(SAMPLE_INTERVENTION_NUMBER);
		verify(surgicalToolRepositoryMock).update(any(SurgicalTool.class));
	}

	@Test
	public void verifyModifySurgicalToolTransactionHandling() throws ServiceRequestException {
		interventionService.modifySurgicalTool(modifySurgicalToolRequestParserMock);
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}

	@Test(expected = ServiceRequestException.class)
	public void verifyModifySurgicalToolThrowsWhenSerialNumberAlreadyExists() throws ServiceRequestException {
		doThrow(new EntityExistsException()).when(surgicalToolRepositoryMock).update(any(SurgicalTool.class));

		interventionService.modifySurgicalTool(modifySurgicalToolRequestParserMock);

		verify(entityTransactionMock).rollback();
	}

	@Test(expected = ServiceRequestException.class)
	public void verifyModifySurgicalToolThrowsWhenOmittingRequiredSerialNumber() throws ServiceRequestException {
		when(modifySurgicalToolRequestParserMock.hasSerialNumber()).thenReturn(false);
		when(interventionMock.getType()).thenReturn(InterventionType.COEUR);

		interventionService.modifySurgicalTool(modifySurgicalToolRequestParserMock);

		verify(entityTransactionMock).rollback();
	}

	@Test(expected = ServiceRequestException.class)
	public void verifyModifySurgicalToolThrowsWhenTypeCodeDiffers() throws ServiceRequestException {
		when(surgicalToolMock.getTypeCode()).thenReturn(ANOTHER_SAMPLE_TYPE_CODE);

		interventionService.modifySurgicalTool(modifySurgicalToolRequestParserMock);

		verify(entityTransactionMock).rollback();
	}
}
