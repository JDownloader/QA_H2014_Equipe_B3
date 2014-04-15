package ca.ulaval.glo4002.services.assemblers;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.intervention.Intervention;
import ca.ulaval.glo4002.domain.intervention.InterventionFactory;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientDoesNotExist;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

@RunWith(MockitoJUnitRunner.class)
public class InterventionAssemblerTest {
	private int SAMPLE_ID = 56;
	private EntityNotFoundException entityNotFound;
	
	@Mock
	private Intervention intervention;
	@Mock
	private InterventionFactory interventionFactory;
	@Mock
	private Patient patient;
	@Mock
	private InterventionCreationDTO dto;
	@Mock
	private PatientRepository patientRepository;
	@InjectMocks
	private InterventionAssembler assembler;
	
	@Before
	public void init() {
		entityNotFound = new EntityNotFoundException();
	}
	
	@Test (expected=PatientDoesNotExist.class)
	public void doNotAssembleWhenNoPatientFound() {
		Mockito.when(dto.getPatientNumber()).thenThrow(entityNotFound);
		
		assembler.assembleInterventionFromDTO(dto, patientRepository);
	}
	
	@Test
	public void assembleWhenPatientFound() {
		Mockito.when(dto.getPatientNumber()).thenReturn(SAMPLE_ID);
		Mockito.when(patientRepository.getById(SAMPLE_ID)).thenReturn(patient);
		Mockito.when(interventionFactory.createIntervention(dto, patient)).thenReturn(intervention);
		
		assembler.assembleInterventionFromDTO(dto, patientRepository);
		
		Mockito.verify(patientRepository).getById(SAMPLE_ID);
	}
}
