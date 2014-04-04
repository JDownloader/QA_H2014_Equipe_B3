package ca.ulaval.glo4002.domain.intervention;

import static org.junit.Assert.*;

import org.junit.Test;


public class InterventionStatusTest {
	private InterventionStatus status;
	
	@Test (expected=IllegalArgumentException.class)
	public void doNotBuildInvalidStatus() {
		status = InterventionStatus.fromString("");
	}
	
	@Test
	public void buildPlannedStatus() {
		status = InterventionStatus.fromString( InterventionStatus.PLANNED.getValue() );
		assertEquals(status, InterventionStatus.PLANNED);
	}
	
	@Test
	public void buildInProgressStatus() {
		status = InterventionStatus.fromString( InterventionStatus.IN_PROGRESS.getValue() );
		assertEquals(status, InterventionStatus.IN_PROGRESS);
	}
	
	@Test
	public void buildDoneStatus() {
		status = InterventionStatus.fromString( InterventionStatus.DONE.getValue() );
		assertEquals(status, InterventionStatus.DONE);
	}
	
	@Test
	public void buildCancelledStatus() {
		status = InterventionStatus.fromString( InterventionStatus.CANCELLED.getValue() );
		assertEquals(status, InterventionStatus.CANCELLED);
	}
	
	@Test
	public void buildPostponedStatus() {
		status = InterventionStatus.fromString( InterventionStatus.POSTPONED.getValue() );
		assertEquals(status, InterventionStatus.POSTPONED);
	}
}