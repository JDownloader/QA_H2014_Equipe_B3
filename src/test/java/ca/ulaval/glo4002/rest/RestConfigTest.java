package ca.ulaval.glo4002.rest;

import static org.junit.Assert.*;

import java.util.prefs.Preferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ca.ulaval.glo4002.rest.RestConfig;

public class RestConfigTest {
	private RestConfig myConfig;
	@Mock private Preferences prefs = Mockito.mock(Preferences.class);
	
	
	@Before
	public void init() {
		myConfig = new RestConfig(prefs);
	}

	@Test
	public void getInt() {
		Mockito.doReturn(123).when(prefs).getInt("",123);
		int value = myConfig.getInt("", 123);
		assertEquals(123, value);
		Mockito.verify(prefs,Mockito.times(1)).getInt("",123);
	}
}
