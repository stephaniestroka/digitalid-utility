package net.digitalid.utility.logging.logger;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.logging.Level;

/**
 * The logger logs messages of various {@link Level levels}.
 * 
 * @see PrintStreamLogger
 */
public abstract class Logger {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the logger which is used for logging.
     */
    public static final Configuration<Logger> configuration = Configuration.<Logger>of(StandardOutputLogger.of());
    
    /* -------------------------------------------------- Caller -------------------------------------------------- */
    
    /**
     * Stores the index of the caller in the stack trace, which is different on Android.
     */
    private static final int INDEX = System.getProperty("java.vendor").equals("The Android Project") ? 5 : 4;
    
    /**
     * Returns the caller of the logging method.
     */
    private static String getCaller() {
        final StackTraceElement element = Thread.currentThread().getStackTrace()[INDEX];
        final String className = element.getClassName();
        final int lineNumber = element.getLineNumber();
        return className.substring(className.lastIndexOf('.') + 1) + "." + element.getMethodName() + (lineNumber > 0 ? ":" + lineNumber : "");
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    /**
     * Logs the given message with the given level, caller and throwable.
     */
    protected abstract void log(Level level, String caller, String message, Throwable throwable);
    
    /**
     * Logs the given message and throwable if the given level is greater or equal to the configured level.
     * 
     * @require level != null : "The given level is not null.";
     */
    public static void log(Level level, String message, Throwable throwable) {
        assert level != null : "The given level is not null.";
        
        if (level.getValue() >= Level.configuration.get().getValue()) {
            configuration.get().log(level, getCaller(), message, throwable);
        }
    }
    
}
