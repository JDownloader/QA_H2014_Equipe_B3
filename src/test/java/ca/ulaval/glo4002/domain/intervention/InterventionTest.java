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
	private static final InterventionStatus SAMPLE_STATUS = InterventionStatus.IN_PROGRESS;
	private static final InterventionType SAMPLE_TYPE = InterventionType.MARROW;
	private static final Surgeon SAMPLE_SURGEON = new Surgeon("3");
	private static final String SAMPLE_SERIAL_NUMBER = "serialnumber";
	private static final Patient SAMPLE_PATIENT = new Patient(3);

	private Intervention intervention;
	SurgicalTool surgicalToolMock;

	@Before
	public void init() {
		surgicalToolMock = mock(SurgicalTool.class);
		intervention = new Intervention(SAMPLE_DESCRIPTION, SAMPLE_SURGEON, SAMPLE_DATE, SAMPLE_ROOM, SAMPLE_TYPE, SAMPLE_STATUS, SAMPLE_PATIENT);
	}

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
