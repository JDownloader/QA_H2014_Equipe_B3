package ca.ulaval.glo4002.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.intervention.Intervention;
import ca.ulaval.glo4002.domain.intervention.InterventionRepository;
import ca.ulaval.glo4002.domain.patient.PatientNotFoundException;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolRepository;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.services.assemblers.InterventionAssembler;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.DTOValidationException;
import ca.ulaval.glo4002.services.dto.validators.InterventionCreationDTOValidator;

@RunWith(MockitoJUnitRunner.class)
public class InterventionServiceTest {
	
	private InterventionService interventionService;
	
	private InterventionRepository interventionRepositoryMock;
	private PatientRepository patientRepositoryMock;
	private SurgicalToolRepository surgicalToolRepositoryMock;
	private EntityManager entityManagerMock;
	private EntityTransaction entityTransactionMock;

	private Intervention interventionMock;
	private InterventionCreationDTO interventionCreationDTO = new InterventionCreationDTO();
	private InterventionCreationDTOValidator interventionCreationDTOValidatorMock;
	private InterventionAssembler interventionAssemblerMock;
	
	@Before
	public void init() {
		createMocks();
		stubMethods();
		interventionService = new InterventionService(interventionRepositoryMock, patientRepositoryMock, surgicalToolRepositoryMock, entityManagerMock);
	}
	
	private void createMocks() {
		interventionMock = mock(Intervention.class);
		interventionRepositoryMock = mock(InterventionRepository.class);
		surgicalToolRepositoryMock = mock(SurgicalToolRepository.class);
		patientRepositoryMock = mock(PatientRepository.class);
		entityManagerMock = mock(EntityManager.class);
		entityTransactionMock = mock(EntityTransaction.class);
		interventionCreationDTOValidatorMock = mock(InterventionCreationDTOValidator.class);
		interventionAssemblerMock = mock(InterventionAssembler.class);
	}

	private void stubMethods() {
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		when(interventionAssemblerMock.assembleFromDTO(interventionCreationDTO, patientRepositoryMock)).thenReturn(interventionMock);
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
		} catch(ServiceRequestException e) {
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
		} catch(ServiceRequestException e) {
			assertEquals(InterventionService.ERROR_INT001, e.getInternalCode());
		}
	}
	
	@Test
	public void verifyInterventionCreationThrowsServiceExceptionOnPatientNotFoundException() throws Exception {
		when(interventionAssemblerMock.assembleFromDTO(eq(interventionCreationDTO), eq(patientRepositoryMock))).thenThrow(new PatientNotFoundException());
		try {
			createIntervention();
			fail("An exception was expected.");
		} catch(ServiceRequestException e) {
			assertEquals(InterventionService.ERROR_INT002, e.getInternalCode());
		}
	}
	
	private void createIntervention() {
		interventionService.createIntervention(interventionCreationDTO, interventionCreationDTOValidatorMock, interventionAssemblerMock);
	}
}
