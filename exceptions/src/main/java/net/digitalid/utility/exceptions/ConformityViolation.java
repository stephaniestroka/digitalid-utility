package net.digitalid.utility.exceptions;

/**
 * This exception indicates that the type does not comply with the expected coding style or class structure.
 */
// TODO: The naming of this exception does not comply with our naming conventions (should end on Exception).
// TODO: Only very general exceptions should be declared in this package and all other where they are used.
// TODO: Add Javadoc for the public methods.
public class ConformityViolation extends InternalException {
    
    protected ConformityViolation(String message, Exception cause, Object... arguments) {
        super(message, cause, arguments);
    }
    
    protected ConformityViolation(String message, Object... arguments) {
        super(message, arguments);
    }
    
    public static ConformityViolation with(String message, Exception cause, Object... arguments) {
        return new ConformityViolation(message, cause, arguments);
    }
    
    public static ConformityViolation with(String message, Exception cause) {
        return new ConformityViolation(message, cause);
    }
    
    public static ConformityViolation with(String message) {
        return new ConformityViolation(message);
    }
    
    public static ConformityViolation with(String message, Object... arguments) {
        return new ConformityViolation(message, arguments);
    }
    
}
