package net.digitalid.utility.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.collections.freezable.FreezableArrayList;
import net.digitalid.utility.collections.readonly.ReadOnlyList;
import net.digitalid.utility.conversion.annotations.Constructing;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.property.ReadOnlyProperty;
import net.digitalid.utility.reflection.exceptions.StructureException;
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
    public static @Nonnull Constructor<?> getConstructor(@Nonnull Class<?> type) throws StructureException {
        final @Nonnull Constructor<?>[] constructors = type.getDeclaredConstructors();
        if (constructors.length > 1) {
            throw StructureException.get("The type has multiple constructors.");
        } else if (constructors.length == 0) {
            throw StructureException.get("The type does not have any declared constructors.");
        }
        return constructors[0];
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

    /**
     * Returns the type parameters of a recovery method or constructor of a given type.
     *
     * @param type the type for which the type parameters of a recovery method or constructor are returned.
     *
     * @return the type parameters of a recovery method or constructor of a given type.
     *
     * @throws StructureException
     */
    // TODO: instead of expecting the -parameters compiler flag, we should generate a data structure containing information about the parameter names, types and order using annotation processing.
    public static @Nonnull @NonNullableElements Parameter[] getTypesOfFields(@Nonnull Class<?> type) throws StructureException {
        final @Nullable Method constructorMethod = getStaticRecoveryMethod(type);
        if (constructorMethod == null) {
            final @Nonnull Constructor<?> constructor = getConstructor(type);
            return constructor.getParameters();
        } else {
            return constructorMethod.getParameters();
        }
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
        final @Nonnull Parameter[] typesOfConvertibleFields = getTypesOfFields(type);
        final @Nonnull @NonNullableElements FreezableArrayList<Field> fields = FreezableArrayList.get();
        for (Parameter typeOfConvertibleField : typesOfConvertibleFields) {
            final @Nonnull String name = typeOfConvertibleField.getName();
            final @Nonnull Field field;
            // TODO: what if the constructor parameter name is not equal to the types field name?
            try {
                field = type.getField(name);
            } catch (NoSuchFieldException e) {
                throw StructureException.get("The class does not have a field '" + name + "'.");
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
