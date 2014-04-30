package ca.ulaval.glo4002.domain.intervention;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NonCriticalInterventionTest extends InterventionTest {

	@Before
	public void init() {
		super.init();
		intervention = new NonCriticalIntervention(SAMPLE_DESCRIPTION, SAMPLE_SURGEON, SAMPLE_DATE, SAMPLE_ROOM, SAMPLE_TYPE, SAMPLE_STATUS, SAMPLE_PATIENT);
	}
	
	@Test
	public void allowsAddingAnonymousSurgicalTool() {
		when(surgicalToolMock.isAnonymous()).thenReturn(true);
		intervention.addSurgicalTool(surgicalToolMock);
		assertTrue(intervention.containsSurgicalTool(surgicalToolMock));
	}
}
