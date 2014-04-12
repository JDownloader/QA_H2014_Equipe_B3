package ca.ulaval.glo4002.domain.intervention;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.services.dto.validators.SurgicalToolCreationException;

public class InterventionTest {

	private static final Date SAMPLE_DATE = new Date();
	private static final String SAMPLE_DESCRIPTION = "description";
	private static final String SAMPLE_ROOM = "room";
	private static final InterventionStatus SAMPLE_STATUS = InterventionStatus.EN_COURS;
	private static final InterventionType SAMPLE_TYPE_FOR_NONANONYMOUS_TOOLS = InterventionType.MOELLE;

	private Patient patientMock;
	private Surgeon surgeonMock;

	private InterventionBuilder interventionBuilder;
	private Intervention intervention;
	SurgicalTool surgicalToolMock;

	@Before
	public void init() {
		patientMock = mock(Patient.class);
		surgeonMock = mock(Surgeon.class);
		surgicalToolMock = mock(SurgicalTool.class);

		buildInterventionMock();
	}

	private void buildInterventionMock() {
		interventionBuilder = new InterventionBuilder()
			.date(SAMPLE_DATE)
			.description(SAMPLE_DESCRIPTION)
			.patient(patientMock)
			.status(SAMPLE_STATUS)
			.room(SAMPLE_ROOM)
			.surgeon(surgeonMock)
			.type(SAMPLE_TYPE_FOR_NONANONYMOUS_TOOLS);
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
		assertEquals(SAMPLE_TYPE_FOR_NONANONYMOUS_TOOLS, intervention.getType());
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
	public void addsNonAnonymousSurgicalToolCorrectly() {
		
		when(surgicalToolMock.isAnonymous()).thenReturn(false);

		intervention.addSurgicalTool(surgicalToolMock);
		assertTrue(intervention.hasSurgicalTool(surgicalToolMock));
	}
	
	@Test(expected = SurgicalToolCreationException.class)
	public void addingAnonymousSurgicalToolToAForbiddenInterventionTypeThrowsException() {
		
		when(surgicalToolMock.isAnonymous()).thenReturn(true);
		
		intervention.addSurgicalTool(surgicalToolMock);
	}
	
	@Test
	public void addingNonAnonymousSurgicalToolToAForbiddenInterventionTypeWorks() {
		
		when(surgicalToolMock.isAnonymous()).thenReturn(false);
		
		intervention.addSurgicalTool(surgicalToolMock);

		assertTrue(intervention.hasSurgicalTool(surgicalToolMock));
	}
	
	
	
}
