package ca.ulaval.glo4002.services.intervention;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.InOrder;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.surgicaltool.*;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.services.assemblers.SurgicalToolAssembler;
import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;
import ca.ulaval.glo4002.services.dto.SurgicalToolModificationDTO;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolCreationDTOValidator;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolModificationDTOValidator;

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
	private SurgicalToolCreationDTOValidator surgicalToolCreationDTOValidatorMock;
	private SurgicalToolModificationDTOValidator surgicalToolModificationDTOValidatorMock;
	private SurgicalToolAssembler surgicalToolAssemblerMock;

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
		surgicalToolAssemblerMock = mock(SurgicalToolAssembler.class);
	}

	private void stubMethods() {
		when(interventionRepositoryMock.getById(anyInt())).thenReturn(interventionMock);
		when(surgicalToolRepositoryMock.getBySerialNumberOrId(anyString())).thenReturn(surgicalToolMock);
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		when(surgicalToolAssemblerMock.assembleFromDTO(surgicalToolCreationDTO)).thenReturn(surgicalToolMock);
	}
	
	@Test
	public void verifySurgicalToolCreationCallsCorrectRepositoryMethods() throws Exception {
		interventionService.createSurgicalTool(surgicalToolCreationDTO, surgicalToolCreationDTOValidatorMock, surgicalToolAssemblerMock);
		
		verify(surgicalToolRepositoryMock).persist(any(SurgicalTool.class));
		verify(interventionRepositoryMock).getById(anyInt());
		verify(interventionRepositoryMock).update(interventionMock);
	}
	
	@Test
	public void verifySurgicalToolCreationCallsCorrectDomainMethods() throws Exception {
		interventionService.createSurgicalTool(surgicalToolCreationDTO, surgicalToolCreationDTOValidatorMock, surgicalToolAssemblerMock);
		verify(interventionMock).addSurgicalTool(surgicalToolMock);
	}
	
	@Test
	public void verifySurgicalToolReturnsCorrectValue() throws Exception {
		final int SAMPLE_ID = 2;
		when(surgicalToolMock.getId()).thenReturn(SAMPLE_ID);
		
		int interventionId = interventionService.createSurgicalTool(surgicalToolCreationDTO, surgicalToolCreationDTOValidatorMock, surgicalToolAssemblerMock);
		
		assertEquals(SAMPLE_ID, interventionId);
	}

	@Test
	public void verifySurgicalToolCreationBeginsAndCommitsTransaction() throws Exception {
		interventionService.createSurgicalTool(surgicalToolCreationDTO, surgicalToolCreationDTOValidatorMock, surgicalToolAssemblerMock);
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}
	
	@Test
	public void verifySurgicalToolCreationRollsbackOnException() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(true);
		when(interventionRepositoryMock.getById(anyInt())).thenThrow(new InterventionNotFoundException());

		try {
			interventionService.createSurgicalTool(surgicalToolCreationDTO, surgicalToolCreationDTOValidatorMock, surgicalToolAssemblerMock);
		} catch(ServiceRequestException e) {
			verify(entityTransactionMock).rollback();
			return;
		}
	}
	
	@Test
	public void verifySurgicalToolCreationDoesNotRollbackOnSuccessfulCommit() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(false);

		interventionService.createSurgicalTool(surgicalToolCreationDTO, surgicalToolCreationDTOValidatorMock, surgicalToolAssemblerMock);
		
		verify(entityTransactionMock).commit();
		verify(entityTransactionMock, never()).rollback();
	}
	
	@Test
	public void verifySurgicalToolModificationCallsCorrectRepositoryMethods() throws Exception {
		interventionService.modifySurgicalTool(surgicalToolModificationDTO, surgicalToolModificationDTOValidatorMock);
		
		verify(surgicalToolRepositoryMock).getBySerialNumberOrId(anyString());
		verify(interventionRepositoryMock).getById(anyInt());
		verify(surgicalToolRepositoryMock).update(surgicalToolMock);
	}
	
	@Test
	public void verifySurgicalToolModificationCallsCorrectDomainMethods() throws Exception {
		final String SAMPLE_SERIAL_NUMBER_PARAMETER = "2985023D";
		surgicalToolModificationDTO.newSerialNumber = SAMPLE_SERIAL_NUMBER_PARAMETER;
		
		interventionService.modifySurgicalTool(surgicalToolModificationDTO, surgicalToolModificationDTOValidatorMock);
		
		verify(surgicalToolMock).setStatus(any(SurgicalToolStatus.class));
		verify(interventionMock).changeSurgicalToolSerialNumber(surgicalToolMock, SAMPLE_SERIAL_NUMBER_PARAMETER);
	}

	@Test
	public void verifySurgicalToolModificationBeginsAndCommitsTransaction() throws Exception {
		interventionService.modifySurgicalTool(surgicalToolModificationDTO, surgicalToolModificationDTOValidatorMock);
		InOrder inOrder = inOrder(entityTransactionMock);

		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}
	
	@Test
	public void verifySurgicalToolModificationRollsbackOnException() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(true);
		when(patientRepositoryMock.getById(anyInt())).thenThrow(new SurgicalToolNotFoundException());

		try {
			interventionService.modifySurgicalTool(surgicalToolModificationDTO, surgicalToolModificationDTOValidatorMock);
		} catch(ServiceRequestException e) {
			verify(entityTransactionMock).rollback();
			return;
		}
	}
	
	@Test
	public void verifySurgicalToolModificationDoesNotRollbackOnSuccessfulCommit() throws Exception {
		when(entityTransactionMock.isActive()).thenReturn(false);

		interventionService.modifySurgicalTool(surgicalToolModificationDTO, surgicalToolModificationDTOValidatorMock);
		
		verify(entityTransactionMock).commit();
		verify(entityTransactionMock, never()).rollback();
	}
	
}
