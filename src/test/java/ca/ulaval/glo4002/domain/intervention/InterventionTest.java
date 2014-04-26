package ca.ulaval.glo4002.domain.intervention;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.domain.patient.Patient;
import ca.ulaval.glo4002.domain.staff.Surgeon;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolNotFoundException;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolRequiresSerialNumberException;

public class InterventionTest {

	private static final Date SAMPLE_DATE = new Date();
	private static final String SAMPLE_DESCRIPTION = "description";
	private static final String SAMPLE_ROOM = "room";
	private static final InterventionStatus SAMPLE_STATUS = InterventionStatus.IN_PROGRESS;
	private static final InterventionType SAMPLE_TYPE = InterventionType.MARROW;
	private static final Surgeon SAMPLE_SURGEON = new Surgeon("3");
	private static final Patient SAMPLE_PATIENT = new Patient(3);
	private static final String SAMPLE_SERIAL_NUMBER = "235423423";
	private static final String SAMPLE_ID = "2";
	
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
		assertTrue(intervention.containsSurgicalTool(surgicalToolMock));
	}
	
	@Test
	public void addsSurgicalToolObserverCorrectly() {
		intervention.addSurgicalTool(surgicalToolMock);
		verify(surgicalToolMock, times(1)).addObserver(any(Intervention.SurgicalToolObserver.class));
	}
	
	@Test(expected = SurgicalToolRequiresSerialNumberException.class)
	public void disallowsAddingAnonymousSurgicalToolToAForbiddenInterventionType() {
		when(surgicalToolMock.isAnonymous()).thenReturn(true);
		intervention.addSurgicalTool(surgicalToolMock);
	}
	
	@Test
	public void linksObserversCorrectly() {
		intervention.addSurgicalTool(surgicalToolMock);
		intervention.addSurgicalTool(surgicalToolMock);
		
		intervention.linkObservers();
		
		verify(surgicalToolMock, times(2)).deleteObservers();
		verify(surgicalToolMock, times(4)).addObserver(any(Intervention.SurgicalToolObserver.class));
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
	public void returnsCorrectSurgicalToolWhenSearchBySerialNumber() {
		when(surgicalToolMock.compareToSerialNumber(SAMPLE_SERIAL_NUMBER)).thenReturn(true);
		intervention.addSurgicalTool(surgicalToolMock);
		
		SurgicalTool actualSurgicalTool = intervention.getSurgicalToolBySerialNumberOrId(SAMPLE_SERIAL_NUMBER);
		
		assertSame(surgicalToolMock, actualSurgicalTool);
	}
}
