package ca.ulaval.glo4002.rest;

import java.io.*;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

public class HospitalRestConfigProvider {
	private static HospitalRestConfigProvider instance = null;
	private Preferences prefs;

	protected HospitalRestConfigProvider(Preferences prefs) {
		this.prefs = prefs;
		try {
			InputStream is = new BufferedInputStream(new FileInputStream("HospitalRestConfig.xml"));
			Preferences.importPreferences(is);
		} catch (Exception e) {
			return;
		}
	}

	public static HospitalRestConfigProvider getInstance() {
		if (instance == null) {
			instance = new HospitalRestConfigProvider(Preferences.userRoot().node("HospitalRestConfig"));
		}
		return instance;
	}

	public int getInt(String key, int def) {
		return prefs.getInt(key, def);
	}

	public String getString(String key, String def) {
		return prefs.get(key, def);
	}

}
