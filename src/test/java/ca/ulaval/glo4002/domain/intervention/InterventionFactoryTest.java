package ca.ulaval.glo4002.domain.intervention;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
 
@RunWith(MockitoJUnitRunner.class)
public class InterventionFactoryTest {
	
	private static final String SAMPLE_DESCRIPTION_PARAMETER = "description";
	private static final Surgeon SAMPLE_SURGEON_PARAMETER = new Surgeon("1");
	private static final Date SAMPLE_DATE_PARAMETER = new Date(3);
	private static final String SAMPLE_ROOM_PARAMETER = "room";
	private static final String SAMPLE_CRITICAL_TYPE_PARAMETER = InterventionType.HEART.getValue();
	private static final String SAMPLE_NONCRITICAL_TYPE_PARAMETER = InterventionType.OTHER.getValue();
	private static final String SAMPLE_STATUS_PARAMETER = InterventionStatus.PLANNED.getValue();
	
	private InterventionFactory interventionFactory;
	private Patient patientMock;
	
	@Before
	public void init() {
		interventionFactory = new InterventionFactory();
		patientMock = mock(Patient.class);
	}
	
	@Test
	public void creatingInterventionUsingNonCriticalTypeReturnsNonCriticalIntervention() {
		Intervention createdIntervention = interventionFactory.createIntervention(SAMPLE_DESCRIPTION_PARAMETER,
				SAMPLE_SURGEON_PARAMETER, SAMPLE_DATE_PARAMETER,
				SAMPLE_ROOM_PARAMETER, InterventionType.fromString(SAMPLE_NONCRITICAL_TYPE_PARAMETER),
				InterventionStatus.fromString(SAMPLE_STATUS_PARAMETER), patientMock);
		
		assertEquals(createdIntervention.getClass(), NonCriticalIntervention.class);
	}
	
	@Test
	public void creatingInterventionUsingCriticalTypeReturnsCriticalIntervention() {
		Intervention createdIntervention = interventionFactory.createIntervention(SAMPLE_DESCRIPTION_PARAMETER,
				SAMPLE_SURGEON_PARAMETER, SAMPLE_DATE_PARAMETER,
				SAMPLE_ROOM_PARAMETER, InterventionType.fromString(SAMPLE_CRITICAL_TYPE_PARAMETER),
				InterventionStatus.fromString(SAMPLE_STATUS_PARAMETER), patientMock);
		
		assertEquals(createdIntervention.getClass(), CriticalIntervention.class);
	}
}
