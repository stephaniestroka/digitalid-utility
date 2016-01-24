package net.digitalid.utility.castable.exceptions;

import javax.annotation.Nonnull;

import net.digitalid.utility.exceptions.CustomException;
import net.digitalid.utility.validation.state.Immutable;
import net.digitalid.utility.validation.state.Pure;

/**
 * This exception is thrown when an object cannot be cast to the desired class.
 */
@Immutable
public class InvalidClassCastException extends CustomException { // TODO: Rather extend a ValidationException.
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    /**
     * Stores the object which should have been casted to the target class.
     */
    private final @Nonnull Object object;
    
    /**
     * Returns the object which should have been casted.
     * 
     * @return the object which should have been casted.
     */
    @Pure
    public final @Nonnull Object getObject() {
        return object;
    }
    
    /* -------------------------------------------------- Class -------------------------------------------------- */
    
    /**
     * Stores the class to which the object should have been casted.
     */
    private final @Nonnull Class<?> targetClass;
    
    /**
     * Returns the class to which the object should have been casted.
     * 
     * @return the class to which the object should have been casted.
     */
    @Pure
    public final @Nonnull Class<?> getTargetClass() {
        return targetClass;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new invalid class cast exception with the given object and target class.
     * 
     * @param object the object which should have been casted to the target class.
     * @param targetClass the class to which the object should have been casted.
     */
    protected InvalidClassCastException(@Nonnull Object object, @Nonnull Class<?> targetClass) {
        super("An object is of type " + object.getClass().getSimpleName() + " and cannot be cast to " + targetClass.getSimpleName() + ".");
        
        this.object = object;
        this.targetClass = targetClass;
    }
    
    /**
     * Returns a new invalid class cast exception with the given object and target class.
     * 
     * @param object the object which should have been casted to the target class.
     * @param targetClass the class to which the object should have been casted.
     * 
     * @return a new invalid class cast exception with the given object and target class.
     */
    @Pure
    public static @Nonnull InvalidClassCastException get(@Nonnull Object object, @Nonnull Class<?> targetClass) {
        return new InvalidClassCastException(object, targetClass);
    }
    
}
