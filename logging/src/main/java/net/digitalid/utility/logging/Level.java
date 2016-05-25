package net.digitalid.utility.logging;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.logging.logger.Logger;
import net.digitalid.utility.string.Strings;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class enumerates the various levels of log messages.
 * 
 * @see Log
 */
@Immutable
public enum Level {
    
    /* -------------------------------------------------- Constants -------------------------------------------------- */
    
    /**
     * The level for verbose messages that make it easier to trace program execution.
     */
    VERBOSE(0),
    
    /**
     * The level for debugging messages that help developers locate bugs in the code.
     */
    DEBUGGING(1),
    
    /**
     * The level for information messages that inform about important runtime events.
     */
    INFORMATION(2),
    
    /**
     * The level for warning messages that indicate potential problems in the program.
     */
    WARNING(3),
    
    /**
     * The level for error messages about malfunctions from which the application can possibly recover.
     */
    ERROR(4),
    
    /**
     * The level for fatal messages about malfunctions that prevent a thread or process from continuing.
     */
    FATAL(5),
    
    /**
     * The level to turn logging off.
     */
    OFF(6);
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    private final byte value;
    
    /**
     * Returns the byte representation of this level.
     */
    public byte getValue() {
        return value;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    private Level(int value) {
        this.value = (byte) value;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return Strings.uppercaseFirstCharacter(name().toLowerCase());
    }
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the level above (and including) which the messages are logged.
     */
    public static final @Nonnull Configuration<Level> threshold = Configuration.with(INFORMATION).addDependency(Logger.logger);
    
}
