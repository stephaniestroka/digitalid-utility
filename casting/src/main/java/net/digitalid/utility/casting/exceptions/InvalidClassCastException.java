package net.digitalid.utility.casting.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.exceptions.InternalException;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This exception is thrown when an object cannot be cast to the desired class.
 */
@Immutable
public class InvalidClassCastException extends InternalException {
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    private final @Nonnull Object object;
    
    /**
     * Returns the object which should have been casted to the target class.
     */
    @Pure
    public @Nonnull Object getObject() {
        return object;
    }
    
    /* -------------------------------------------------- Class -------------------------------------------------- */
    
    private final @Nonnull Class<?> targetClass;
    
    /**
     * Returns the target class to which the object should have been casted.
     */
    @Pure
    public final @Nonnull Class<?> getTargetClass() {
        return targetClass;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected InvalidClassCastException(@Nonnull Object object, @Nonnull Class<?> targetClass) {
        super("An object is of type $ and cannot be cast to $.", object.getClass().getSimpleName(), targetClass.getSimpleName());
        
        this.object = object;
        this.targetClass = targetClass;
    }
    
    /**
     * Returns an invalid class cast exception with the given object and target class.
     * 
     * @param object the object which should have been casted to the target class.
     * @param targetClass the class to which the object should have been casted.
     */
    @Pure
    public static @Nonnull InvalidClassCastException get(@Nonnull Object object, @Nonnull Class<?> targetClass) {
        return new InvalidClassCastException(object, targetClass);
    }
    
}
