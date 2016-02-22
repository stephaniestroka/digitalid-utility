package net.digitalid.utility.conversion.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.collections.freezable.FreezableArrayList;
import net.digitalid.utility.collections.readonly.ReadOnlyList;
import net.digitalid.utility.conversion.annotations.Constructing;
import net.digitalid.utility.conversion.annotations.Parameters;
import net.digitalid.utility.conversion.reflection.exceptions.StructureException;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.property.ReadOnlyProperty;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 */
public class ReflectionUtility {

    /**
     * Returns a constructor for a given type.
     *
     * @param type the type for which the constructor should be returned.
     *
     * @return a constructor for a given type.
     *
     * @throws StructureException if the type has multiple of no declared constructors.
     */
    @SuppressWarnings("unchecked")
    public static @Nonnull <T> Constructor<T> getConstructor(@Nonnull Class<T> type) throws StructureException {
        final @Nonnull Constructor<?>[] constructors = type.getDeclaredConstructors();
        if (constructors.length > 1) {
            throw StructureException.get("The type has multiple constructors.");
        } else if (constructors.length == 0) {
            throw StructureException.get("The type does not have any declared constructors.");
        }
        return (Constructor<T>) constructors[0];
    }

    /**
     * Returns the static recovery method for the given type.
     *
     * @param type the type for which the static recovery method should be returned.
     *
     * @return the static recovery method for the given type.
     */
    public static @Nullable Method getStaticRecoveryMethod(@Nonnull Class<?> type) {
        final @Nonnull @NonNullableElements Method[] methods = type.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Constructing.class)) {
                return method;
            }
        }
        return null;
    }
    
    private static @Nonnull @NonNullableElements @Frozen ReadOnlyList<Field> getPropertyFields(@Nonnull Class<?> type) {
        @Nonnull @NonNullableElements FreezableArrayList propertyFields = FreezableArrayList.get();
        for (@Nonnull Field field : type.getFields()) {
            if (ReadOnlyProperty.class.isAssignableFrom(field.getType())) {
                propertyFields.add(field);
            }
        }
        return propertyFields.freeze();
    }
    
    /**
     * Returns a string array of field names that represent the fields of a type that is converted. 
     * The field names are retrieved from the @Parameters annotation, which is set on the constructing method or the constructor.
     */
    private static @Nonnull @NonNullableElements String[] getRepresentingFieldNames(@Nonnull Class<?> type) throws StructureException {
        final @Nullable Method staticRecoveryMethod = getStaticRecoveryMethod(type);
        final @Nonnull Parameters parameters;
        if (staticRecoveryMethod != null) {
            if (!staticRecoveryMethod.isAnnotationPresent(Parameters.class)) {
                throw StructureException.get("Field names for conversion must be recovered from @Parameters annotation, but @Parameters annotation is missing.");
            }
            parameters = staticRecoveryMethod.getAnnotation(Parameters.class);
        } else {
            final @Nullable Constructor<?> constructor = getConstructor(type);
            if (!constructor.isAnnotationPresent(Parameters.class)) {
                throw StructureException.get("Field names for conversion must be recovered from @Parameters annotation, but @Parameters annotation is missing.");
            }
            parameters = constructor.getAnnotation(Parameters.class);
        }
        return parameters.value();
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
    public static @Frozen @Nonnull @NonNullableElements ReadOnlyList<Field> getReconstructionFields(@Nonnull Class<?> type) throws StructureException {
        final @Nonnull @NonNullableElements String[] representingFieldNames = getRepresentingFieldNames(type);
        final @Nonnull @NonNullableElements FreezableArrayList<Field> fields = FreezableArrayList.get();
        for (@Nonnull String representingFieldName : representingFieldNames) {
            final @Nonnull Field field;
            // TODO: what if the constructor parameter name is not equal to the types field name?
            try {
                field = type.getField(representingFieldName);
            } catch (NoSuchFieldException e) {
                throw StructureException.get("The class does not have a field '" + representingFieldName + "'.");
            }
            fields.add(field);
        }
        final @Nonnull @NonNullableElements ReadOnlyList<Field> propertyFields = getPropertyFields(type);
        for (@Nonnull Field propertyField : propertyFields) {
            fields.add(propertyField);
        }
        return fields.freeze();
    }

}
