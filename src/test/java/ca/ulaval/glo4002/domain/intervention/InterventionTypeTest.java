package ca.ulaval.glo4002.domain.intervention;

import static org.junit.Assert.*;

import org.junit.Test;

public class InterventionTypeTest {
	private static final String INVALID_TYPE = "COWUR";
	private static final String SAMPLE_TYPE = "COEUR";
	
	@Test(expected = IllegalArgumentException.class)
	public void throwsIllegalArgumentExceptionOnInvalidStatusString() {
		InterventionType.fromString(INVALID_TYPE);
	}
	
	@Test
	public void returnsCorrectSurgicalToolFromValidStatus() {
		InterventionType type = InterventionType.fromString(SAMPLE_TYPE);
		assertEquals(InterventionType.HEART, type);
	}
}