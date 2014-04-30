package ca.ulaval.glo4002.uats.steps.contexts;
 
public class ThreadLocalContext {
    static final class ContextLocal extends ThreadLocal<ContextDataStore> {
        @Override
        protected ContextDataStore initialValue() {
            return new ContextDataStore();
        }
    }

    private static final ThreadLocal<ContextDataStore> threadLocalContext = new ContextLocal();

    public static ContextDataStore get() {
        return threadLocalContext.get();
    }
    
    public static Object getObject(String key) {
        return get().getObject(key);
    }
    
    public static void putObject(String key, Object object) {
    	get().putObject(key, object);
    }
}
