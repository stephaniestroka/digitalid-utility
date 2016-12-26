package net.digitalid.utility.conversion.converter;

import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class enumerates the various representations of objects.
 */
@Immutable
public enum Representation {
    
    /**
     * The encoding of an object can contain information that is only available at the current site (like generated primary keys).
     */
    INTERNAL,
    
    /**
     * The encoding of an object may not contain information that is only available at the current site because it is to be shared.
     */
    EXTERNAL;
    
}
