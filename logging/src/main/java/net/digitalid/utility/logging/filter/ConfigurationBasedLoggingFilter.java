package net.digitalid.utility.logging.filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.exceptions.UnexpectedFailureException;
import net.digitalid.utility.file.Files;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Description.
 */
@Mutable
public class ConfigurationBasedLoggingFilter extends RuleBasedLoggingFilter {
    
    /* -------------------------------------------------- File -------------------------------------------------- */
    
    private final @Nonnull File file;
    
    /**
     * Returns the configuration file.
     */
    @Pure
    public @Nonnull File getFile() {
        return file;
    }
    
    /* -------------------------------------------------- Loading -------------------------------------------------- */
    
    /**
     * Reloads the logging rules from the configuration file.
     */
    @Impure
    public void reload() {
        setRules(Files.getNonEmptyTrimmedLines(file).map(LoggingRule::parse));
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ConfigurationBasedLoggingFilter(@Nonnull File file) {
        this.file = file;
        
        if (!file.exists()) {
            try (@Nonnull PrintWriter writer = new PrintWriter(file)) {
                writer.println("# Only messages that match one of the following rules are logged.");
                writer.println("# There is one rule per line written in the following format:");
                writer.println("# Level-Threshold; Caller-Prefix; Thread-Prefix; Message-Regex");
                writer.println("# When skipping all subsequent tokens, the semicolons are optional.");
                writer.println("Information"); // TODO: Pass the default rules also to the constructor.
            } catch (@Nonnull FileNotFoundException exception) {
                throw UnexpectedFailureException.with(exception);
            }
            // TODO: Set the default rules here.
        } else {
            reload();
        }
    }
    
    /**
     * Returns a logging filter with the given configuration file.
     */
    @Pure
    public static @Nonnull ConfigurationBasedLoggingFilter with(@Nonnull File file) {
        return new ConfigurationBasedLoggingFilter(file);
    }
    
}
