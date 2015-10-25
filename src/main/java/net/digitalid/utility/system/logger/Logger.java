package net.digitalid.utility.system.logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.annotations.state.Initialized;
import net.digitalid.utility.annotations.state.Pure;

/**
 * The logger logs messages of various {@link Level levels}.
 * 
 * @see DefaultLogger
 */
public abstract class Logger {
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Logger –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Stores the actual logger.
     */
    private static @Nullable Logger logger;
    
    /**
     * Initializes this class with the given logger.
     * 
     * @param logger an implementation of this abstract class.
     */
    public static void initialize(@Nonnull Logger logger) {
        Logger.logger = logger;
        
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@Nonnull Thread thread, @Nonnull Throwable throwable) {
                Log.error("The following exception caused this thread to terminate.", throwable);
            }
        });
    }
    
    /**
     * Returns whether this class is initialized.
     * 
     * @return whether this class is initialized.
     */
    @Pure
    public static boolean isInitialized() {
        return logger != null;
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Logging –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Logs the given message with the given tag and exception.
     * 
     * @param level the log level of the message.
     * @param tag a tag to annotate the message.
     * @param message the message to be logged.
     * @param throwable the throwable to log.
     */
    protected abstract void protectedLog(@Nonnull Level level, @Nonnull String tag, @Nonnull String message, @Nullable Throwable throwable);
    
    /**
     * Logs the given message with the given tag and exception.
     * 
     * @param level the log level of the message.
     * @param tag a tag to annotate the message.
     * @param message the message to be logged.
     * @param throwable the throwable to log.
     */
    @Initialized
    public static void log(@Nonnull Level level, @Nonnull String tag, @Nonnull String message, @Nullable Throwable throwable) {
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
    @Initialized
    public static void log(@Nonnull Level level, @Nonnull String tag, @Nonnull String message) {
        log(level, tag, message, null);
    }
    
}
