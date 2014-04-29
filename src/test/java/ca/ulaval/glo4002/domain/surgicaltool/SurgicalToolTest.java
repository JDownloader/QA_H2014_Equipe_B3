package ca.ulaval.glo4002.domain.surgicaltool;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SurgicalToolTest {
	private static final String SAMPLE_SERIAL_NUMBER = "2321984423QTY";
	private static final String ANOTHER_SAMPLE_SERIAL_NUMBER = "4321984423QTY";
	private static final String SAMPLE_TYPE_CODE = "1FT566";
	private static final SurgicalToolStatus SAMPLE_STATUS = SurgicalToolStatus.UNUSED;
	
	private SurgicalTool surgicalTool;
	private Observer observerMock;

	@Before
	public void init() {
		surgicalTool = new SurgicalTool(SAMPLE_SERIAL_NUMBER, SAMPLE_TYPE_CODE, SAMPLE_STATUS);
		observerMock = mock(Observer.class);
	}

	@Test
	public void nullSerialNumberReportsAsAnonymous() {
		surgicalTool.changeSerialNumber(null);
		assertTrue(SAMPLE_SERIAL_NUMBER, surgicalTool.isAnonymous());
	}

	@Test
	public void emptySerialNumberReportsAsAnonymous() {
		surgicalTool.changeSerialNumber("");
		assertTrue(SAMPLE_SERIAL_NUMBER, surgicalTool.isAnonymous());
	}
	
	@Test
	public void nonEmptySerialNumberReportsAsNonAnonymous() {
		assertFalse(SAMPLE_SERIAL_NUMBER, surgicalTool.isAnonymous());
	}
	
	@Test
	public void changeSerialNumberNotifiesObserver() {
		surgicalTool.addObserver(observerMock);
		surgicalTool.changeSerialNumber(SAMPLE_SERIAL_NUMBER);
		verify(observerMock).update(any(Observable.class), any(Object.class));
	}
	
	@Test
	public void comparesToSerialNumberReturnsTrueWhenComparingWithSameSerialNumber() {
		assertTrue(surgicalTool.compareToSerialNumber(SAMPLE_SERIAL_NUMBER));
	}
	
	@Test
	public void comparesToSerialNumberReturnsFalseWhenComparingWithDifferentSerialNumber() {
		assertFalse(surgicalTool.compareToSerialNumber(ANOTHER_SAMPLE_SERIAL_NUMBER));
	}
}
