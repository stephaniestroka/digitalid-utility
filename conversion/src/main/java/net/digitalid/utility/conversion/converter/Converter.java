package net.digitalid.utility.conversion.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.collections.list.ReadOnlyList;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.conversion.exceptions.RecoveryException;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.conversion.reflection.ReflectionUtility;
import net.digitalid.utility.conversion.reflection.exceptions.StructureException;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Abstract class for format converters. Defines abstract methods that must be implemented in every
 * converter, and helper methods that can be used to identify how to convert types.
 */
@Stateless
public abstract class Converter {
    
    /* -------------------------------------------------- Access Annotations -------------------------------------------------- */
    
    /**
     * Returns a map of annotations which carries information such as generic types of a field.
     */
/*    public static @Nonnull ConverterAnnotations getAnnotations(@Nonnull AnnotatedElement annotatedElement) {
        final @Nonnull @NonNullableElements ConverterAnnotations fieldMetaData = ConverterAnnotations.get();
        for (@Nonnull Annotation annotation : annotatedElement.getDeclaredAnnotations()) {
            final @Nonnull Annotation annotationObject = annotatedElement.getAnnotation(annotation.annotationType());
            fieldMetaData.put(annotation.annotationType(), annotationObject);
        }
        return fieldMetaData;
    }*/
    
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
    
    /* -------------------------------------------------- Structure -------------------------------------------------- */

    /**
     * The structure of a type. 
     * SINGLE_TYPE represents types with a single field, because a converted object of this type stores the field`s value as its content.
     * TUPLE represents types with multiple fields, because a converted object of this type must store its fields in a tuple.
     */
    public enum Structure {
        TUPLE, SINGLE_TYPE
    }

   /**
     * Returns a single convertible field of a given type.
     * 
     * @param type the type for which the field should be returned.
     * 
     * @return a single convertible field of a given type.
     *
     * @throws StructureException if a type parameter of a recovery method cannot be identified as a field.
     */
   // TODO (steffi, 19.04.2016): We have the convertible/representing fields in the class information during compile time. 
    protected static @Nonnull Field getConvertibleField(@Nonnull Class<?> type) throws StructureException {
        final @Frozen @NonNullableElements @Nonnull ReadOnlyList<Field> fields = ReflectionUtility.getReconstructionFields(type);
        Require.that(fields.size() == 1).orThrow("There is only one field in type '" + type + "'");
        return fields.getNonNullable(0);
    }
    
    /**
     * Infers the structure of the type by looking at it's recovery fields. The convertible fields are taken from the types of the recovery method, or, if no method with the annotation @Recovery was found, from the types of the constructor.
     * If the recovery method or constructor only take one recoverable parameter, we return a SINGLE_TYPE structure. If it takes multiple recoverable parameters, we return a TUPLE structure.
     * 
     * @param type the type which structure should be inferred.
     * 
     * @return the structure of the type, which is either TUPLE or SINGLE_TYPE.
     * 
     * @throws StructureException thrown if the structure of the type cannot be inferred, e.g. because the recovery method or the constructor do not take any constructable parameters.
     */
    public static @Nonnull Structure inferStructure(@Nonnull Class<?> type) throws StructureException {
        final @Nonnull @NonNullableElements ReadOnlyList<Field> fields = ReflectionUtility.getReconstructionFields(type);
        @Nonnull Structure structureType;
        if (fields.size() == 0) {
            throw StructureException.get("Cannot convert objects without values (empty constructor).");
        } else if (fields.size() == 1) {
            structureType = Structure.SINGLE_TYPE;
        } else {
            structureType = Structure.TUPLE;
        }
        return structureType;
    }
    
}
