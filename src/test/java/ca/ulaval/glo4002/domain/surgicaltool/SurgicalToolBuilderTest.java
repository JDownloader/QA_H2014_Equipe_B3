package ca.ulaval.glo4002.domain.surgicaltool;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class SurgicalToolBuilderTest {
	private static final String SAMPLE_SERIAL_NUMBER = "2321984423QTY";
	private static final String SAMPLE_TYPE_CODE = "1FT566";
	private static final SurgicalToolStatus SAMPLE_STATUS = SurgicalToolStatus.INUTILISE;

	private SurgicalToolBuilder surgicalToolBuilder;
	private SurgicalToolBuilder surgicalToolBuilderSpy;

	@Before
	public void init() {
		surgicalToolBuilder = new SurgicalToolBuilder();
		surgicalToolBuilderSpy = spy(surgicalToolBuilder);
	}

	@Test
	public void buildsCorrectly() {
		doBuild();
	}

	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedStatus() {
		doReturn(surgicalToolBuilderSpy).when(surgicalToolBuilderSpy).status(any(SurgicalToolStatus.class));
		doBuild();
	}

	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedTypeCode() {
		doReturn(surgicalToolBuilderSpy).when(surgicalToolBuilderSpy).typeCode(anyString());
		doBuild();
	}

	private void doBuild() {
		surgicalToolBuilderSpy.serialNumber(SAMPLE_SERIAL_NUMBER)
			.typeCode(SAMPLE_TYPE_CODE)
			.status(SAMPLE_STATUS).build();
	}
}
