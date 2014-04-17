package ca.ulaval.glo4002.uats.steps.contexts;

import java.util.HashMap;

import org.jbehave.core.context.Context;

public class ContextDataStore extends Context {
	private HashMap<String, Object> objectMap = new HashMap<String, Object>();
	
	public Object getObject(String key) {
		return objectMap.get(key);
	}
	
	public Object putObject(String key, Object object) {
		return objectMap.put(key, object);
	}
}
