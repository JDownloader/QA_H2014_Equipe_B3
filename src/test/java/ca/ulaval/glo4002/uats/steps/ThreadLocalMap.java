package ca.ulaval.glo4002.uats.steps;

import java.util.HashMap;

public class ThreadLocalMap {
	private static ThreadLocal<HashMap<String, Object>> localMap = new ThreadLocal<HashMap<String, Object>>();

	public static Object getObject(String key) {
		return localMap.get().get(key);
	}

	public static Object putObject(String key, Object object) {
		synchronized(ThreadLocalMap.class) {
    		if (localMap.get() == null) {
    			localMap.set(new HashMap<String, Object>());
    		}
		}
		return localMap.get().put(key, object);
	}
}
