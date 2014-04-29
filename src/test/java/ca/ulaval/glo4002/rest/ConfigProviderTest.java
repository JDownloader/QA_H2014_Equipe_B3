package ca.ulaval.glo4002.rest;

import static org.junit.Assert.*;

import java.util.prefs.Preferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConfigProviderTest {
	private static final int INT_PREF_DEFAULT_VALUE = 123;
	private static final int INT_PREF_VALUE = 321;
	private static final String KEY_PREF_STRING = "KEY";
	private static final String STRING_PREF_DEFAULT_VALUE = "abcdefj";
	private static final String STRING_PREF_VALUE = "jfedcba";
	private static Preferences prefsMock;
	private ConfigProvider configProvider;

	@Before
	public void init() {
		prefsMock = Mockito.mock(Preferences.class);
		configProvider = new ConfigProvider(prefsMock);
	}

	@Test
	public void getIntReturnsCorrectPreferenceValue() {
		Mockito.doReturn(INT_PREF_VALUE).when(prefsMock).getInt(KEY_PREF_STRING, INT_PREF_DEFAULT_VALUE);
		int value = configProvider.getInt(KEY_PREF_STRING, INT_PREF_DEFAULT_VALUE);
		assertEquals(INT_PREF_VALUE, value);
	}

	@Test
	public void getIntCallsPreferences() {
		Mockito.doReturn(INT_PREF_VALUE).when(prefsMock).getInt(KEY_PREF_STRING, INT_PREF_DEFAULT_VALUE);
		configProvider.getInt(KEY_PREF_STRING, INT_PREF_DEFAULT_VALUE);
		Mockito.verify(prefsMock, Mockito.times(1)).getInt(KEY_PREF_STRING, INT_PREF_DEFAULT_VALUE);
	}

	@Test
	public void getStringReturnsCorrectPreferenceValue() {
		Mockito.doReturn(STRING_PREF_VALUE).when(prefsMock).get(KEY_PREF_STRING, STRING_PREF_DEFAULT_VALUE);
		String value = configProvider.getString(KEY_PREF_STRING, STRING_PREF_DEFAULT_VALUE);
		assertEquals(STRING_PREF_VALUE, value);
	}

	@Test
	public void getStringCallsPreferences() {
		Mockito.doReturn(STRING_PREF_VALUE).when(prefsMock).get(KEY_PREF_STRING, STRING_PREF_DEFAULT_VALUE);
		configProvider.getString(KEY_PREF_STRING, STRING_PREF_DEFAULT_VALUE);
		Mockito.verify(prefsMock, Mockito.times(1)).get(KEY_PREF_STRING, STRING_PREF_DEFAULT_VALUE);
	}
}
