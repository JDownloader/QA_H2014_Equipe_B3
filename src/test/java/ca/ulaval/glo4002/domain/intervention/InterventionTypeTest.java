package ca.ulaval.glo4002.domain.intervention;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InterventionTypeTest {
	private static final String INVALID_TYPE = "COWUR";
	private static final String SAMPLE_TYPE = "COEUR";

	@Test(expected = InterventionTypeParseException.class)
	public void throwsIllegalArgumentExceptionOnInvalidStatusString() {
		InterventionType.fromString(INVALID_TYPE);
	}

	@Test
	public void returnsCorrectSurgicalToolFromValidStatus() {
		InterventionType type = InterventionType.fromString(SAMPLE_TYPE);
		assertEquals(InterventionType.HEART, type);
	}
}
