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
import net.digitalid.utility.collections.annotations.elements.NonNullableElements;
import net.digitalid.utility.collections.freezable.FreezableArrayList;
import net.digitalid.utility.collections.readonly.ReadOnlyList;
import net.digitalid.utility.conversion.annotations.Constructing;
import net.digitalid.utility.conversion.annotations.Ignore;
import net.digitalid.utility.conversion.exceptions.RestoringException;
import net.digitalid.utility.conversion.exceptions.StructureException;

/**
 * Abstract class for format converters. Defines abstract methods that must be implemented in every
 * converter, and helper methods that can be used to identify how to convert types.
 */
public abstract class Converter {
    
    /* -------------------------------------------------- Access Annotations -------------------------------------------------- */
    
    /**
     * Returns a map of annotations which carries information such as generic types of a field.
     * 
     * @param annotatedElement the field from which the meta data is extracted from.
     *              
     * @return a map of annotations which carries information such as generic types of a field.
     */
    public static @Nonnull ConverterAnnotations getAnnotations(@Nonnull AnnotatedElement annotatedElement) {
        final @Nonnull @NonNullableElements ConverterAnnotations fieldMetaData = ConverterAnnotations.get();
        for (@Nonnull Annotation annotation : annotatedElement.getDeclaredAnnotations()) {
            final @Nonnull Annotation annotationObject = annotatedElement.getAnnotation(annotation.annotationType());
            fieldMetaData.put(annotation.annotationType(), annotationObject);
        }
        return fieldMetaData;
    }
    
    /* -------------------------------------------------- Object Construction -------------------------------------------------- */
    
    /**
     * Constructs an object of a given type with the given values using the type constructor.
     * 
     * @param type the type of the object that is constructed.
     * @param values the values used to construct the object.
     *  
     * @return the constructed object.
     * 
     * @throws RestoringException if the object cannot be constructed.
     */
    private static @Nonnull Object constructObjectWithConstructor(@Nonnull Class<?> type, @Nonnull Object values) throws RestoringException {
        @Nonnull Constructor constructor;
        try {
            constructor = getConstructor(type);
        } catch (StructureException e) {
            throw RestoringException.get(type, "Failed to restore object with the constructor.", e);
        }
        try {
            return constructor.newInstance(values);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw RestoringException.get(type, "Failed to restore object with the constructor.", e);
        }
    }
    
    /**
     * Constructs an object with the given values using the type's static constructing method.
     * 
     * @param constructorMethod the type's constructor method.
     * @param values the values used to construct the object.
     *               
     * @return the constructed object.
     * 
     * @throws RestoringException if the object cannot be constructed.
     */
    private static @Nullable Object constructObjectWithStaticMethod(@Nonnull Method constructorMethod, @Nonnull Object[] values) throws RestoringException {
        try {
            return constructorMethod.invoke(null, values);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw RestoringException.get(constructorMethod.getDeclaringClass(), "Failed to restore object with static constructor method.", e);
        }
    }

    /**
     * Returns the static constructor method for the given type.
     * 
     * @param type the type for which the static constructor method should be returned.
     *             
     * @return the static constructor method for the given type.
     */
    private static @Nullable Method getStaticConstructorMethod(@Nonnull Class<?> type) {
        Method[] methods = type.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Constructing.class)) {
                return method;
            }
        }
        return null;
    }
    
    protected static @Nonnull Object constructObjectNonNullable(@Nonnull Class<?> type, @Nonnull Object... values) throws RestoringException {
        @Nullable Method constructorMethod = getStaticConstructorMethod(type);
        @Nullable Object restoredObject;
        if (constructorMethod == null) {
            restoredObject = constructObjectWithConstructor(type, values);
        } else {
            restoredObject = constructObjectWithStaticMethod(constructorMethod, values);
        }
        if (restoredObject == null) {
            // TODO: Rather verify that the constructor method returns a non-nullable object.
            throw RestoringException.get(type, "Expected non-null object.");
        }
        return restoredObject;
    }
    
    protected static @Nonnull Constructor<?> getConstructor(@Nonnull Class<?> clazz) throws StructureException {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length > 1) {
            throw StructureException.get("The class has multiple constructors.");
        } else if (constructors.length == 0) {
            throw StructureException.get("The class does not have any declared constructors.");
        }
        return constructors[0];
    }
    
    /* -------------------------------------------------- Structure -------------------------------------------------- */
    
    protected enum Structure {
        TUPLE, SINGLE_TYPE
    }
    
    private static TypeVariable<? extends Constructor<?>>[] getTypeParameters(@Nonnull Class<?> clazz) throws StructureException {
        // TODO: Shouldn't this also (or only) consider the static constructor method?
        Constructor<?> constructor = getConstructor(clazz);
        return constructor.getTypeParameters();
    }
    
    // TODO: optimize with a cache
    protected static @Nonnull @NonNullableElements ReadOnlyList<Field> getStorableFields(@Nonnull Class<?> clazz) throws StructureException {
        TypeVariable<? extends Constructor<?>>[] typeParameters = getTypeParameters(clazz);
        FreezableArrayList<Field> fields = FreezableArrayList.get();
        for (TypeVariable<? extends Constructor<?>> typeParameter : typeParameters) {
            @Nonnull String name = typeParameter.getName();
            Field field;
            // TODO: what if the constructor parameter name is not equal to the types field name?
            try {
                field = clazz.getField(name);
            } catch (NoSuchFieldException e) {
                throw StructureException.get("The class does not have a field '" + name + "'.");
            }
            // TODO: discuss which annotations to use (e.g. @Ignore, @Helper or @RequiredForReconstruction)
            if (field.isAnnotationPresent(Ignore.class)) {
                continue;
            }
            fields.add(field);
        }
        return fields.freeze();
    }
    
    protected static @Nonnull Field getStorableField(@Nonnull Class<?> clazz) throws StructureException {
        ReadOnlyList<Field> fields = getStorableFields(clazz);
        assert fields.size() == 1 : "There is only one field in type '" + clazz + "'";
        return fields.getNonNullable(0);
    }
    
    // Cases:
    // 1. no type parameters are present, thus the constructor does not hold any fields that can be stored. We throw an exception at that point.
    // 2. one type parameter is present, thus the type of the object is or can be converted to a primitive or structural type. The parameter may not be marked as a 'helper'.
    // 3. multiple type parameters are present, but only one of them is not marked as 'helper' type. The 'helper' (e.g. entity), is injected from outside, e.g. from the class that holds an object of this type. The type parameter that is not marked as a 'helper' is eventually converted into a primitive or structural type
    // 4. multiple type parameters are present and more than one are not marked as 'helper' types. The types not marked as helpers form a tuple.
    protected static Structure inferStructure(Class<?> clazz) throws StructureException {
        @Nonnull @NonNullableElements ReadOnlyList<Field> fields = getStorableFields(clazz);
        Structure structureType;
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
