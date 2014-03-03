package ca.ulaval.glo4002.domain.surgicaltool;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SurgicalToolTest {
	private SurgicalTool surgicalTool;

	private static final String SAMPLE_SERIAL_NUMBER = "2321984423QTY";
	private static final String SAMPLE_SERIAL_NUMBER_2 = "64651RF";
	private static final String SAMPLE_TYPE_CODE = "1FT566";
	private static final SurgicalToolStatus SAMPLE_STATUS = SurgicalToolStatus.INUTILISE;
	private static final SurgicalToolStatus SAMPLE_STATUS_2 = SurgicalToolStatus.SOUILLE;

	@Before
	public void init() {
		SurgicalToolBuilder surgicalToolBuilder = new SurgicalToolBuilder();
		surgicalToolBuilder.serialNumber(SAMPLE_SERIAL_NUMBER);
		surgicalToolBuilder.typeCode(SAMPLE_TYPE_CODE);
		surgicalToolBuilder.status(SAMPLE_STATUS);
		surgicalTool = surgicalToolBuilder.build();
	}

	@Test
	public void returnsSerialNumberCorrectly() {
		assertEquals(SAMPLE_SERIAL_NUMBER, surgicalTool.getSerialNumber());
	}

	@Test
	public void setsSerialNumberCorrectly() {
		surgicalTool.setSerialNumber(SAMPLE_SERIAL_NUMBER_2);
		assertEquals(SAMPLE_SERIAL_NUMBER_2, surgicalTool.getSerialNumber());
	}

	@Test
	public void returnsTypeCodeCorrectly() {
		assertEquals(SAMPLE_TYPE_CODE, surgicalTool.getTypeCode());
	}

	@Test
	public void returnsStatusCorrectly() {
		assertEquals(SAMPLE_STATUS, surgicalTool.getStatus());
	}

	@Test
	public void setsStatusCorrectly() {
		surgicalTool.setStatus(SAMPLE_STATUS_2);
		assertEquals(SAMPLE_STATUS_2, surgicalTool.getStatus());
	}
}
