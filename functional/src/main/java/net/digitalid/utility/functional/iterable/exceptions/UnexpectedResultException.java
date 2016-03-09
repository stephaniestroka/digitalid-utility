package net.digitalid.utility.functional.iterable.exceptions;

import javax.annotation.Nonnull;

/**
 *
 */
public class UnexpectedResultException extends Exception {
    
    protected UnexpectedResultException(@Nonnull String message) {
        super(message);
    }
    
    public static UnexpectedResultException with(@Nonnull String message) {
        return new UnexpectedResultException(message);
    }
    
}
