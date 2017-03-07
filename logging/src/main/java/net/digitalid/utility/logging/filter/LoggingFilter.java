package net.digitalid.utility.logging.filter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Instances of this class are used to filter log messages.
 * 
 * @see LevelBasedLoggingFilter
 * @see RuleBasedLoggingFilter
 */
@Mutable
public abstract class LoggingFilter {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the filter which is used to filter the log messages.
     */
    public static final @Nonnull Configuration<LoggingFilter> filter = Configuration.with(LevelBasedLoggingFilter.with(Level.INFORMATION));
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    /**
     * Returns whether a message with the given level is potentially logged.
     */
    @Pure
    public abstract boolean isPotentiallyLogged(@Nonnull Level level);
    
    /**
     * Returns whether the given message with the given arguments is (to be) logged.
     */
    @Pure
    public abstract boolean isLogged(@Nonnull Level level, @Nonnull String caller, @Nonnull String thread, @Nonnull String message, @Nullable Throwable throwable);
    
}
