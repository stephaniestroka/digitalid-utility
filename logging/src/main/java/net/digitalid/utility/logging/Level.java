package net.digitalid.utility.logging;

import net.digitalid.utility.configuration.Configuration;

/**
 * This class enumerates the various levels of log messages.
 * 
 * @see Log
 */
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
    
    /**
     * Stores the byte representation of this level.
     */
    private final byte value;
    
    /**
     * Returns the byte representation of this level.
     */
    public byte getValue() {
        return value;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a level with the given value.
     */
    private Level(int value) {
        this.value = (byte) value;
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Override
    public String toString() {
        final String string = name().toLowerCase();
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the level above (and including) which the messages are logged.
     */
    public static final Configuration<Level> threshold = Configuration.with(INFORMATION);
    
}
