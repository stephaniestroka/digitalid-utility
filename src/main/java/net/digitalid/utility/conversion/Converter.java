package net.digitalid.utility.conversion;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.collections.annotations.elements.NonNullableElements;
import net.digitalid.utility.collections.freezable.FreezableArrayList;
import net.digitalid.utility.collections.readonly.ReadOnlyList;
import net.digitalid.utility.conversion.annotations.Constructing;
import net.digitalid.utility.conversion.annotations.ConvertToConvertibleType;
import net.digitalid.utility.conversion.annotations.Ignore;
import net.digitalid.utility.conversion.exceptions.ConverterException;
import net.digitalid.utility.conversion.exceptions.FieldConverterException;
import net.digitalid.utility.conversion.exceptions.RestoringException;
import net.digitalid.utility.conversion.exceptions.StoringException;
import net.digitalid.utility.conversion.exceptions.StructureException;

/**
 * Abstract class for format converters. Defines abstract methods that must be implemented in every
 * converter, and helper methods that can be used to identify how to convert types.
 * 
 * @param <C> The specific format converter.
 */
public abstract class Converter<C extends Converter<?>> {
    
    /* -------------------------------------------------- Type Converter -------------------------------------------------- */
    
    /**
     * Maps types to converters. 
     */
    private final Map<Class<?>, C> converters = new HashMap<>();

    /**
     * Returns the converter which converts objects of type boolean or {@link Boolean} to and from the format.
     * 
     * @return the converter which converts objects of type boolean or {@link Boolean} to and from the format.
     */
    protected abstract C getBooleanConverter();
    
    /**
     * Returns the converter which converts objects of type char or {@link Character} to and from the format.
     * 
     * @return the converter which converts objects of type char or {@link Character} to and from the format.
     */
    protected abstract C getCharacterConverter();
    
    /**
     * Returns the converter which converts objects of type {@link String} to and from the format.
     * 
     * @return the converter which converts objects of type {@link String} to and from the format.
     */
    protected abstract C getStringConverter();

    /**
     * Returns the converter which converts objects of type byte[] or {@link Byte[]} to and from the format.
     * 
     * @return the converter which converts objects of type byte[] or {@link Byte[]} to and from the format.
     */
    protected abstract C getBinaryConverter();

    /**
     * Returns the converter which converts objects of type {@link BigInteger} to and from the format.
     * 
     * @return the converter which converts objects of type {@link BigInteger} to and from the format.
     */
    protected abstract C getIntegerConverter();
    
    /**
     * Returns the converter which converts objects of type {@link Byte} to and from the format.
     * 
     * @return the converter which converts objects of type {@link Byte} to and from the format.
     */
    protected abstract C getInteger08Converter();
    
    /**
     * Returns the converter which converts objects of type {@link Short} to and from the format.
     * 
     * @return the converter which converts objects of type {@link Short} to and from the format.
     */
    protected abstract C getInteger16Converter();
     
    /**
     * Returns the converter which converts objects of type {@link Integer} to and from the format.
     * 
     * @return the converter which converts objects of type {@link Integer} to and from the format.
     */
    protected abstract C getInteger32Converter();
     
    /**
     * Returns the converter which converts objects of type {@link Long} to and from the format.
     * 
     * @return the converter which converts objects of type {@link Long} to and from the format.
     */
    protected abstract C getInteger64Converter();
     
    /**
     * Returns the converter which converts objects of type {@link Convertible} to and from the format.
     * 
     * @return the converter which converts objects of type {@link Convertible} to and from the format.
     */
    protected abstract C getConvertibleConverter();
     
    /**
     * Returns the converter which converts objects of type {@link Collection} to and from the format.
     * 
     * @return the converter which converts objects of type {@link Collection} to and from the format.
     */
    protected abstract C getCollectionConverter();
    
    /**
     * Returns the converter which converts objects of type {@link Map} to and from the format.
     * 
     * @return the converter which converts objects of type {@link Map} to and from the format.
     */    
    protected abstract C getMapConverter();
     
    /**
     * Returns the converter which converts objects of type {@link Object[} to and from the format.
     * 
     * @return the converter which converts objects of type {@link Object[]} to and from the format.
     */
    protected abstract C getArrayConverter();
      
    /**
     * Returns the converter which maps objects of one type to another using the {@link TypeToTypeMapper} and then converts it to and from the format.
     *
     * @param typeToTypeMapper The type-to-type mapper maps the field type to a convertible type and vice versa.
     *  
     * @return the converter which maps objects of one type to another using the {@link TypeToTypeMapper} and then converts it to and from the format.
     */
    protected abstract C getTypeToTypeConverter(TypeToTypeMapper<?, ?> typeToTypeMapper);
     
    /**
     * Registers a converter for a specific type.
     * 
     * @param type The type for which the specific converter should be registered.
     * @param converter The converter which should be used when an object of the specific type must be converted.
     */
    protected void registerConverter(@Nonnull Class<?> type, @Nonnull C converter) {
        converters.put(type, converter);
    }

    /**
     * Returns the type converter for the specific type and throws a restoring exception in case the type converter was not found.
     * 
     * @param type The type for which a converter must be found.
     *              
     * @return The converter for the given type.
     * 
     * @throws RestoringException If a type converter was not found for this type.
     */
    protected C getRestoringTypeConverter(@Nonnull Class<?> type) throws RestoringException {
        try {
            return getTypeConverter(type);
        } catch (ConverterException e) {
            throw RestoringException.get(type, "Failed to get appropriate type converter.", e);
        }
    }
    
    /**
     * Returns the type converter for the specific type and throws a storing exception in case the type converter was not found.
     * 
     * @param type The type for which a converter must be found.
     *              
     * @return The converter for the given type.
     * 
     * @throws StoringException If a type converter was not found for this type.
     */
    protected C getStoringTypeConverter(@Nonnull Class<?> type) throws StoringException {
        try {
            return getTypeConverter(type);
        } catch (ConverterException e) {
            throw StoringException.get(type, e);
        }
    }
    
    /**
     * Returns the type converter for a specific type.
     * 
     * @param type The type which is used to find the correct converter.
     *             
     * @return The type converter for the specific type.
     * 
     * @throws ConverterException if no type converter was found for the specified type.
     */
    private @Nonnull C getTypeConverter(@Nonnull Class<?> type) throws ConverterException {
        @Nonnull C typeConverter = converters.get(type);
        
        if (typeConverter == null) {
            if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                typeConverter = getBooleanConverter();
            } else if (type.equals(Character.class) || type.equals(char.class)) {
                typeConverter = getCharacterConverter();
            } else if (type.equals(String.class)) {
                typeConverter = getStringConverter();
            } else if (type.equals(Byte.class) || type.equals(byte.class)) {
                typeConverter = getInteger08Converter();
            } else if (type.equals(Short.class) || type.equals(short.class)) {
                typeConverter = getInteger16Converter();
            } else if (type.equals(Integer.class) || type.equals(int.class)) {
                typeConverter = getInteger32Converter();
            } else if (type.equals(Long.class) || type.equals(long.class)) {
                typeConverter = getInteger64Converter();
            } else if (type.equals(BigInteger.class)) {
                typeConverter = getIntegerConverter();
            } else if (type.equals(Byte[].class) || type.equals(byte[].class)) {
                typeConverter = getBinaryConverter();
            } else if (Convertible.class.isAssignableFrom(type)) {
                typeConverter = getConvertibleConverter();
            } else if (Collections.class.isAssignableFrom(type)) {
                typeConverter = getCollectionConverter();
            } else if (type.isArray()) {
                typeConverter = getArrayConverter();
            } else if (Map.class.isAssignableFrom(type)) {
                typeConverter = getMapConverter();
            } else if (TypeToTypeMapper.class.isAssignableFrom(type)) {
                // TODO: check that TypeToTypeMapper is instantiable
                try {
                    TypeToTypeMapper typeToTypeMapperInstance = (TypeToTypeMapper) type.newInstance();
                    typeConverter = getTypeToTypeConverter(typeToTypeMapperInstance);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw ConverterException.get(type, e);
                }
            } 
            if (typeConverter != null) {
                converters.put(type, typeConverter);
            }
        }
        if (typeConverter == null) {
            throw ConverterException.get(type);
        }
        return typeConverter;
    }
    
    /* -------------------------------------------------- Field Converter -------------------------------------------------- */

    /**
     * Returns a converter for a specific field. The decision, which converter to return, is 
     * either made based on the annotation of the field or the type of the field.
     * 
     * @param field The field for which a converter is returned.
     *              
     * @return The type converter for the field.
     * 
     * @throws FieldConverterException 
     */
    @SuppressWarnings("unchecked")
    protected C getFieldConverter(@Nonnull Field field) throws FieldConverterException {
        boolean isConvertToConvertibleTypeAnnotationPresent = field.isAnnotationPresent(ConvertToConvertibleType.class);
        C fieldConverter;
        if (isConvertToConvertibleTypeAnnotationPresent) {
            ConvertToConvertibleType convertedWith = field.getAnnotation(ConvertToConvertibleType.class);
            Class<? extends TypeToTypeMapper> typeToTypeMapper = convertedWith.typeToTypeMapper();
            
            try {
                fieldConverter = getTypeConverter(typeToTypeMapper);
            } catch (ConverterException e) {
                throw FieldConverterException.get(field.getName(), "The converter for the type mapper '" + typeToTypeMapper + "' of field '" + field.getName() + "', type '" + field.getType() + "' cannot be instantiated.", e);
            }
        } else {
            Class<?> fieldType = field.getType();
            try {
                fieldConverter = getTypeConverter(fieldType);
            } catch (ConverterException e) {
                throw FieldConverterException.get(field.getName(), "The converter for field '" + field.getName() + "', type '" + field.getType() + "' cannot be instantiated.", e);
            }
        }
        return fieldConverter;
    }
    
    /* -------------------------------------------------- Object Construction -------------------------------------------------- */

    /**
     * Constructs an object of a given type with the given values using the type constructor.
     * 
     * @param type The type of the object that is constructed.
     *             
     * @param values The values used to construct the object.
     *               
     * @return The constructed object.
     * 
     * @throws RestoringException Thrown if the object cannot be constructed.
     */
    private static @Nonnull Object constructObjectWithConstructor(@Nonnull Class<?> type, @Nonnull Object values) throws RestoringException {
        Constructor constructor;
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
     * @param constructorMethod The type's constructor method.
     * @param values The values used to construct the object.
     *               
     * @return The constructed object.
     * 
     * @throws RestoringException Thrown if the object cannot be constructed.
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
     * @param type The type for which the static constructor method should be returned.
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
            throw RestoringException.get(type, "Expected non-null object.");
        }
        return restoredObject;
    }
    
    protected static Constructor<?> getConstructor(@Nonnull Class<?> clazz) throws StructureException {
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
