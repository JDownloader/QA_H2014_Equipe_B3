package ca.ulaval.glo4002.services.assemblers;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.unitils.reflectionassert.ReflectionAssert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;
 
@RunWith(MockitoJUnitRunner.class)
public class InterventionAssemblerTest {
	private static final String SAMPLE_DESCRIPTION_PARAMETER = "description";
	private static final Surgeon SAMPLE_SURGEON_PARAMETER = new Surgeon("1");
	private static final Date SAMPLE_DATE_PARAMETER = new Date(3);
	private static final String SAMPLE_ROOM_PARAMETER = "room";
	private static final String SAMPLE_TYPE_PARAMETER = InterventionType.HEART.getValue();
	private static final String SAMPLE_STATUS_PARAMETER = InterventionStatus.PLANNED.getValue();
	
	private InterventionAssembler interventionAssembler = new InterventionAssembler();
	private InterventionCreationDTO interventionCreationDTO = new InterventionCreationDTO();
	private InterventionFactory interventionFactoryMock;
	private Intervention interventionMock;
	private PatientRepository patientRepositoryMock;
	private Patient patientMock;
	
	@Before
	public void init() {
		setupDTO();
		createMocks();
		when(patientRepositoryMock.getById(anyInt())).thenReturn(patientMock);
	}

	private void setupDTO() {
		interventionCreationDTO.description = SAMPLE_DESCRIPTION_PARAMETER;
		interventionCreationDTO.surgeon = SAMPLE_SURGEON_PARAMETER;
		interventionCreationDTO.date = SAMPLE_DATE_PARAMETER;
		interventionCreationDTO.room = SAMPLE_ROOM_PARAMETER;
		interventionCreationDTO.type = SAMPLE_TYPE_PARAMETER;
		interventionCreationDTO.status = SAMPLE_STATUS_PARAMETER;
	}

	private void createMocks() {
		interventionFactoryMock = mock(InterventionFactory.class);
		interventionMock = mock(Intervention.class);
		patientRepositoryMock = mock(PatientRepository.class);
		patientMock = mock(Patient.class);
	}

	@Test
	public void createsInterventionCorrectly() throws ParseException {
		when(interventionFactoryMock.createIntervention(SAMPLE_DESCRIPTION_PARAMETER,
				SAMPLE_SURGEON_PARAMETER, SAMPLE_DATE_PARAMETER,
				SAMPLE_ROOM_PARAMETER, InterventionType.fromString(SAMPLE_TYPE_PARAMETER),
				InterventionStatus.fromString(SAMPLE_STATUS_PARAMETER), patientMock)).thenReturn(interventionMock);
		
		Intervention assembledIntervention = interventionAssembler.assembleFromDTO(interventionCreationDTO, interventionFactoryMock, patientRepositoryMock);
		
		assertReflectionEquals(interventionMock, assembledIntervention);
	}
}
