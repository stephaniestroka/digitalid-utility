package net.digitalid.utility.logging;

import net.digitalid.utility.logging.logger.Logger;

/**
 * This class makes it easier to {@link Logger#log(net.digitalid.utility.logging.Level, java.lang.String, java.lang.Throwable) log} messages.
 */
public final class Log {
    
    /* -------------------------------------------------- Error -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable as an error.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void error(CharSequence message, Throwable throwable, Object... arguments) {
        Logger.log(Level.ERROR, message, throwable);
    }
    
    /**
     * Logs the given message as an error.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void error(CharSequence message, Object... arguments) {
        Logger.log(Level.ERROR, message, null);
    }
    
    /* -------------------------------------------------- Warning -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable as a warning.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void warning(CharSequence message, Throwable throwable, Object... arguments) {
        Logger.log(Level.WARNING, message, throwable);
    }
    
    /**
     * Logs the given message as a warning.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void warning(CharSequence message, Object... arguments) {
        Logger.log(Level.WARNING, message, null);
    }
    
    /* -------------------------------------------------- Information -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable as information.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void information(CharSequence message, Throwable throwable, Object... arguments) {
        Logger.log(Level.INFORMATION, message, throwable);
    }
    
    /**
     * Logs the given message as information.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void information(CharSequence message, Object... arguments) {
        Logger.log(Level.INFORMATION, message, null);
    }
    
    /* -------------------------------------------------- Debugging -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable for debugging.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void debugging(CharSequence message, Throwable throwable, Object... arguments) {
        Logger.log(Level.DEBUGGING, message, throwable);
    }
    
    /**
     * Logs the given message for debugging.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void debugging(CharSequence message, Object... arguments) {
        Logger.log(Level.DEBUGGING, message, null);
    }
    
    /* -------------------------------------------------- Verbose -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable only in verbose mode.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void verbose(CharSequence message, Throwable throwable, Object... arguments) {
        Logger.log(Level.VERBOSE, message, throwable);
    }
    
    /**
     * Logs the given message only in verbose mode.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    public static void verbose(CharSequence message, Object... arguments) {
        Logger.log(Level.VERBOSE, message, null);
    }
    
}
