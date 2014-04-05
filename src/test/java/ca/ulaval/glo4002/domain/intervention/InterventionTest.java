package ca.ulaval.glo4002.domain.intervention;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;

@RunWith(MockitoJUnitRunner.class)
public class InterventionTest {
	private String SAMPLE_DESCRIPTION = "This is a sample description";
	private String SAMPLE_ROOM = "This is a room";
	private String SAMPLE_TYPE = (InterventionType.EYE.getValue());
	private String SAMPLE_STATUS = (InterventionStatus.CANCELLED.getValue());
	private String EMPTY_STATUS = "";
	private String EMPTY_DESCRIPTION = "";
	private String EMPTY_ROOM = "";
	
	@Mock
	private Patient patient;
	@Mock
	private Surgeon surgeon;
	@Mock
	private Date date;
	
	private Intervention intervention;
	
	@Test (expected=RuntimeException.class)
	public void doNotBuildInterventionWhenEmptyDescription() {
		intervention = new Intervention(EMPTY_DESCRIPTION, surgeon, date, SAMPLE_ROOM, SAMPLE_TYPE, SAMPLE_STATUS, patient);
	}
	
	@Test (expected=RuntimeException.class)
	public void doNotBuildInterventionWhenNoRoom() {
		intervention = new Intervention(SAMPLE_DESCRIPTION, surgeon, date, EMPTY_ROOM, SAMPLE_TYPE, SAMPLE_STATUS, patient);
	}
	
	@Test
	public void buildInterventionWithPlannedStatusWhenNoStatusGiven() {
		intervention = new Intervention(SAMPLE_DESCRIPTION, surgeon, date, SAMPLE_ROOM, SAMPLE_TYPE, EMPTY_STATUS, patient);
		assertEquals(InterventionStatus.PLANNED, intervention.getStatus());
	}

}