package ca.ulaval.glo4002.rest;

import static org.junit.Assert.*;

import java.util.prefs.Preferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class HospitalRestConfigProviderTest {
	private HospitalRestConfigProvider configProvider;
	private static final int INT_PREF_DEFAULT_VALUE = 123;
	private static final int INT_PREF_VALUE = 321;
	private static final String KEY_PREF_STING = "KEY";
	private static final String STRING_PREF_DEFAULT_VALUE = "abcdefj";
	private static final String STRING_PREF_VALUE = "jfedcba";
	
	@Mock
	private Preferences prefs = Mockito.mock(Preferences.class);

	@Before
	public void init() {
		configProvider = new HospitalRestConfigProvider(prefs);
	}

	@Test
	public void verifyReturnsCorrectInt() {
		doReturn(INT_PREF_VALUE).when(prefs).getInt(KEY_PREF_STING, INT_PREF_DEFAULT_VALUE);
		int value = configProvider.getInt(KEY_PREF_STING, INT_PREF_DEFAULT_VALUE);
		assertEquals(INT_PREF_VALUE, value);
		verify(prefs, Mockito.times(1)).getInt(KEY_PREF_STING, INT_PREF_DEFAULT_VALUE);
	}

	@Test
	public void verifyReturnsCorrectString() {
		doReturn(STRING_PREF_VALUE).when(prefs).get(KEY_PREF_STING, STRING_PREF_DEFAULT_VALUE);
		String value = configProvider.getString(KEY_PREF_STING, STRING_PREF_DEFAULT_VALUE);
		assertEquals(STRING_PREF_VALUE, value);
		verify(prefs, Mockito.times(1)).get(KEY_PREF_STING, STRING_PREF_DEFAULT_VALUE);
	}
}
