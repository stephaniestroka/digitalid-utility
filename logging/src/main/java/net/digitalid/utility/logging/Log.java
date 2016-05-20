package net.digitalid.utility.logging;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.logging.logger.Logger;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * This class makes it easier to {@link Logger#log(net.digitalid.utility.logging.Level, java.lang.CharSequence, java.lang.Throwable, java.lang.Object...) log} messages.
 */
@Utility
public final class Log {
    
    /* -------------------------------------------------- Error -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable as an error.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void error(@Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.ERROR, message, throwable);
    }
    
    /**
     * Logs the given message as an error.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void error(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.ERROR, message, null);
    }
    
    /* -------------------------------------------------- Warning -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable as a warning.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void warning(@Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.WARNING, message, throwable);
    }
    
    /**
     * Logs the given message as a warning.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void warning(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.WARNING, message, null);
    }
    
    /* -------------------------------------------------- Information -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable as information.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void information(@Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.INFORMATION, message, throwable);
    }
    
    /**
     * Logs the given message as information.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void information(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.INFORMATION, message, null);
    }
    
    /* -------------------------------------------------- Debugging -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable for debugging.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void debugging(@Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.DEBUGGING, message, throwable);
    }
    
    /**
     * Logs the given message for debugging.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void debugging(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.DEBUGGING, message, null);
    }
    
    /* -------------------------------------------------- Verbose -------------------------------------------------- */
    
    /**
     * Logs the given message and throwable only in verbose mode.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void verbose(@Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.VERBOSE, message, throwable);
    }
    
    /**
     * Logs the given message only in verbose mode.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void verbose(@Nonnull CharSequence message, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        Logger.log(Level.VERBOSE, message, null);
    }
    
}
