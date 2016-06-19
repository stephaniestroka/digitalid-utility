package net.digitalid.utility.logging.filter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This logging filter accepts all messages at and above the given threshold.
 */
@Immutable
public class LevelBasedLoggingFilter extends LoggingFilter {
    
    /* -------------------------------------------------- Level -------------------------------------------------- */
    
    private final @Nonnull Level threshold;
    
    /**
     * Returns the threshold at and above which the messages are accepted.
     */
    @Pure
    public @Nonnull Level getThreshold() {
        return threshold;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected LevelBasedLoggingFilter(@Nonnull Level threshold) {
        this.threshold = threshold;
    }
    
    /**
     * Returns a logging filter with the given threshold.
     */
    @Pure
    public static @Nonnull LevelBasedLoggingFilter with(@Nonnull Level threshold) {
        return new LevelBasedLoggingFilter(threshold);
    }
    
    /* -------------------------------------------------- Methods -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isPotentiallyLogged(@Nonnull Level level) {
        return level.getValue() >= threshold.getValue();
    }
    
    @Pure
    @Override
    public boolean isLogged(@Nonnull Level level, @Nonnull String caller, @Nonnull String thread, @Nonnull String message, @Nullable Throwable throwable) {
        return level.getValue() >= threshold.getValue();
    }
    
}
