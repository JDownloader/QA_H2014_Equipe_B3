package ca.ulaval.glo4002.domain.surgicaltool;

import static org.junit.Assert.*;

import org.junit.Test;

public class SurgicalToolStatusTest {
	private static final String INVALID_STATUS = "Inutilwse";
	private static final String SAMPLE_STATUS = "Inutilise";
	
	@Test(expected = IllegalArgumentException.class)
	public void throwsIllegalArgumentExceptionOnInvalidStatus() {
		SurgicalToolStatus.fromString(INVALID_STATUS);
	}
	
	@Test
	public void returnsCorrectSurgicalToolFromValidStatus() {
		SurgicalToolStatus status = SurgicalToolStatus.fromString(SAMPLE_STATUS);
		assertEquals(SurgicalToolStatus.UNUSED, status);
	}
}
