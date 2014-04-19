package ca.ulaval.glo4002.domain.intervention;

import java.text.ParseException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import static org.unitils.reflectionassert.ReflectionAssert.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.services.dto.InterventionCreationDTO;

public class InterventionFactoryTest {
	private static final String SAMPLE_DESCRIPTION_PARAMETER = "description";
	private static final Surgeon SAMPLE_SURGEON_PARAMETER = new Surgeon("1");
	private static final Date SAMPLE_DATE_PARAMETER = new Date(3);
	private static final String SAMPLE_ROOM_PARAMETER = "room";
	private static final InterventionType SAMPLE_TYPE_PARAMETER = InterventionType.HEART;
	private static final InterventionStatus SAMPLE_STATUS_PARAMETER = InterventionStatus.PLANNED;
	private static final Patient SAMPLE_PATIENT = new Patient(3);
	
	InterventionFactory interventionFactory = new InterventionFactory();
	InterventionCreationDTO interventionCreationDTO = new InterventionCreationDTO();
	
	@Before
	public void init() {
		interventionCreationDTO.description = SAMPLE_DESCRIPTION_PARAMETER;
		interventionCreationDTO.surgeon = SAMPLE_SURGEON_PARAMETER;
		interventionCreationDTO.date = SAMPLE_DATE_PARAMETER;
		interventionCreationDTO.room = SAMPLE_ROOM_PARAMETER;
		interventionCreationDTO.type = SAMPLE_TYPE_PARAMETER;
		interventionCreationDTO.status = SAMPLE_STATUS_PARAMETER;
	}

	@Test
	public void createsInterventionCorrectly() throws ParseException {
		Intervention createdIntervention = interventionFactory.createFromDTO(interventionCreationDTO, SAMPLE_PATIENT);
		Intervention expectedIntervention = new Intervention(SAMPLE_DESCRIPTION_PARAMETER,
				SAMPLE_SURGEON_PARAMETER, SAMPLE_DATE_PARAMETER,
				SAMPLE_ROOM_PARAMETER, SAMPLE_TYPE_PARAMETER,
				SAMPLE_STATUS_PARAMETER, SAMPLE_PATIENT);
		
		assertReflectionEquals(expectedIntervention, createdIntervention);
	}
}
