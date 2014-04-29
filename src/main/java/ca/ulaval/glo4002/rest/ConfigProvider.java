package ca.ulaval.glo4002.rest;

import java.io.*;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

public class ConfigProvider {
	private static final String XML_CONFIG_FILE_NAME = "HospitalRestConfig.xml";
	private static final String XML_CONFIG_FILE_NODE = "HospitalRestConfigProvider";
	private Preferences prefs;

	private static class ConfigProviderHolder {
		private static final ConfigProvider INSTANCE = new ConfigProvider(Preferences.userRoot().node(XML_CONFIG_FILE_NODE));
	}

	@SuppressWarnings("static-access") //For testability purpose: Allows call to importPreferences to be mockable
	protected ConfigProvider(Preferences prefs) {
		this.prefs = prefs;
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(XML_CONFIG_FILE_NAME));
			prefs.importPreferences(is);
		} catch (InvalidPreferencesFormatException e) {
		} catch (IOException e) {
			//Static class Preferences has its own exception handling.
			//Therefore, in case of an error, getters will always return the specified default value.
		}
	}

	public static ConfigProvider getInstance() {
		return ConfigProviderHolder.INSTANCE;
	}

	public int getInt(String key, int def) {
		return prefs.getInt(key, def);
	}

	public String getString(String key, String def) {
		return prefs.get(key, def);
	}

}
