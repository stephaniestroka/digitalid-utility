package net.digitalid.utility.exceptions;

/**
 * This exception indicates that a variable had an unexpected value.
 * For example, it is thrown if a switch statement over an enumeration needs a default case.
 */
public class UnexpectedValueException extends InternalException {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected UnexpectedValueException(String variable, Object value) {
        super("It was not expected that $ = $.", variable, value);
    }
    
    /**
     * Returns an unexpected value exception with the given variable and value.
     * 
     * @param variable the non-nullable name of the variable with the unexpected value.
     * @param value the nullable value of the given variable that was not expected.
     */
    public static UnexpectedValueException with(String variable, Object value) {
        return new UnexpectedValueException(variable, value);
    }
    
}
