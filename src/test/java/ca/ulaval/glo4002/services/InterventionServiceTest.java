package ca.ulaval.glo4002.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;
import ca.ulaval.glo4002.services.assemblers.InterventionAssembler;
import ca.ulaval.glo4002.services.assemblers.SurgicalToolAssembler;
import ca.ulaval.glo4002.services.dto.*;
import ca.ulaval.glo4002.services.dto.validators.*;
 
@RunWith(MockitoJUnitRunner.class)
public class InterventionServiceTest {

	private InterventionService interventionService;

	private InterventionRepository interventionRepositoryMock;
	private PatientRepository patientRepositoryMock;
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
	private InterventionFactory interventionFactoryMock;

	@Before
	public void init() {
		createMocks();
		stubMethods();
		setupDTOs();
		interventionService = new InterventionService(interventionRepositoryMock, patientRepositoryMock, entityManagerMock);
	}

	private void createMocks() {
		interventionMock = mock(Intervention.class);
		surgicalToolMock = mock(SurgicalTool.class);
		interventionRepositoryMock = mock(InterventionRepository.class);
		patientRepositoryMock = mock(PatientRepository.class);
		entityManagerMock = mock(EntityManager.class);
		entityTransactionMock = mock(EntityTransaction.class);
		surgicalToolCreationDTOValidatorMock = mock(SurgicalToolCreationDTOValidator.class);
		surgicalToolModificationDTOValidatorMock = mock(SurgicalToolModificationDTOValidator.class);
		interventionCreationDTOValidatorMock = mock(InterventionCreationDTOValidator.class);
		surgicalToolFactoryMock = mock(SurgicalToolAssembler.class);
		interventionAssemblerMock = mock(InterventionAssembler.class);
		interventionFactoryMock = mock(InterventionFactory.class);
	}

	private void stubMethods() {
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		when(interventionRepositoryMock.getById(anyInt())).thenReturn(interventionMock);
		when(interventionMock.getSurgicalToolBySerialNumberOrId(anyString())).thenReturn(surgicalToolMock);
		when(interventionAssemblerMock.assembleFromDTO(interventionCreationDTO, interventionFactoryMock, patientRepositoryMock)).thenReturn(interventionMock);
		when(surgicalToolFactoryMock.assembleFromDTO(surgicalToolCreationDTO)).thenReturn(surgicalToolMock);
	}
	
	private void setupDTOs() {
		surgicalToolModificationDTO.newStatus = SurgicalToolStatus.UNUSED.getValue();
	}

	@Test
	public void verifyInterventionCreationCallsCorrectRepositoryMethods() {
		createIntervention();
		verify(interventionRepositoryMock, times(1)).persist(interventionMock);
	}

	@Test
	public void verifyInterventionCreationBeginsAndCommitsTransaction() {
		createIntervention();
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock, times(1)).begin();
		inOrder.verify(entityTransactionMock, times(1)).commit();
	}

	@Test
	public void verifyInterventionCreationRollsbackOnException() {
		when(entityTransactionMock.isActive()).thenReturn(true);
		doThrow(new RuntimeException()).when(interventionCreationDTOValidatorMock).validate(eq(interventionCreationDTO));

		try {
			createIntervention();
		} catch (RuntimeException e) {
			verify(entityTransactionMock, times(1)).rollback();
			return;
		}
	}

	@Test
	public void verifyInterventionCreationDoesNotRollbackOnSuccessfulCommit() {
		when(entityTransactionMock.isActive()).thenReturn(false);

		createIntervention();

		verify(entityTransactionMock, times(1)).commit();
		verify(entityTransactionMock, never()).rollback();
	}

	@Test
	public void verifySurgicalToolCreationCallsCorrectRepositoryMethods() {
		createSurgicalTool();

		verify(interventionRepositoryMock, times(1)).getById(anyInt());
		verify(interventionRepositoryMock, times(1)).persist(interventionMock);
	}

	@Test
	public void verifySurgicalToolCreationCallsCorrectDomainMethods() {
		createSurgicalTool();
		verify(interventionMock, times(1)).addSurgicalTool(surgicalToolMock);
	}

	@Test
	public void verifySurgicalToolCreationReturnsCorrectValue() {
		final Integer SAMPLE_ID = 2;
		when(surgicalToolMock.getId()).thenReturn(SAMPLE_ID);

		Integer interventionId = createSurgicalTool();

		assertEquals(SAMPLE_ID, interventionId);
	}

	@Test
	public void verifySurgicalToolCreationBeginsAndCommitsTransaction() {
		createSurgicalTool();
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock, times(1)).begin();
		inOrder.verify(entityTransactionMock, times(1)).commit();
	}

	@Test
	public void verifySurgicalToolCreationRollsbackOnException() {
		when(entityTransactionMock.isActive()).thenReturn(true);
		when(interventionRepositoryMock.getById(anyInt())).thenThrow(new RuntimeException());

		try {
			createSurgicalTool();
		} catch (RuntimeException e) {
			verify(entityTransactionMock, times(1)).rollback();
			return;
		}
	}

	@Test
	public void verifySurgicalToolCreationDoesNotRollbackOnSuccessfulCommit() {
		when(entityTransactionMock.isActive()).thenReturn(false);

		createSurgicalTool();

		verify(entityTransactionMock).commit();
		verify(entityTransactionMock, never()).rollback();
	}

	@Test
	public void verifySurgicalToolModificationCallsCorrectRepositoryMethods() {
		modifySurgicalTool();

		verify(interventionRepositoryMock, times(1)).getById(anyInt());
		verify(interventionRepositoryMock, times(1)).persist(interventionMock);
	}

	@Test
	public void verifySurgicalToolModificationCallsCorrectDomainMethods() {
		modifySurgicalTool();

		verify(interventionMock, times(1)).getSurgicalToolBySerialNumberOrId(anyString());
		verify(surgicalToolMock, times(1)).setStatus(any(SurgicalToolStatus.class));
		verify(surgicalToolMock, times(1)).changeSerialNumber(anyString());
	}

	@Test
	public void verifySurgicalToolModificationBeginsAndCommitsTransaction() {
		modifySurgicalTool();
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock, times(1)).begin();
		inOrder.verify(entityTransactionMock, times(1)).commit();
	}

	@Test
	public void verifySurgicalToolModificationRollsbackOnException() {
		when(entityTransactionMock.isActive()).thenReturn(true);
		when(interventionMock.getSurgicalToolBySerialNumberOrId(anyString())).thenThrow(new RuntimeException());

		try {
			modifySurgicalTool();
		} catch (RuntimeException e) {
			verify(entityTransactionMock, times(1)).rollback();
			return;
		}
	}

	@Test
	public void verifySurgicalToolModificationDoesNotRollbackOnSuccessfulCommit() {
		when(entityTransactionMock.isActive()).thenReturn(false);

		modifySurgicalTool();

		verify(entityTransactionMock, times(1)).commit();
		verify(entityTransactionMock, never()).rollback();
	}

	private void createIntervention() {
		interventionService.createIntervention(interventionCreationDTO, interventionCreationDTOValidatorMock, interventionAssemblerMock, interventionFactoryMock);
	}

	private Integer createSurgicalTool() {
		return interventionService.createSurgicalTool(surgicalToolCreationDTO, surgicalToolCreationDTOValidatorMock, surgicalToolFactoryMock);
	}

	private void modifySurgicalTool() {
		interventionService.modifySurgicalTool(surgicalToolModificationDTO, surgicalToolModificationDTOValidatorMock);
	}
}
