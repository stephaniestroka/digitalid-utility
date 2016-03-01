package net.digitalid.utility.exceptions;

/**
 * This exception indicates that the type does not comply with the expected coding style or class structure.
 */
public class ConformityViolation extends InternalException {
    
    protected ConformityViolation(String message, Exception cause) {
        super(message, cause);
    }
    
    protected ConformityViolation(String message) {
        super(message);
    }
    
    public static ConformityViolation with(String message, Exception cause) {
        return new ConformityViolation(message, cause);
    }
    
    public static ConformityViolation with(String message) {
        return new ConformityViolation(message);
    }
    
}
