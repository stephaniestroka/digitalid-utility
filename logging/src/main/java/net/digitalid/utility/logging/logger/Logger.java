package net.digitalid.utility.logging.logger;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.logging.Caller;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.Version;

/**
 * The logger logs messages of various {@link Level levels}.
 * 
 * @see PrintStreamLogger
 */
public abstract class Logger {
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    static {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Log.error("The following issue caused this thread to terminate.", throwable);
            }
        });
    }
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the logger which is used for logging.
     */
    public static final Configuration<Logger> logger = Configuration.<Logger>with(StandardOutputLogger.withNoArguments()).addDependency(Caller.index).addDependency(Version.string);
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    /**
     * Logs the given message with the given level, caller and throwable.
     */
    protected abstract void log(Level level, String caller, String message, Throwable throwable);
    
    /**
     * Logs the given message and throwable if the given level is greater or equal to the configured level.
     * 
     * @require level != null : "The level may not be null.";
     */
    public static void log(Level level, String message, Throwable throwable) {
        Require.that(level != null).orThrow("The level may not be null.");
        
        if (level.getValue() >= Level.threshold.get().getValue()) {
            final boolean addNoPeriod = message.endsWith(".") || message.endsWith(":") || message.endsWith("\n");
            logger.get().log(level, Caller.get(), addNoPeriod ? message : message + ".", throwable);
        }
    }
    
}
