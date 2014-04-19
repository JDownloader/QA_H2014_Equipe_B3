package ca.ulaval.glo4002.services.assemblers;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.domain.intervention.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.patient.PatientRepository;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;
import ca.ulaval.glo4002.utils.DateParser;

@RunWith(MockitoJUnitRunner.class)
public class InterventionAssemblerTest {
	private static final String SAMPLE_DESCRIPTION_PARAMETER = "description";
	private static final Surgeon SAMPLE_SURGEON_PARAMETER = new Surgeon("1");
	private static final String SAMPLE_DATE_PARAMETER = "2001-07-04T12:08:56";
	private static final String SAMPLE_ROOM_PARAMETER = "room";
	private static final InterventionType SAMPLE_TYPE_PARAMETER = InterventionType.HEART;
	private static final InterventionStatus SAMPLE_STATUS_PARAMETER = InterventionStatus.PLANNED;
	
	InterventionAssembler interventionAssembler = new InterventionAssembler();
	InterventionCreationDTO interventionCreationDTO = new InterventionCreationDTO();
	PatientRepository patientRepositoryMock;
	Patient patientMock;
	
	@Before
	public void init() throws ParseException {
		setupDTO();
		patientRepositoryMock = mock(PatientRepository.class);
		patientMock = mock(Patient.class);
		when(patientRepositoryMock.getById(anyInt())).thenReturn(patientMock);
	}
	
	private void setupDTO() throws ParseException {
		interventionCreationDTO.description = SAMPLE_DESCRIPTION_PARAMETER;
		interventionCreationDTO.surgeon = SAMPLE_SURGEON_PARAMETER;
		interventionCreationDTO.date = DateParser.parseDate(SAMPLE_DATE_PARAMETER);
		interventionCreationDTO.room = SAMPLE_ROOM_PARAMETER;
		interventionCreationDTO.type = SAMPLE_TYPE_PARAMETER;
		interventionCreationDTO.status = SAMPLE_STATUS_PARAMETER;
	}

	@Test
	public void createsInterventionCorrectly() throws ParseException {
		Intervention createdIntervention = interventionAssembler.assembleFromDTO(interventionCreationDTO, patientRepositoryMock);
		Intervention expectedIntervention = new Intervention(SAMPLE_DESCRIPTION_PARAMETER,
				SAMPLE_SURGEON_PARAMETER, DateParser.parseDate(SAMPLE_DATE_PARAMETER),
				SAMPLE_ROOM_PARAMETER, SAMPLE_TYPE_PARAMETER,
				SAMPLE_STATUS_PARAMETER, patientMock);
		
		assertReflectionEquals(expectedIntervention, createdIntervention);
	}
}
