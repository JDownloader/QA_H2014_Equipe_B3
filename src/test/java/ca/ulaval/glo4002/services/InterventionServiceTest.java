package ca.ulaval.glo4002.services;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.intervention.Intervention;
import ca.ulaval.glo4002.domain.patient.PatientDoesNotExist;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.persistence.intervention.HibernateInterventionRepository;
import ca.ulaval.glo4002.persistence.patient.HibernatePatientRepository;
import ca.ulaval.glo4002.persistence.surgicaltool.HibernateSurgicalToolRepository;
import ca.ulaval.glo4002.services.assemblers.InterventionAssembler;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;
import ca.ulaval.glo4002.services.dto.validators.InterventionCreationDTOValidator;

@RunWith(MockitoJUnitRunner.class)
public class InterventionServiceTest {
	
	@Mock
	private ServiceRequestException serviceException;
	@Mock
	private Intervention intervention;
	@Mock
	private InterventionCreationDTO interventionCreationDTO;
	@Mock
	private EntityManager entityManager;
	@Mock
	private EntityTransaction entityTransaction;
	@Mock
	private HibernateInterventionRepository interventionRepository;
	@Mock
	private HibernatePatientRepository patientRepository;
	@Mock
	private HibernateSurgicalToolRepository surgicalToolRepository;
	@Mock
	private InterventionAssembler interventionAssembler;
	@Mock
	private InterventionCreationDTOValidator interventionCreationValidator;
	//Do not use @InjectMocks here, it won't mock the EntityManager/EntityTransaction properly
	private InterventionService interventionService;
	
	private int SAMPLE_ID = 1;
	
	@Before
	public void init() {
		Mockito.when(entityManager.getTransaction()).thenReturn(entityTransaction);
		interventionService = new InterventionService(interventionRepository, patientRepository, surgicalToolRepository, interventionAssembler, entityManager, interventionCreationValidator);
	}

	private void mockSucessfulInterventionCreation() {
		Mockito.doNothing().when(interventionCreationValidator).validate(interventionCreationDTO);
		Mockito.when(interventionAssembler.assembleInterventionFromDTO(interventionCreationDTO, patientRepository)).thenReturn(intervention);
		Mockito.when(intervention.getId()).thenReturn(SAMPLE_ID);
	}
	
	@Test
	public void returnInterventionIdWhenCreated() {
		Mockito.when(interventionAssembler.assembleInterventionFromDTO(interventionCreationDTO, patientRepository)).thenReturn(intervention);
		Mockito.when(intervention.getId()).thenReturn(SAMPLE_ID);
		
		int resultingId = interventionService.createIntervention(interventionCreationDTO);
		
		assertEquals(SAMPLE_ID, resultingId);
	}
	
	@Test (expected=ServiceRequestException.class)
	public void doNotCreateInterventionWhenServiceException() {
		Mockito.doThrow(serviceException).when(interventionCreationValidator).validate(interventionCreationDTO);
		
		interventionService.createIntervention(interventionCreationDTO);
		
		Mockito.verify(intervention, Mockito.never()).getId();
	}
	
	@Test
	public void commitTransactionWhenInterventionIsCreated() {
		mockSucessfulInterventionCreation();
		
		interventionService.createIntervention(interventionCreationDTO);
		
		Mockito.verify(entityTransaction).commit();
	}
	
	@Test (expected=ServiceRequestException.class)
	public void rollbackTransactionNoPatientFoundInInterventionCreation() {
		mockSucessfulInterventionCreation();
		Mockito.doThrow(new PatientDoesNotExist()).when(interventionAssembler).assembleInterventionFromDTO(interventionCreationDTO, patientRepository);
		
		interventionService.createIntervention(interventionCreationDTO);
		
		Mockito.verify(entityTransaction).rollback();
	}
	
	@Test
	public void callInterventionAssemblerWhenValidInterventionCreation() {
		mockSucessfulInterventionCreation();
		
		interventionService.createIntervention(interventionCreationDTO);
		
		Mockito.verify(interventionAssembler).assembleInterventionFromDTO(interventionCreationDTO, patientRepository);
	}
	
	@Test
	public void createInRepositoryWhenValidInterventionCreation() {
		mockSucessfulInterventionCreation();
		
		interventionService.createIntervention(interventionCreationDTO);
		
		Mockito.verify(interventionRepository).create(intervention);
	}
	
	@Test (expected=ServiceRequestException.class)
	public void expectServiceExceptionWhenInvalidInterventionCreationDTO() {
		Mockito.doThrow(serviceException).when(interventionCreationValidator).validate(interventionCreationDTO);
		
		interventionService.createIntervention(interventionCreationDTO);
	}
	
	@Test (expected=ServiceRequestException.class)
	public void expectServiceExceptionWhenPatientNotFoundInRepository() {
		Mockito.doNothing().when(interventionCreationValidator).validate(interventionCreationDTO);
		Mockito.doThrow(new PatientDoesNotExist()).when(interventionAssembler).assembleInterventionFromDTO(interventionCreationDTO, patientRepository);
		
		interventionService.createIntervention(interventionCreationDTO);
	}
}
