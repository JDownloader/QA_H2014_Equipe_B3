package ca.ulaval.glo4002.domain.surgicaltool;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SurgicalToolTest {
	private SurgicalTool surgicalTool;

	private static final String SAMPLE_SERIAL_NUMBER = "2321984423QTY";
	private static final String SAMPLE_TYPE_CODE = "1FT566";
	private static final SurgicalToolStatus SAMPLE_STATUS = SurgicalToolStatus.INUTILISE;

	@Before
	public void init() {
		surgicalTool = new SurgicalTool(SAMPLE_SERIAL_NUMBER, SAMPLE_TYPE_CODE, SAMPLE_STATUS);
	}

	@Test
	public void nullSerialNumberReportsAsAnonymous() {
		surgicalTool.setSerialNumber(null);
		assertTrue(SAMPLE_SERIAL_NUMBER, surgicalTool.isAnonymous());
	}

	@Test
	public void emptySerialNumberReportsAsAnonymous() {
		surgicalTool.setSerialNumber("");
		assertTrue(SAMPLE_SERIAL_NUMBER, surgicalTool.isAnonymous());
	}
	
	@Test
	public void nonEmptySerialNumberReportsAsNonAnonymous() {
		assertFalse(SAMPLE_SERIAL_NUMBER, surgicalTool.isAnonymous());
	}
}
