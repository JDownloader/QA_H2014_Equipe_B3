package ca.ulaval.glo4002.domain.surgicaltool;

import static org.junit.Assert.*;

import org.junit.Test;

public class SurgicalToolStatusTest {
	private static final String INVALID_STATUS = "INUTILESE";
	private static final String SAMPLE_STATUS = "INUTILISE";
	
	@Test(expected = SurgicalToolStatusParseException.class)
	public void throwsIllegalArgumentExceptionOnInvalidStatusString() {
		SurgicalToolStatus.fromString(INVALID_STATUS);
	}
	
	@Test
	public void returnsCorrectSurgicalToolStatusFromValidStatusString() {
		SurgicalToolStatus status = SurgicalToolStatus.fromString(SAMPLE_STATUS);
		assertEquals(SurgicalToolStatus.UNUSED, status);
	}
}
