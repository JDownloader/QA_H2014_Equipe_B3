package ca.ulaval.glo4002.domain.intervention;

import java.util.Date;

import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;

public class InterventionTest {

	private static final Date SAMPLE_DATE = new Date();
	private static final String SAMPLE_DESCRIPTION = "description";
	private static final String SAMPLE_ROOM = "room";
	private static final InterventionStatus SAMPLE_STATUS = InterventionStatus.EN_COURS;
	private static final InterventionType SAMPLE_TYPE = InterventionType.MOELLE;

	private Patient patientMock;
	private Surgeon surgeonMock;

	private InterventionBuilder interventionBuilder;
	private Intervention intervention;

	@Before
	public void setup() {
		patientMock = mock(Patient.class);
		surgeonMock = mock(Surgeon.class);

		buildInterventionMock();
	}

	private void buildInterventionMock() {
		interventionBuilder = new InterventionBuilder();
		interventionBuilder.date(SAMPLE_DATE);
		interventionBuilder.description(SAMPLE_DESCRIPTION);
		interventionBuilder.patient(patientMock);
		interventionBuilder.status(SAMPLE_STATUS);
		interventionBuilder.room(SAMPLE_ROOM);
		interventionBuilder.surgeon(surgeonMock);
		interventionBuilder.type(SAMPLE_TYPE);
		intervention = interventionBuilder.build();
	}

	@Test
	public void returnsDescriptionCorrectly() {
		assertEquals(SAMPLE_DESCRIPTION, intervention.getDescription());
	}

	@Test
	public void returnsSurgeonCorrectly() {
		assertSame(surgeonMock, intervention.getSurgeon());
	}

	@Test
	public void returnsDateCorrectly() {
		assertEquals(SAMPLE_DATE, intervention.getDate());
	}

	@Test
	public void returnsRoomCorrectly() {
		assertEquals(SAMPLE_ROOM, intervention.getRoom());
	}

	@Test
	public void returnsTypeCorrectly() {
		assertEquals(SAMPLE_TYPE, intervention.getType());
	}

	@Test
	public void returnsSatusCorrectly() {
		assertSame(SAMPLE_STATUS, intervention.getStatus());
	}

	@Test
	public void returnsPatientCorrectly() {
		assertSame(patientMock, intervention.getPatient());
	}
	
	@Test
	public void addsSurgicalToolCorrectly() {
		SurgicalTool surgicalToolMock = mock(SurgicalTool.class);
		intervention.addSurgicalTool(surgicalToolMock);
		assertTrue(intervention.hasSurgicalTool(surgicalToolMock));
	}
}
