package net.digitalid.utility.conversion;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.validation.state.Stateless;
import net.digitalid.utility.collections.annotations.elements.NonNullableElements;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.collections.freezable.FreezableArrayList;
import net.digitalid.utility.collections.readonly.ReadOnlyList;
import net.digitalid.utility.conversion.annotations.Constructing;
import net.digitalid.utility.conversion.annotations.IgnoreForConversion;
import net.digitalid.utility.conversion.annotations.RequiredForReconstruction;
import net.digitalid.utility.conversion.exceptions.RecoveryException;
import net.digitalid.utility.conversion.exceptions.StructureException;

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
    public static @Nonnull ConverterAnnotations getAnnotations(@Nonnull AnnotatedElement annotatedElement) {
        final @Nonnull @NonNullableElements ConverterAnnotations fieldMetaData = ConverterAnnotations.get();
        for (@Nonnull Annotation annotation : annotatedElement.getDeclaredAnnotations()) {
            final @Nonnull Annotation annotationObject = annotatedElement.getAnnotation(annotation.annotationType());
            fieldMetaData.put(annotation.annotationType(), annotationObject);
        }
        return fieldMetaData;
    }
    
    /* -------------------------------------------------- Object Recovery -------------------------------------------------- */
    
    /**
     * Returns a constructor for a given type.
     * 
     * @param type the type for which the constructor should be returned.
     * 
     * @return a constructor for a given type.
     * 
     * @throws StructureException if the type has multiple of no declared constructors.
     */
    protected static @Nonnull Constructor<?> getConstructor(@Nonnull Class<?> type) throws StructureException {
        final @Nonnull Constructor<?>[] constructors = type.getDeclaredConstructors();
        if (constructors.length > 1) {
            throw StructureException.get("The type has multiple constructors.");
        } else if (constructors.length == 0) {
            throw StructureException.get("The type does not have any declared constructors.");
        }
        return constructors[0];
    }
    
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
    private static @Nonnull Object recoverObjectWithConstructor(@Nonnull Class<?> type, @Nullable Object... values) throws RecoveryException {
        final @Nonnull Constructor constructor;
        try {
            constructor = getConstructor(type);
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
     * Returns the static recovery method for the given type.
     * 
     * @param type the type for which the static recovery method should be returned.
     *             
     * @return the static recovery method for the given type.
     */
    private static @Nullable Method getStaticRecoveryMethod(@Nonnull Class<?> type) {
        final @Nonnull @NonNullableElements Method[] methods = type.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Constructing.class)) {
                return method;
            }
        }
        return null;
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
    protected static @Nonnull Object recoverNonNullableObject(@Nonnull Class<?> type, @Nullable Object... values) throws RecoveryException {
        final @Nullable Method constructorMethod = getStaticRecoveryMethod(type);
        final @Nullable Object restoredObject;
        if (constructorMethod == null) {
            restoredObject = recoverObjectWithConstructor(type, values);
        } else {
            restoredObject = recoverObjectWithStaticMethod(constructorMethod, values);
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
     * Returns the type parameters of a recovery method or constructor of a given type.
     * 
     * @param type the type for which the type parameters of a recovery method or constructor are returned.
     * 
     * @return the type parameters of a recovery method or constructor of a given type.
     * 
     * @throws StructureException
     */
    private static @Nonnull @NonNullableElements TypeVariable<?>[] getTypesOfConvertibleFields(@Nonnull Class<?> type) throws StructureException {
        final @Nullable Method constructorMethod = getStaticRecoveryMethod(type);
        if (constructorMethod == null) {
            final @Nonnull Constructor<?> constructor = getConstructor(type);
            return constructor.getTypeParameters();
        } else {
            return constructorMethod.getTypeParameters();
        }
    }

    /**
     * Returns all convertible fields of a given type. 
     * A field is considered convertible if it is passed to the recovery method or constructor of the type.
     * 
     * @param type the type for which the convertible fields should be returned.
     * 
     * @return all convertible fields of a given type.
     * 
     * @throws StructureException if a type parameter of a recovery method cannot be identified as a field.
     */
    // TODO: optimize with a cache
    protected static @Frozen @Nonnull @NonNullableElements ReadOnlyList<Field> getConvertibleFields(@Nonnull Class<?> type) throws StructureException {
        final @Nonnull TypeVariable<?>[] typesOfConvertibleFields = getTypesOfConvertibleFields(type);
        final @Nonnull @NonNullableElements FreezableArrayList<Field> fields = FreezableArrayList.get();
        for (TypeVariable<?> typeOfConvertibleField : typesOfConvertibleFields) {
            final @Nonnull String name = typeOfConvertibleField.getName();
            final @Nonnull Field field;
            // TODO: what if the constructor parameter name is not equal to the types field name?
            try {
                field = type.getField(name);
            } catch (NoSuchFieldException e) {
                throw StructureException.get("The class does not have a field '" + name + "'.");
            }
            if (field.isAnnotationPresent(IgnoreForConversion.class) | field.isAnnotationPresent(RequiredForReconstruction.class)) {
                continue;
            }
            fields.add(field);
        }
        return fields.freeze();
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
    protected static @Nonnull Field getConvertibleField(@Nonnull Class<?> type) throws StructureException {
        final @Nonnull @NonNullableElements @Frozen ReadOnlyList<Field> fields = getConvertibleFields(type);
        assert fields.size() == 1 : "There is only one field in type '" + type + "'";
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
        final @Nonnull @NonNullableElements ReadOnlyList<Field> fields = getConvertibleFields(type);
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
