package net.digitalid.utility.logging;

import net.digitalid.utility.configuration.Configuration;

/**
 * The logger logs messages of various {@link Level levels}.
 * 
 * @see DefaultLogger
 */
public abstract class Logger {
    
    /* -------------------------------------------------- Logger -------------------------------------------------- */
    
    // TODO: Rename DefaultLogger to StandardOutputLogger.
    public static final Configuration<Logger> configuration = Configuration.of(DefaultLogger.g);
    
    /**
     * Stores the actual logger.
     */
    private static Logger logger;
    
    /**
     * Initializes this class with the given logger.
     * 
     * @param logger an implementation of this abstract class.
     */
    public static void initialize(Logger logger) {
        Logger.logger = logger;
        
        // TODO: Move this initialization to the FileLogger.
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Log.error("The following issue caused this thread to terminate.", throwable);
            }
        });
    }
    
    /**
     * Returns whether this class is initialized.
     * 
     * @return whether this class is initialized.
     */
    public static boolean isInitialized() {
        return logger != null;
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    /**
     * Logs the given message with the given tag and exception.
     * 
     * @param level the log level of the message.
     * @param tag a tag to annotate the message.
     * @param message the message to be logged.
     * @param throwable the throwable to log.
     */
    protected abstract void protectedLog(Level level, String tag, String message, Throwable throwable);
    
    /**
     * Logs the given message with the given tag and exception.
     * 
     * @param level the log level of the message.
     * @param tag a tag to annotate the message.
     * @param message the message to be logged.
     * @param throwable the throwable to log.
     */
    public static void log(Level level, String tag, String message, Throwable throwable) {
        assert logger != null : "This class is initialized.";
        
        logger.protectedLog(level, tag, message, throwable);
    }
    
    /**
     * Logs the given message with the given tag.
     * 
     * @param level the log level of the message.
     * @param tag a tag to annotate the message.
     * @param message the message to be logged.
     */
    public static void log(Level level, String tag, String message) {
        log(level, tag, message, null);
    }
    
}
