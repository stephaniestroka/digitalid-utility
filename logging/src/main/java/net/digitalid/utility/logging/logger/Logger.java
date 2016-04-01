package net.digitalid.utility.logging.logger;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.logging.Caller;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.Version;
import net.digitalid.utility.string.Strings;

/**
 * The logger logs messages of various {@link Level levels}.
 * 
 * @see PrintStreamLogger
 */
public abstract class Logger {
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    static {
        Thread.setDefaultUncaughtExceptionHandler((Thread thread, Throwable throwable) -> Log.error("The following issue caused this thread to terminate.", throwable));
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
     * Each dollar sign in the message is replaced with the corresponding argument.
     * 
     * @require level != null : "The level may not be null.";
     */
    public static void log(Level level, CharSequence message, Throwable throwable, Object... arguments) {
        Require.that(level != null).orThrow("The level may not be null.");
        
        if (level.getValue() >= Level.threshold.get().getValue()) {
            final String originalMessage = message.toString();
            final boolean addNoPeriod = originalMessage.endsWith(".") || originalMessage.endsWith(":") || originalMessage.endsWith("\n");
            logger.get().log(level, Caller.get(), Strings.format(originalMessage, arguments) + (addNoPeriod ? "" : "."), throwable);
        }
    }
    
}
