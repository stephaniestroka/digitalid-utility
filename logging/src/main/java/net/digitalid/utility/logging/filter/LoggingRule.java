package net.digitalid.utility.logging.filter;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
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
     * Returns a logging rule with the given threshold, caller prefix and thread prefix.
     */
    @Pure
    public static @Nonnull LoggingRule with(@Nonnull Level threshold, @Nullable String callerPrefix, @Nullable String threadPrefix) {
        return new LoggingRule(threshold, callerPrefix, threadPrefix, null);
    }
    
    /**
     * Returns a logging rule with the given threshold and caller prefix.
     */
    @Pure
    public static @Nonnull LoggingRule with(@Nonnull Level threshold, @Nullable String callerPrefix) {
        return new LoggingRule(threshold, callerPrefix, null, null);
    }
    
    /**
     * Returns a logging rule with the given threshold.
     */
    @Pure
    public static @Nonnull LoggingRule with(@Nonnull Level threshold) {
        return new LoggingRule(threshold, null, null, null);
    }
    
    /* -------------------------------------------------- Encoding and Decoding -------------------------------------------------- */
    
    /**
     * Encodes this logging rule.
     */
    @Pure
    public @Nonnull String encode() {
        final @Nonnull StringBuilder result = new StringBuilder(threshold.toString());
        if (callerPrefix != null || threadPrefix != null || messageRegex != null) {
            result.append(";");
            if (callerPrefix != null) { result.append(" ").append(callerPrefix); }
            if (threadPrefix != null || messageRegex != null) {
                result.append(";");
                if (threadPrefix != null) { result.append(" ").append(threadPrefix); }
                if (messageRegex != null) { result.append("; ").append(messageRegex); }
            }
        }
        return result.toString();
    }
    
    /**
     * Returns the token with the given index or null if the array is not long enough or the token is empty.
     */
    @Pure
    private static @Nullable String getNonEmpty(@NonCaptured @Unmodified @Nonnull @NonNullableElements String[] tokens, int index) {
        if (tokens.length > index) {
            final @Nonnull String token = tokens[index].trim();
            if (!token.isEmpty()) { return token; }
        }
        return null;
    }
    
    /**
     * Decodes the given line and returns the corresponding rule.
     * 
     * @throws IllegalArgumentException if a rule has an invalid level.
     */
    @Pure
    public static @Nonnull LoggingRule decode(@Nonnull String line) throws IllegalArgumentException {
        final @Nonnull @NonNullableElements String[] tokens = line.split(";", 4);
        final @Nonnull Level threshold = Level.valueOf(tokens[0].trim().toUpperCase());
        final @Nullable String callerPrefix = getNonEmpty(tokens, 1);
        final @Nullable String threadPrefix = getNonEmpty(tokens, 2);
        final @Nullable String messageRegex = getNonEmpty(tokens, 3);
        return new LoggingRule(threshold, callerPrefix, threadPrefix, messageRegex);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean equals(@NonCaptured @Unmodified @Nullable Object object) {
        if (object == this) { return true; }
        if (object == null || !(object instanceof LoggingRule)) { return false; }
        final @Nonnull LoggingRule that = (LoggingRule) object;
        return this.threshold == that.threshold && Objects.equals(this.callerPrefix, that.callerPrefix) && Objects.equals(this.threadPrefix, that.threadPrefix) && Objects.equals(this.messageRegex, that.messageRegex);
    }
    
    @Pure
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + threshold.hashCode();
        hash = 83 * hash + Objects.hashCode(callerPrefix);
        hash = 83 * hash + Objects.hashCode(threadPrefix);
        hash = 83 * hash + Objects.hashCode(messageRegex);
        return hash;
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return "LoggingRule(threshold: " + threshold + ", callerPrefix: " + Quotes.inCode(callerPrefix) + ", threadPrefix: " + Quotes.inCode(threadPrefix) + ", messageRegex: " + Quotes.inCode(messageRegex) + ")";
    }
    
}
