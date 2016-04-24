package net.digitalid.utility.conversion.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.conversion.exceptions.RecoveryException;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Abstract class for format converters. Defines abstract methods that must be implemented in every
 * converter, and helper methods that can be used to identify how to convert types.
 */
@Stateless
public abstract class Converter {
    
    /* -------------------------------------------------- Object Recovery -------------------------------------------------- */
    
    /**
     * Recovers an object of a given type with the given values using the type constructor.
     * 
     * @param type the type of the object that is recovered.
     * @param values the values used to construct the object.
     *  
     * @return the recovered object.
     * 
     * @throws RecoveryException if the object cannot be constructed.
     */
    private static @Nonnull <T> T recoverObjectWithConstructor(@Nonnull Class<T> type, @Nullable Object... values) throws RecoveryException {
        final @Nonnull Constructor<T> constructor;
        try {
            constructor = ReflectionUtility.getConstructor(type);
        } catch (StructureException e) {
            throw RecoveryException.get(type, "Failed to restore object with the constructor.", e);
        }
        try {
            return constructor.newInstance(values);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw RecoveryException.get(type, "Failed to restore object with the constructor.", e);
        }
    }
    
    /**
     * Recovers an object with the given values using the type's static recovery method.
     * 
     * @param recoveryMethod the type's recovery method.
     * @param values the values used to recover the object.
     *               
     * @return the recovered object.
     * 
     * @throws RecoveryException if the object cannot be recovered.
     */
    private static @Nullable Object recoverObjectWithStaticMethod(@Nonnull Method recoveryMethod, @Nullable Object... values) throws RecoveryException {
        try {
            return recoveryMethod.invoke(null, values);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw RecoveryException.get(recoveryMethod.getDeclaringClass(), "Failed to restore object with static constructor method.", e);
        }
    }


    /**
     * Recovers an object of a given type using the given values.
     * 
     * @param type the type of the object which should be recovered.
     * @param values the values used to recover the object.
     * 
     * @return the recovered object.
     * 
     * @throws RecoveryException if the recovery method unexpectedly returns null.
     */
    @SuppressWarnings("unchecked")
    protected static @Nonnull <T> T recoverNonNullableObject(@Nonnull Class<T> type, @Nullable Object... values) throws RecoveryException {
        final @Nullable Method constructorMethod = ReflectionUtility.getStaticRecoveryMethod(type);
        final @Nullable T restoredObject;
        if (constructorMethod == null) {
            restoredObject = recoverObjectWithConstructor(type, values);
        } else {
            restoredObject = (T) recoverObjectWithStaticMethod(constructorMethod, values);
        }
        if (restoredObject == null) {
            // TODO: Rather verify that the constructor method returns a non-nullable object.
            throw RecoveryException.get(type, "Expected non-null object.");
        }
        return restoredObject;
    }
    
}
