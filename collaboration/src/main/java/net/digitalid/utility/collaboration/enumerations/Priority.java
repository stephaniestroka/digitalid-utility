package net.digitalid.collaboration.enumerations;

import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class enumerates the possible priorities of issues.
 */
@Immutable
public enum Priority {
    
    /**
     * The priority of the issue was not specified.
     */
    UNSPECIFIED,
    
    /**
     * The issue should be resolved within a few days.
     */
    HIGH,
    
    /**
     * The issue should be resolved within a few weeks.
     */
    MIDDLE,
    
    /**
     * The issue should be resolved within a few months.
     */
    LOW,
    
    /**
     * The issue is optional and does not have to be resolved.
     */
    OPTIONAL;
    
}
