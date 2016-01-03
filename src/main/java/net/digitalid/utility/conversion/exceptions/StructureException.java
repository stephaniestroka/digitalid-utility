package net.digitalid.utility.conversion.exceptions;

import javax.annotation.Nonnull;

public class StructureException extends Exception {
    
    private StructureException(@Nonnull String message) {
        super(message);
    }
    
    public static StructureException get(@Nonnull String message) {
        return new StructureException(message);
    }
    
}
