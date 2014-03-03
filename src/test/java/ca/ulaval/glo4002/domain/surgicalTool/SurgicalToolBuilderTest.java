package ca.ulaval.glo4002.domain.surgicaltool;

import org.junit.*;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolBuilder;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;

public class SurgicalToolBuilderTest {
	private static final String SAMPLE_SERIAL_NUMBER = "2321984423QTY";
	private static final String SAMPLE_TYPE_CODE = "1FT566";
	private static final SurgicalToolStatus SAMPLE_STATUS = SurgicalToolStatus.TERMINEE;
	
	private SurgicalToolBuilder surgicalToolBuilder;
	private SurgicalToolBuilder surgicalToolBuilderSpy;
	
	@Before
	public void setup() {
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
		surgicalToolBuilderSpy.serialNumber(SAMPLE_SERIAL_NUMBER);
		surgicalToolBuilderSpy.typeCode(SAMPLE_TYPE_CODE);
		surgicalToolBuilderSpy.status(SAMPLE_STATUS);
		surgicalToolBuilderSpy.build();
	}
}
