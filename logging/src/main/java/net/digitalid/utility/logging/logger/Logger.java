package net.digitalid.utility.logging.logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.logging.Caller;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Version;
import net.digitalid.utility.logging.filter.LoggingFilter;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The logger logs messages of various {@link Level levels}.
 * <p>
 * Some ideas for future improvements (not all of which might be desirable):
 * <ul>
 * <li>In addition to vararg objects, also support vararg producers that are only evaluated when the message is actually logged.</li>
 * <li>Also allow the user to indicate whether the stack-trace of exceptions shall be appended or not.</li>
 * <li>Support logging to several appenders (e.g. fatal error messages also on System.err).</li>
 * <li>Be more flexible in terms of messages (e.g. only expect an object there).</li>
 * <li>Also make the (time) format configurable.</li>
 * </ul>
 * 
 * @see PrintStreamLogger
 */
@Mutable
public abstract class Logger {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the logger which is used for logging.
     */
    public static final @Nonnull Configuration<Logger> logger = Configuration.<Logger>with(StandardOutputLogger.withNoArguments()).addDependency(LoggingFilter.filter).addDependency(Caller.index).addDependency(Version.string);
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    /**
     * Logs the given message with the given level, caller, thread and throwable.
     */
    protected abstract void log(@Nonnull Level level, @Nonnull String caller, @Nonnull String thread, @Nonnull String message, @Nullable Throwable throwable);
    
    /**
     * Logs the given message and throwable if the configured filter accepts them.
     * Each dollar sign in the message is replaced with the corresponding argument.
     */
    @Impure
    public static void log(@Nonnull Level level, @Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        final @Nonnull LoggingFilter filter = LoggingFilter.filter.get();
        if (filter.isPotentiallyLogged(level)) {
            final @Nonnull String caller = Caller.get();
            final @Nonnull String thread = Thread.currentThread().getName();
            final @Nonnull String originalMessage = message.toString();
            final boolean addNoPeriod = originalMessage.endsWith(".") || originalMessage.endsWith(":") || originalMessage.endsWith("\n");
            final @Nonnull String formattedMessage = Strings.format(originalMessage, arguments) + (addNoPeriod ? "" : ".");
            if (filter.isLogged(level, caller, thread, formattedMessage, throwable)) { logger.get().log(level, caller, thread, formattedMessage, throwable); }
        }
    }
    
}
