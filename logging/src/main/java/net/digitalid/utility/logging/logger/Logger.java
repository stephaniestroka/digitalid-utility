package net.digitalid.utility.logging.logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.logging.Caller;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.logging.Version;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.elements.NullableElements;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * The logger logs messages of various {@link Level levels}.
 * 
 * @see PrintStreamLogger
 * 
 * TODO: Implement the desired improvements:
 * 
 * Do:
 * - Make the level filter more flexible (and pluggable) so that different thresholds can be used for different caller prefixes.
 * - Make it possible to read the configuration from a file so that no recompilation is necessary to change the logging levels.
 * - Also allow to filter based on the thread name and maybe even message content?
 * - What about configuring the processor and testing logs?
 * - Ability to reload the configuration file (either automatically on modification or through a method call)?
 * - Ability to switch to faster modes where e.g. the caller is not determined.
 * 
 * Don't (for now):
 * - In addition to vararg objects, also support vararg producers that are only evaluated when the message is actually logged.
 * - Also allow the user to indicate whether the stack-trace of exceptions shall be appended or not.
 * - Support logging to several appenders (e.g. fatal error messages also on System.err.).
 * - Be more flexible in terms of messages (i.e. only expect an object there)?
 * - Also make the (time) format configurable?
 * 
 * Action plan:
 * - Instead of just comparing the level, pass the evaluation to a log filter.
 * - The log filter should have a first method that returns whether it has a rule that logs a message with the given level so that the thread and caller don't have to be determined yet.
 * - All arguments (thread, level, caller, message, throwable and arguments) are then determined and passed to a second method that returns whether this message is to be logged.
 * - Make an implementation that uses rule objects to implement the two mentioned methods.
 * - Extend this implementation so that the rules are loaded (and reloaded) from a configuration file.
 * - The format of the configuration file might be something like 'Level-Threshold:Caller-Prefix:Thread-Prefix:Message-Regex', where every part is optional (yet the preceding colons have to be all there).
 * - The default for the level threshold is 'information' and the last rule used is always "an empty line" (i.e. everything at and above 'information' is logged if no previous rule matches).
 * - Log the Java runtime and the OS version at the beginning of each log-file (or whenever the logger is initialized?).
 * - Store a configuration file in a 'config' folder next to 'src' and 'target' in each project for both testing (verbose for current project) and annotation processing (empty, i.e. information). (Git should ignore these config files.)
 */
@Mutable
public abstract class Logger {
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    // TODO: Shouldn't this initialization be done with an @Initialize?
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
    public static void log(@Nonnull Level level, @Nonnull CharSequence message, @Nullable Throwable throwable, @NonCaptured @Unmodified @Nonnull @NullableElements Object... arguments) {
        if (level.getValue() >= Level.threshold.get().getValue()) {
            final @Nonnull String originalMessage = message.toString();
            final boolean addNoPeriod = originalMessage.endsWith(".") || originalMessage.endsWith(":") || originalMessage.endsWith("\n");
            logger.get().log(level, Caller.get(), Strings.format(originalMessage, arguments) + (addNoPeriod ? "" : "."), throwable);
        }
    }
    
}
