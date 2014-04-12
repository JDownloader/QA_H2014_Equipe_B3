package ca.ulaval.glo4002.services;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.intervention.Intervention;
import ca.ulaval.glo4002.persistence.intervention.HibernateInterventionRepository;
import ca.ulaval.glo4002.persistence.patient.HibernatePatientRepository;
import ca.ulaval.glo4002.persistence.surgicaltool.HibernateSurgicalToolRepository;
import ca.ulaval.glo4002.services.assemblers.InterventionAssembler;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

@RunWith(MockitoJUnitRunner.class)
public class InterventionServiceTest {

	private int SAMPLE_ID = 1;
	private Exception SAMPLE_EXCEPTION = new RuntimeException();
	
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
	//Do not use @InjectMocks here, it won't mock the EntityManager properly
	private InterventionService interventionService;
	
	@Before
	public void init() {
		Mockito.when(entityManager.getTransaction()).thenReturn(entityTransaction);
		interventionService = new InterventionService(interventionRepository, patientRepository, surgicalToolRepository, interventionAssembler, entityManager);
	}

	@Test
	public void returnIdOfInterventionWhenCreationIsASuccess() {
		Mockito.when(interventionAssembler.assembleInterventionFromDTO(interventionCreationDTO, patientRepository)).thenReturn(intervention);
		Mockito.when(intervention.getId()).thenReturn(SAMPLE_ID);
		
		int resultingId = interventionService.createIntervention(interventionCreationDTO);
		
		assertEquals(SAMPLE_ID, resultingId);
	}
	
	@Test
	public void commitTransactionWhenCreationOfIntervention() {
		Mockito.when(interventionAssembler.assembleInterventionFromDTO(interventionCreationDTO, patientRepository)).thenReturn(intervention);
		Mockito.when(intervention.getId()).thenReturn(SAMPLE_ID);
		
		interventionService.createIntervention(interventionCreationDTO);
		
		Mockito.verify(entityTransaction).commit();
	}
	
	@Test (expected=RuntimeException.class)
	public void doNotCommitTransactionWhenCreationOfInterventionFails() {
		Mockito.when(interventionAssembler.assembleInterventionFromDTO(interventionCreationDTO, patientRepository))
		.thenThrow(SAMPLE_EXCEPTION);
		
		interventionService.createIntervention(interventionCreationDTO);
		
		Mockito.verify(entityTransaction, Mockito.never()).commit();
	}
	
	@Test (expected=RuntimeException.class)
	public void makeRollbackWhenCreationOfInterventionFails() {
		Mockito.when(interventionAssembler.assembleInterventionFromDTO(interventionCreationDTO, patientRepository))
		.thenThrow(SAMPLE_EXCEPTION);
		Mockito.when(entityTransaction.isActive()).thenReturn(true);
		
		interventionService.createIntervention(interventionCreationDTO);
		
		Mockito.verify(entityTransaction).rollback();
	}
	
	@Test (expected=RuntimeException.class)
	public void throwExceptionWhenAssemblingOfInterventionFails() {
		Mockito.when(interventionAssembler.assembleInterventionFromDTO(interventionCreationDTO, patientRepository)).thenThrow(SAMPLE_EXCEPTION);
		
		interventionService.createIntervention(interventionCreationDTO);
	}
}
