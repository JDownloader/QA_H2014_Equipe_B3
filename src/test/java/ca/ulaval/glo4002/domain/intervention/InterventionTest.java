package ca.ulaval.glo4002.domain.intervention;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolRequiresSerialNumberException;

public class InterventionTest {

	private static final Date SAMPLE_DATE = new Date();
	private static final String SAMPLE_DESCRIPTION = "description";
	private static final String SAMPLE_ROOM = "room";
	private static final InterventionStatus SAMPLE_STATUS = InterventionStatus.EN_COURS;
	private static final InterventionType SAMPLE_TYPE = InterventionType.MOELLE;
	private static final String SAMPLE_SERIAL_NUMBER = "serialnumber";

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

	//Refactor NewMarie BEGIN
	private void buildInterventionMock() {
		interventionBuilder = new InterventionBuilder()
			.date(SAMPLE_DATE)
			.description(SAMPLE_DESCRIPTION)
			.patient(patientMock)
			.status(SAMPLE_STATUS)
			.room(SAMPLE_ROOM)
			.surgeon(surgeonMock)
			.type(SAMPLE_TYPE);
		intervention = interventionBuilder.build();
	}
	//Refactor NewMarie END

	@Test
	public void addsNonAnonymousSurgicalToolCorrectly() {
		when(surgicalToolMock.isAnonymous()).thenReturn(false);
		intervention.addSurgicalTool(surgicalToolMock);
		assertTrue(intervention.hasSurgicalTool(surgicalToolMock));
	}
	
	@Test(expected = SurgicalToolRequiresSerialNumberException.class)
	public void disallowsAddingAnonymousSurgicalToolToAForbiddenInterventionType() {
		when(surgicalToolMock.isAnonymous()).thenReturn(true);
		intervention.addSurgicalTool(surgicalToolMock);
	}

	@Test
	public void changesSerialNumberOfSurgicalToolCorrectly() {
		when(surgicalToolMock.isAnonymous()).thenReturn(false);
		intervention.changeSurgicalToolSerialNumber(surgicalToolMock, SAMPLE_SERIAL_NUMBER);
		verify(surgicalToolMock).setSerialNumber(SAMPLE_SERIAL_NUMBER);
	}
	
	@Test(expected = SurgicalToolRequiresSerialNumberException.class)
	public void disallowsRemovingSerialNumberOfSurgicalToolWhenAssociatedWithForbiddenInterventionType() {
		when(surgicalToolMock.isAnonymous()).thenReturn(true);
		intervention.changeSurgicalToolSerialNumber(surgicalToolMock, "");
	}
	
}
