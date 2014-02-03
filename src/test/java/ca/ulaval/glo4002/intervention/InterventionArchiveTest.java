package ca.ulaval.glo4002.intervention;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import ca.ulaval.glo4002.exceptions.InterventionNotFoundException;

public class InterventionArchiveTest {
	
	private static final int UNEXISTING_INTERVENTION_ID = -1;
	
	InterventionArchive interventionArchive;
	Intervention interventionMock;

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init() {
		interventionArchive = new InterventionArchive();
		interventionMock = Mockito.mock(Intervention.class);
	}
	
	@Test
	public void newInterventionArchiveIsBeingCreated() {
		assertNotNull(interventionArchive);
	}
	
	@Test
	public void newInterventionIsBeingAddedWithoutThrowingAnException() {
		interventionArchive.addIntervention(interventionMock);
	}
	
	@Test
	public void interventionCanBeRetrivedCorrectlyOnceAdded() throws InterventionNotFoundException {
		interventionArchive.addIntervention(interventionMock);
		int interventionId = interventionMock.getId();
		Mockito.when(interventionMock.getId()).thenReturn(interventionId);
		
		Intervention intervention = interventionArchive.getIntervention(interventionId);
		
		assertEquals(interventionId, intervention.getId());
	}
	
	@Test(expected = InterventionNotFoundException.class)
	public void throwsAnExceptionWhenRetrievingAnUnexistingInterventionId() throws InterventionNotFoundException {
		interventionArchive.getIntervention(UNEXISTING_INTERVENTION_ID);
	}
}
