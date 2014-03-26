package ca.ulaval.glo4002.rest;

import static org.junit.Assert.*;

import java.util.prefs.Preferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


public class HospitalRestConfigProviderTest {
	private HospitalRestConfigProvider myConfig;
	int INT_PREF_DEFAULT_VALUE = 123;
	int INT_PREF_VALUE = 321;
	String KEY_PREF_STING = "KEY";
	String STRING_PREF_DEFAULT_VALUE = "abcdefj";
	String STRING_PREF_VALUE = "jfedcba";
	private Preferences prefs;

	@Before
	public void init() {
		prefs = Mockito.mock(Preferences.class);
		myConfig = new HospitalRestConfigProvider(prefs);
	}

	@Test
	public void getInt() {
		Mockito.doReturn(INT_PREF_VALUE).when(prefs).getInt(KEY_PREF_STING, INT_PREF_DEFAULT_VALUE);
		int value = myConfig.getInt(KEY_PREF_STING, INT_PREF_DEFAULT_VALUE);
		assertEquals(INT_PREF_VALUE, value);
	}

	@Test
	public void getIntCallPreferences() {
		Mockito.doReturn(INT_PREF_VALUE).when(prefs).getInt(KEY_PREF_STING, INT_PREF_DEFAULT_VALUE);
		myConfig.getInt(KEY_PREF_STING, INT_PREF_DEFAULT_VALUE);
		Mockito.verify(prefs,Mockito.times(1)).getInt(KEY_PREF_STING, INT_PREF_DEFAULT_VALUE);
	}

	@Test
	public void getString() {
		Mockito.doReturn(STRING_PREF_VALUE).when(prefs).get(KEY_PREF_STING, STRING_PREF_DEFAULT_VALUE);
		String value = myConfig.getString(KEY_PREF_STING, STRING_PREF_DEFAULT_VALUE);
		assertEquals(STRING_PREF_VALUE, value);
	}

	@Test
	public void getStringCallPreferences() {
		Mockito.doReturn(STRING_PREF_VALUE).when(prefs).get(KEY_PREF_STING, STRING_PREF_DEFAULT_VALUE);
		myConfig.getString(KEY_PREF_STING, STRING_PREF_DEFAULT_VALUE);
		Mockito.verify(prefs,Mockito.times(1)).get(KEY_PREF_STING, STRING_PREF_DEFAULT_VALUE);
	}
}
