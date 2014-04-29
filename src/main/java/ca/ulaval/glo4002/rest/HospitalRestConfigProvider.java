package ca.ulaval.glo4002.rest;

import java.io.*;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

public class HospitalRestConfigProvider {
	private static final String XML_CONFIG_FILE_NAME = "HospitalRestConfig.xml";
	private static final String XML_CONFIG_FILE_NODE = "HospitalRestConfigProvider";
	private Preferences prefs;

	private static class HospitalRestConfigProviderHolder {
		private static final HospitalRestConfigProvider INSTANCE = new HospitalRestConfigProvider(Preferences.userRoot().node(XML_CONFIG_FILE_NODE));
	}

	protected HospitalRestConfigProvider(Preferences prefs) {
		this.prefs = prefs;
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(XML_CONFIG_FILE_NAME));
			prefs.importPreferences(is);
		} catch (InvalidPreferencesFormatException e) {
		} catch (IOException e) {
			//Static class Preferences have its own error gestion.
			//If an error is detected, it will always return 
			//default parameters for get* functions
		}
	}

	public static HospitalRestConfigProvider getInstance() {
		return HospitalRestConfigProviderHolder.INSTANCE;
	}

	public int getInt(String key, int def) {
		return prefs.getInt(key, def);
	}

	public String getString(String key, String def) {
		return prefs.get(key, def);
	}

}
