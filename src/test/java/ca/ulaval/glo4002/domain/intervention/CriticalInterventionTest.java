package ca.ulaval.glo4002.domain.intervention;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolRequiresSerialNumberException;

@RunWith(MockitoJUnitRunner.class)
public class CriticalInterventionTest extends InterventionTest {

	@Before
	public void init() {
		super.init();
		intervention = new CriticalIntervention(SAMPLE_DESCRIPTION, SAMPLE_SURGEON, SAMPLE_DATE, SAMPLE_ROOM, SAMPLE_TYPE, SAMPLE_STATUS, SAMPLE_PATIENT);
	}
	
	@Test
	public void addsSurgicalToolObserverCorrectly() {
		intervention.addSurgicalTool(surgicalToolMock);
		verify(surgicalToolMock, times(1)).addObserver(any(CriticalIntervention.SurgicalToolObserver.class));
	}
	
	@Test(expected = SurgicalToolRequiresSerialNumberException.class)
	public void disallowsAddingAnonymousSurgicalTool() {
		when(surgicalToolMock.isAnonymous()).thenReturn(true);
		intervention.addSurgicalTool(surgicalToolMock);
	}
	
	@Test
	public void linksObserversCorrectly() {
		intervention.addSurgicalTool(surgicalToolMock);
		intervention.addSurgicalTool(surgicalToolMock);
		
		((CriticalIntervention) intervention).linkObservers();
		
		verify(surgicalToolMock, times(2)).deleteObservers();
		verify(surgicalToolMock, times(4)).addObserver(any(CriticalIntervention.SurgicalToolObserver.class));
	}
}
