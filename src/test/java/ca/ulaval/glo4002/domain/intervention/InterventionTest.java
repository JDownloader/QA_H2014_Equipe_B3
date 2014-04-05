package ca.ulaval.glo4002.domain.intervention;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.utils.DateParser;

@RunWith(MockitoJUnitRunner.class)
public class InterventionTest {
	private String SAMPLE_DESCRIPTION = "This is a sample description";
	private String SAMPLE_ROOM = "This is a room";
	private String SAMPLE_TYPE = (InterventionType.EYE.getValue());
	private String SAMPLE_STATUS = (InterventionStatus.CANCELLED.getValue());
	private String EMPTY_STATUS = "";
	private String EMPTY_DESCRIPTION = "";
	private String EMPTY_ROOM = "";
	private String SAMPLE_DATE = "2001-07-04T12:08:56";
	private String INVALID_DATE = "";
	private Date VALID_DATE;
	
	@Mock
	private Patient patient;
	@Mock
	private Surgeon surgeon;
	
	private Intervention intervention;
	
	@Before
	public void init() throws ParseException {
		VALID_DATE = DateParser.parseDate(SAMPLE_DATE);
	}
	
	@Test (expected=RuntimeException.class)
	public void doNotBuildInterventionWithInvalidDate() {
		intervention = new Intervention(SAMPLE_DESCRIPTION, surgeon, INVALID_DATE, SAMPLE_ROOM, SAMPLE_TYPE, SAMPLE_STATUS, patient);
		assertEquals(InterventionStatus.PLANNED, intervention.getStatus());
	}
	
	@Test
	public void buildInterventionWithValidDate() {
		intervention = new Intervention(SAMPLE_DESCRIPTION, surgeon, SAMPLE_DATE, SAMPLE_ROOM, SAMPLE_TYPE, SAMPLE_STATUS, patient);
		assertEquals(VALID_DATE, intervention.getDate());
	}
	
	@Test (expected=RuntimeException.class)
	public void doNotBuildInterventionWhenEmptyDescription() {
		intervention = new Intervention(EMPTY_DESCRIPTION, surgeon, SAMPLE_DATE, SAMPLE_ROOM, SAMPLE_TYPE, SAMPLE_STATUS, patient);
	}
	
	@Test (expected=RuntimeException.class)
	public void doNotBuildInterventionWhenNoRoom() {
		intervention = new Intervention(SAMPLE_DESCRIPTION, surgeon, SAMPLE_DATE, EMPTY_ROOM, SAMPLE_TYPE, SAMPLE_STATUS, patient);
	}
	
	@Test
	public void buildInterventionWithPlannedStatusWhenNoStatusGiven() {
		intervention = new Intervention(SAMPLE_DESCRIPTION, surgeon, SAMPLE_DATE, SAMPLE_ROOM, SAMPLE_TYPE, EMPTY_STATUS, patient);
		assertEquals(InterventionStatus.PLANNED, intervention.getStatus());
	}

}