package net.digitalid.utility.exceptions.internal;

import net.digitalid.utility.exceptions.InternalException;

/**
 * This exception indicates a case which was not covered and is typically thrown if a switch over an enumeration needs a default case.
 */
public class UncoveredCaseException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates an uncovered case exception with the given nullable object.
     * 
     * @param object a nullable object to indicate the uncovered case.
     */
    protected UncoveredCaseException(Object object) {
        super("The following case was not covered: " + object);
    }
    
    /**
     * Returns an uncovered case exception with the given nullable object.
     * 
     * @param object a nullable object to indicate the uncovered case.
     */
    public static UncoveredCaseException with(Object object) {
        return new UncoveredCaseException(object);
    }
    
}
