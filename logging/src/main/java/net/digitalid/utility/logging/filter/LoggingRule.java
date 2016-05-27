package net.digitalid.utility.logging.filter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A logging rule defines which messages are accepted and thus logged.
 */
@Immutable
public class LoggingRule {
    
    /* -------------------------------------------------- Threshold -------------------------------------------------- */
    
    private final @Nonnull Level threshold;
    
    /**
     * Returns the threshold at and above which messages are accepted.
     */
    @Pure
    public @Nonnull Level getThreshold() {
        return threshold;
    }
    
    /* -------------------------------------------------- Caller Prefix -------------------------------------------------- */
    
    private final @Nullable String callerPrefix;
    
    /**
     * Returns the prefix that the caller has to have in order to be accepted.
     */
    @Pure
    public @Nullable String getCallerPrefix() {
        return callerPrefix;
    }
    
    /* -------------------------------------------------- Thread Prefix -------------------------------------------------- */
    
    private final @Nullable String threadPrefix;
    
    /**
     * Returns the prefix that the thread has to have in order to be accepted.
     */
    @Pure
    public @Nullable String getThreadPrefix() {
        return threadPrefix;
    }
    
    /* -------------------------------------------------- Message Regex -------------------------------------------------- */
    
    private final @Nullable String messageRegex;
    
    /**
     * Returns the regex that the message has to match in order to be accepted.
     */
    @Pure
    public @Nullable String getMessageRegex() {
        return messageRegex;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected LoggingRule(@Nonnull Level threshold, @Nullable String callerPrefix, @Nullable String threadPrefix, @Nullable String messageRegex) {
        this.threshold = threshold;
        this.callerPrefix = callerPrefix;
        this.threadPrefix = threadPrefix;
        this.messageRegex = messageRegex;
    }
    
    /**
     * Returns a logging rule with the given threshold, caller prefix, thread prefix and message regex.
     */
    @Pure
    public static @Nonnull LoggingRule with(@Nonnull Level threshold, @Nullable String callerPrefix, @Nullable String threadPrefix, @Nullable String messageRegex) {
        return new LoggingRule(threshold, callerPrefix, threadPrefix, messageRegex);
    }
    
    /**
     * Parses the given line and returns the corresponding rule.
     */
    @Pure
    public static @Nonnull LoggingRule parse(@Nonnull String line) /* throws InvalidConfigurationException */ {
        // TODO:
        return new LoggingRule(Level.INFORMATION, null, null, null);
    }
    
    // TODO: Also implement toString() to store a rule in a file.
    
    /* -------------------------------------------------- Acceptance -------------------------------------------------- */
    
    /**
     * Returns whether this rule accepts the given message with the given arguments.
     */
    @Pure
    public boolean accepts(@Nonnull Level level, @Nonnull String caller, @Nonnull String thread, @Nonnull String message) {
        return level.getValue() >= threshold.getValue() &&
                (callerPrefix == null || caller.startsWith(callerPrefix)) &&
                (threadPrefix == null || thread.startsWith(threadPrefix)) &&
                (messageRegex == null || message.matches(messageRegex));
    }
    
}
