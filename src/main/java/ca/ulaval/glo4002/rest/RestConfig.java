package ca.ulaval.glo4002.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

public class RestConfig {
	private static RestConfig instance = null;
	private Preferences prefs;

	public RestConfig(Preferences prefs) {
		this.prefs = prefs;
		try {
			InputStream is = new BufferedInputStream(new FileInputStream("RestConfig.xml"));
			Preferences.importPreferences(is);
		} catch (InvalidPreferencesFormatException e) {
		} catch (IOException e) {
		}
	}

	public static RestConfig getInstance() {
		if (instance == null) {
			instance = new RestConfig(Preferences.userRoot().node("RestConfig"));
		}
		return instance;
	}

	public int getInt(String key, int def) {
		return prefs.getInt(key, def);
	}

}
