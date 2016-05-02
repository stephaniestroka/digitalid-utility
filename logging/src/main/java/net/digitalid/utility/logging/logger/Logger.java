package net.digitalid.utility.logging.logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.logging.Caller;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.Version;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The logger logs messages of various {@link Level levels}.
 * 
 * @see PrintStreamLogger
 */
@Mutable
public abstract class Logger {
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    static {
        // NetBeans 8.1 crashes if you use type annotations on anonymous classes and lambda expressions!
        Thread.setDefaultUncaughtExceptionHandler((Thread thread, Throwable throwable) -> Log.error("The following issue caused this thread to terminate.", throwable));
    }
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the logger which is used for logging.
     */
    public static final @Nonnull Configuration<Logger> logger = Configuration.<Logger>with(StandardOutputLogger.withNoArguments()).addDependency(Caller.index).addDependency(Version.string);
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    /**
     * Logs the given message with the given level, caller and throwable.
     */
    protected abstract void log(@Nonnull Level level, @Nonnull String caller, @Nonnull String message, @Nullable Throwable throwable);
    
    /**
     * Logs the given message and throwable if the given level is greater or equal to the configured level.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void log(@Nonnull Level level, @Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nullable Object... arguments) {
        Require.that(level != null).orThrow("The level may not be null.");
        
        if (level.getValue() >= Level.threshold.get().getValue()) {
            final @Nonnull String originalMessage = message.toString();
            final boolean addNoPeriod = originalMessage.endsWith(".") || originalMessage.endsWith(":") || originalMessage.endsWith("\n");
            logger.get().log(level, Caller.get(), Strings.format(originalMessage, arguments) + (addNoPeriod ? "" : "."), throwable);
        }
    }
    
}
