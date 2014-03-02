package ca.ulaval.glo4002.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

public class HospitalConfig {
	private static HospitalConfig instance = null;
	private Preferences prefs;
	
	protected HospitalConfig(Preferences prefs) {
		this.prefs = prefs;
		try {
			InputStream is = new BufferedInputStream(new FileInputStream("HospitalConfig.xml"));
			Preferences.importPreferences(is);
		} catch ( InvalidPreferencesFormatException e) {
		} catch (IOException e) {
		}
	}
	
	public static HospitalConfig getInstance() {
		if(instance == null) {
			instance = new HospitalConfig(Preferences.userRoot().node("HospitalConfig"));
		}
		return instance;
	}

	public int getInt(String key, int def) {
		return prefs.getInt(key, def);
	}

}
