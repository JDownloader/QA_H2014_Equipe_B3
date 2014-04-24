package ca.ulaval.glo4002.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.PatientNotFoundException;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.surgicaltool.*;
import ca.ulaval.glo4002.services.assemblers.InterventionAssembler;
import ca.ulaval.glo4002.services.assemblers.SurgicalToolAssembler;
import ca.ulaval.glo4002.services.dto.*;
import ca.ulaval.glo4002.services.dto.validators.*;

@RunWith(MockitoJUnitRunner.class)
public class InterventionServiceTest {

	private InterventionService interventionService;

	private InterventionRepository interventionRepositoryMock;
	private PatientRepository patientRepositoryMock;
	private SurgicalToolRepository surgicalToolRepositoryMock;
	private EntityManager entityManagerMock;
	private EntityTransaction entityTransactionMock;

	private Intervention interventionMock;
	private SurgicalTool surgicalToolMock;
	private SurgicalToolCreationDTO surgicalToolCreationDTO = new SurgicalToolCreationDTO();
	private SurgicalToolModificationDTO surgicalToolModificationDTO = new SurgicalToolModificationDTO();
	private InterventionCreationDTO interventionCreationDTO = new InterventionCreationDTO();
	private SurgicalToolCreationDTOValidator surgicalToolCreationDTOValidatorMock;
	private SurgicalToolModificationDTOValidator surgicalToolModificationDTOValidatorMock;
	private InterventionCreationDTOValidator interventionCreationDTOValidatorMock;
	private SurgicalToolAssembler surgicalToolFactoryMock;
	private InterventionAssembler interventionAssemblerMock;

	@Before
	public void init() {
		createMocks();
		stubMethods();
		interventionService = new InterventionService(interventionRepositoryMock, patientRepositoryMock, surgicalToolRepositoryMock, entityManagerMock);
	}

	private void createMocks() {
		interventionMock = mock(Intervention.class);
		surgicalToolMock = mock(SurgicalTool.class);
		interventionRepositoryMock = mock(InterventionRepository.class);
		surgicalToolRepositoryMock = mock(SurgicalToolRepository.class);
		patientRepositoryMock = mock(PatientRepository.class);
		entityManagerMock = mock(EntityManager.class);
		entityTransactionMock = mock(EntityTransaction.class);
		surgicalToolCreationDTOValidatorMock = mock(SurgicalToolCreationDTOValidator.class);
		surgicalToolModificationDTOValidatorMock = mock(SurgicalToolModificationDTOValidator.class);
		interventionCreationDTOValidatorMock = mock(InterventionCreationDTOValidator.class);
		surgicalToolFactoryMock = mock(SurgicalToolAssembler.class);
		interventionAssemblerMock = mock(InterventionAssembler.class);
	}

	private void stubMethods() {
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		when(interventionRepositoryMock.getById(anyInt())).thenReturn(interventionMock);
		when(surgicalToolRepositoryMock.getBySerialNumberOrId(anyString())).thenReturn(surgicalToolMock);
		when(interventionAssemblerMock.assembleFromDTO(interventionCreationDTO, patientRepositoryMock)).thenReturn(interventionMock);
		when(surgicalToolFactoryMock.createFromDTO(surgicalToolCreationDTO)).thenReturn(surgicalToolMock);
	}

	@Test
	public void verifyInterventionCreationCallsCorrectRepositoryMethods() throws Exception {
		createIntervention();
		verify(interventionRepositoryMock).persist(interventionMock);
	}

	@Test
	public void verifyInterventionCreationBeginsAndCommitsTransaction() throws Exception {
		createIntervention();
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}

	@Test
	public void verifyInterventionCreationRollsbackOnException() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(true);
		doThrow(new DTOValidationException()).when(interventionCreationDTOValidatorMock).validate(eq(interventionCreationDTO));

		try {
			createIntervention();
		} catch (ServiceRequestException e) {
			verify(entityTransactionMock).rollback();
			return;
		}
	}

	@Test
	public void verifyInterventionCreationDoesNotRollbackOnSuccessfulCommit() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(false);

		createIntervention();

		verify(entityTransactionMock).commit();
		verify(entityTransactionMock, never()).rollback();
	}

	@Test
	public void verifyInterventionCreationThrowsServiceExceptionOnDTOValidationException() throws Exception {
		doThrow(new DTOValidationException()).when(interventionCreationDTOValidatorMock).validate(any(InterventionCreationDTO.class));
		try {
			createIntervention();
			fail("An exception was expected.");
		} catch (ServiceRequestException e) {
			assertEquals(InterventionService.ERROR_INT001, e.getInternalCode());
		}
	}

	@Test
	public void verifyInterventionCreationThrowsServiceExceptionOnPatientNotFoundException() throws Exception {
		when(interventionAssemblerMock.assembleFromDTO(eq(interventionCreationDTO), eq(patientRepositoryMock))).thenThrow(new PatientNotFoundException());
		try {
			createIntervention();
			fail("An exception was expected.");
		} catch (ServiceRequestException e) {
			assertEquals(InterventionService.ERROR_INT002, e.getInternalCode());
		}
	}

	@Test
	public void verifySurgicalToolCreationCallsCorrectRepositoryMethods() throws Exception {
		createSurgicalTool();

		verify(surgicalToolRepositoryMock).persist(any(SurgicalTool.class));
		verify(interventionRepositoryMock).getById(anyInt());
		verify(interventionRepositoryMock).update(interventionMock);
	}

	@Test
	public void verifySurgicalToolCreationCallsCorrectDomainMethods() throws Exception {
		createSurgicalTool();
		verify(interventionMock).addSurgicalTool(surgicalToolMock);
	}

	@Test
	public void verifySurgicalToolReturnsCorrectValue() throws Exception {
		final Integer SAMPLE_ID = 2;
		when(surgicalToolMock.getId()).thenReturn(SAMPLE_ID);

		Integer interventionId = createSurgicalTool();

		assertEquals(SAMPLE_ID, interventionId);
	}

	@Test
	public void verifySurgicalToolCreationBeginsAndCommitsTransaction() throws Exception {
		createSurgicalTool();
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}

	@Test
	public void verifySurgicalToolCreationRollsbackOnException() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(true);
		when(interventionRepositoryMock.getById(anyInt())).thenThrow(new InterventionNotFoundException());

		try {
			createSurgicalTool();
		} catch (ServiceRequestException e) {
			verify(entityTransactionMock).rollback();
			return;
		}
	}

	@Test
	public void verifySurgicalToolCreationDoesNotRollbackOnSuccessfulCommit() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(false);

		createSurgicalTool();

		verify(entityTransactionMock).commit();
		verify(entityTransactionMock, never()).rollback();
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void verifySurgicalToolCreationThrowsServiceExceptionOnDTOValidationException() throws Exception {
		doThrow(new DTOValidationException()).when(surgicalToolCreationDTOValidatorMock).validate(any(SurgicalToolCreationDTO.class));
		try {
			createSurgicalTool();
			fail("An exception was expected.");
		} catch (ServiceRequestException e) {
			assertEquals(InterventionService.ERROR_INT010, e.getInternalCode());
		}
	}

	@Test
	public void verifySurgicalToolCreationThrowsServiceExceptionOnInterventionNotFoundException() throws Exception {
		when(interventionRepositoryMock.getById(anyInt())).thenThrow(new InterventionNotFoundException());
		try {
			createSurgicalTool();
			fail("An exception was expected.");
		} catch (ServiceRequestException e) {
			assertEquals(InterventionService.ERROR_INT010, e.getInternalCode());
		}
	}

	@Test
	public void verifySurgicalToolCreationThrowsServiceExceptionOnSurgicalToolExistsException() throws Exception {
		doThrow(new SurgicalToolExistsException()).when(surgicalToolRepositoryMock).persist(eq(surgicalToolMock));
		try {
			createSurgicalTool();
			fail("An exception was expected.");
		} catch (ServiceRequestException e) {
			assertEquals(InterventionService.ERROR_INT011, e.getInternalCode());
		}

	}

	@Test
	public void verifySurgicalToolCreationThrowsServiceExceptionOnSurgicalToolRequiresSerialNumberException() throws Exception {
		doThrow(new SurgicalToolRequiresSerialNumberException()).when(interventionMock).addSurgicalTool(eq(surgicalToolMock));
		try {
			createSurgicalTool();
			fail("An exception was expected.");
		} catch (ServiceRequestException e) {
			assertEquals(InterventionService.ERROR_INT012, e.getInternalCode());
		}
	}

	@Test
	public void verifySurgicalToolModificationCallsCorrectRepositoryMethods() throws Exception {
		modifySurgicalTool();

		verify(surgicalToolRepositoryMock).getBySerialNumberOrId(anyString());
		verify(interventionRepositoryMock).getById(anyInt());
		verify(surgicalToolRepositoryMock).update(surgicalToolMock);
	}

	@Test
	public void verifySurgicalToolModificationCallsCorrectDomainMethods() throws Exception {
		final String SAMPLE_SERIAL_NUMBER_PARAMETER = "2985023D";
		surgicalToolModificationDTO.newSerialNumber = SAMPLE_SERIAL_NUMBER_PARAMETER;

		modifySurgicalTool();

		verify(surgicalToolMock).setStatus(any(SurgicalToolStatus.class));
		verify(interventionMock).changeSurgicalToolSerialNumber(surgicalToolMock, SAMPLE_SERIAL_NUMBER_PARAMETER);
	}

	@Test
	public void verifySurgicalToolModificationBeginsAndCommitsTransaction() throws Exception {
		modifySurgicalTool();
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}

	@Test
	public void verifySurgicalToolModificationRollsbackOnException() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(true);
		when(patientRepositoryMock.getById(anyInt())).thenThrow(new SurgicalToolNotFoundException());

		try {
			modifySurgicalTool();
		} catch (ServiceRequestException e) {
			verify(entityTransactionMock).rollback();
			return;
		}
	}

	@Test
	public void verifySurgicalToolModificationDoesNotRollbackOnSuccessfulCommit() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(false);

		modifySurgicalTool();

		verify(entityTransactionMock).commit();
		verify(entityTransactionMock, never()).rollback();
	}

	@Test
	public void verifySurgicalToolModificationThrowsServiceExceptionOnDTOValidationException() throws Exception {
		doThrow(new DTOValidationException()).when(surgicalToolModificationDTOValidatorMock).validate(any(SurgicalToolModificationDTO.class));
		try {
			modifySurgicalTool();
			fail("An exception was expected.");
		} catch (ServiceRequestException e) {
			assertEquals(InterventionService.ERROR_INT010, e.getInternalCode());
		}
	}

	@Test
	public void verifySurgicalToolModificationThrowsServiceExceptionOnSurgicalToolNotFoundException() throws Exception {
		when(surgicalToolRepositoryMock.getBySerialNumberOrId(anyString())).thenThrow(new SurgicalToolNotFoundException());
		try {
			modifySurgicalTool();
			fail("An exception was expected.");
		} catch (ServiceRequestException e) {
			assertEquals(InterventionService.ERROR_INT010, e.getInternalCode());
		}
	}

	@Test
	public void verifySurgicalToolModificationThrowsServiceExceptionOnSurgicalToolExistsException() throws Exception {
		doThrow(new SurgicalToolExistsException()).when(surgicalToolRepositoryMock).update(eq(surgicalToolMock));
		try {
			modifySurgicalTool();
			fail("An exception was expected.");
		} catch (ServiceRequestException e) {
			assertEquals(InterventionService.ERROR_INT011, e.getInternalCode());
		}
	}

	@Test
	public void verifySurgicalToolModificationThrowsServiceExceptionOnSurgicalToolRequiresSerialNumberException() throws Exception {
		doThrow(new SurgicalToolRequiresSerialNumberException()).when(interventionMock).changeSurgicalToolSerialNumber(eq(surgicalToolMock), anyString());
		try {
			modifySurgicalTool();
			fail("An exception was expected.");
		} catch (ServiceRequestException e) {
			assertEquals(InterventionService.ERROR_INT012, e.getInternalCode());
		}
	}

	private void createIntervention() {
		interventionService.createIntervention(interventionCreationDTO, interventionCreationDTOValidatorMock, interventionAssemblerMock);
	}

	private Integer createSurgicalTool() {
		return interventionService.createSurgicalTool(surgicalToolCreationDTO, surgicalToolCreationDTOValidatorMock, surgicalToolFactoryMock);
	}

	private void modifySurgicalTool() {
		interventionService.modifySurgicalTool(surgicalToolModificationDTO, surgicalToolModificationDTOValidatorMock);
	}
}
