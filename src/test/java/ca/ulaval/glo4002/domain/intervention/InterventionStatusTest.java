package ca.ulaval.glo4002.domain.intervention;

import static org.junit.Assert.*;

import org.junit.Test;

public class InterventionStatusTest {
	private static final String INVALID_STATUS = "EN_CWURS";
	private static final String SAMPLE_STATUS = "EN_COURS";

	@Test(expected = InterventionStatusParseException.class)
	public void throwsIllegalArgumentExceptionOnInvalidStatusString() {
		InterventionStatus.fromString(INVALID_STATUS);
	}

	@Test
	public void returnsCorrectInterventionStatusFromValidStatusString() {
		InterventionStatus status = InterventionStatus.fromString(SAMPLE_STATUS);
		assertEquals(InterventionStatus.IN_PROGRESS, status);
	}
}
