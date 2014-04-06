package ca.ulaval.glo4002.rest;

import java.io.*;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

public class HospitalRestConfigProvider {
	private static final String XML_CONFIG_FILE_NAME = "HospitalRestConfig.xml";
	private static final String XML_CONFIG_FILE_NODE = "HospitalRestConfigProvider";
	private static Preferences prefs;
	
	private static class HospitalRestConfigProviderHolder {
        private static final HospitalRestConfigProvider instance = new HospitalRestConfigProvider(Preferences.userRoot().node(XML_CONFIG_FILE_NODE));
    }
	
	protected HospitalRestConfigProvider(Preferences prefs) {
		this.prefs = prefs;
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(XML_CONFIG_FILE_NAME));
			Preferences.importPreferences(is);
		} catch (InvalidPreferencesFormatException e) {
		} catch (IOException e) {
		}
	}

	public static HospitalRestConfigProvider getInstance() {
		return HospitalRestConfigProviderHolder.instance;
	}

	public int getInt(String key, int def) {
		return prefs.getInt(key, def);
	}

	public String getString(String key, String def) {
		return prefs.get(key, def);
	}

}
