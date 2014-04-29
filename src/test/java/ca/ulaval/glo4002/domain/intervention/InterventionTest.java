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
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public abstract class InterventionTest {

	protected static final Date SAMPLE_DATE = new Date();
	protected static final String SAMPLE_DESCRIPTION = "description";
	protected static final String SAMPLE_ROOM = "room";
	protected static final InterventionStatus SAMPLE_STATUS = InterventionStatus.IN_PROGRESS;
	protected static final InterventionType SAMPLE_TYPE = InterventionType.MARROW;
	protected static final Surgeon SAMPLE_SURGEON = new Surgeon("3");
	protected static final Patient SAMPLE_PATIENT = new Patient(3);
	protected static final String SAMPLE_SERIAL_NUMBER = "235423423";
	protected static final String SAMPLE_ID = "2";
	
	protected Intervention intervention;
	SurgicalTool surgicalToolMock;

	@Before
	public void init() {
		surgicalToolMock = mock(SurgicalTool.class);
	}

	@Test
	public void addsNonAnonymousSurgicalToolCorrectly() {
		when(surgicalToolMock.isAnonymous()).thenReturn(false);
		intervention.addSurgicalTool(surgicalToolMock);
		assertTrue(intervention.containsSurgicalTool(surgicalToolMock));
	}

	@Test(expected = SurgicalToolNotFoundException.class)
	public void throwsExceptionWhenRetrievingUnexistingSurgicalToolSerialNumber() {
		when(surgicalToolMock.compareToSerialNumber(SAMPLE_SERIAL_NUMBER)).thenReturn(false);
		when(surgicalToolMock.compareToId(SAMPLE_ID)).thenReturn(false);
		intervention.addSurgicalTool(surgicalToolMock);
		
		intervention.getSurgicalToolBySerialNumberOrId(SAMPLE_SERIAL_NUMBER);
	}
	
	@Test
	public void returnsCorrectSurgicalToolWhenSearchedById() {
		when(surgicalToolMock.compareToId(SAMPLE_ID)).thenReturn(true);
		intervention.addSurgicalTool(surgicalToolMock);
		
		SurgicalTool actualSurgicalTool = intervention.getSurgicalToolBySerialNumberOrId(SAMPLE_ID);
		
		assertSame(surgicalToolMock, actualSurgicalTool);
	}
	
	@Test
	public void returnsCorrectSurgicalToolWhenSearchedBySerialNumber() {
		when(surgicalToolMock.compareToSerialNumber(SAMPLE_SERIAL_NUMBER)).thenReturn(true);
		intervention.addSurgicalTool(surgicalToolMock);
		
		SurgicalTool actualSurgicalTool = intervention.getSurgicalToolBySerialNumberOrId(SAMPLE_SERIAL_NUMBER);
		
		assertSame(surgicalToolMock, actualSurgicalTool);
	}
}
