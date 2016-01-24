package net.digitalid.utility.logging;

import net.digitalid.utility.logging.logger.Logger;

/**
 * This class makes it easier to {@link Logger#log(net.digitalid.utility.logging.Level, java.lang.String, java.lang.Throwable) log} messages.
 */
public final class Log {
    
    /* -------------------------------------------------- Error -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable as an error.
     */
    public static void error(String message, Throwable throwable) {
        Logger.log(Level.ERROR, message, throwable);
    }
    
    /**
     * Logs the given message as an error.
     */
    public static void error(String message) {
        Logger.log(Level.ERROR, message, null);
    }
    
    /* -------------------------------------------------- Warning -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable as a warning.
     */
    public static void warning(String message, Throwable throwable) {
        Logger.log(Level.WARNING, message, throwable);
    }
    
    /**
     * Logs the given message as a warning.
     */
    public static void warning(String message) {
        Logger.log(Level.WARNING, message, null);
    }
    
    /* -------------------------------------------------- Information -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable as an information.
     */
    public static void information(String message, Throwable throwable) {
        Logger.log(Level.INFORMATION, message, throwable);
    }
    
    /**
     * Logs the given message as an information.
     */
    public static void information(String message) {
        Logger.log(Level.INFORMATION, message, null);
    }
    
    /* -------------------------------------------------- Debugging -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable for debugging.
     */
    public static void debugging(String message, Throwable throwable) {
        Logger.log(Level.DEBUGGING, message, throwable);
    }
    
    /**
     * Logs the given message for debugging.
     */
    public static void debugging(String message) {
        Logger.log(Level.DEBUGGING, message, null);
    }
    
    /* -------------------------------------------------- Verbose -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable only in verbose mode.
     */
    public static void verbose(String message, Throwable throwable) {
        Logger.log(Level.VERBOSE, message, throwable);
    }
    
    /**
     * Logs the given message only in verbose mode.
     */
    public static void verbose(String message) {
        Logger.log(Level.VERBOSE, message, null);
    }
    
}
