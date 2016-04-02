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
     * The level for verbose.
     */
    VERBOSE(0),
    
    /**
     * The level for debugging.
     */
    DEBUGGING(1),
    
    /**
     * The level for information.
     */
    INFORMATION(2),
    
    /**
     * The level for warnings.
     */
    WARNING(3),
    
    /**
     * The level for errors.
     */
    ERROR(4),
    
    /**
     * The level for off.
     */
    OFF(5);
    
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
