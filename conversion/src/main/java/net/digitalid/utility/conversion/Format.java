package net.digitalid.utility.conversion;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.collections.freezable.FreezableHashMap;
import net.digitalid.utility.collections.freezable.FreezableMap;
import net.digitalid.utility.conversion.annotations.ConvertToConvertibleType;
import net.digitalid.utility.conversion.exceptions.ConverterNotFoundException;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.generator.conversion.Convertible;
import net.digitalid.utility.property.ReadOnlyProperty;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The abstract format class defines all converters, which must be implemented by each format.
 * 
 * @param <C> the specific format converter.
 */
@Immutable
public abstract class Format<C extends Converter> {
    
    /* -------------------------------------------------- Required Converters -------------------------------------------------- */
    
    /**
     * Returns the converter which converts objects of type boolean or {@link Boolean} to and from the format.
     * 
     * @return the converter which converts objects of type boolean or {@link Boolean} to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getBooleanConverter();
    
    /**
     * Returns the converter which converts objects of type byte or {@link Byte} to and from the format.
     * 
     * @return the converter which converts objects of type byte or {@link Byte} to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getInteger08Converter();
    
    /**
     * Returns the converter which converts objects of type short or {@link Short} to and from the format.
     * 
     * @return the converter which converts objects of type short or {@link Short} to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getInteger16Converter();
     
    /**
     * Returns the converter which converts objects of type int or {@link Integer} to and from the format.
     * 
     * @return the converter which converts objects of type int or {@link Integer} to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getInteger32Converter();
     
    /**
     * Returns the converter which converts objects of type long or {@link Long} to and from the format.
     * 
     * @return the converter which converts objects of type long or {@link Long} to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getInteger64Converter();
     
    /**
     * Returns the converter which converts objects of type {@link BigInteger} to and from the format.
     * 
     * @return the converter which converts objects of type {@link BigInteger} to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getIntegerConverter();
    
    /**
     * Returns the converter which converts objects of type char or {@link Character} to and from the format.
     * 
     * @return the converter which converts objects of type char or {@link Character} to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getCharacterConverter();
    
    /**
     * Returns the converter which converts objects of type {@link String} to and from the format.
     * 
     * @return the converter which converts objects of type {@link String} to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getStringConverter();

    /**
     * Returns the converter which converts objects of type byte[] or {@link Byte[]} to and from the format.
     * 
     * @return the converter which converts objects of type byte[] or {@link Byte[]} to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getBinaryConverter();
    
    /**
     * Returns the converter which converts objects of type {@link Convertible} to and from the format.
     * 
     * @return the converter which converts objects of type {@link Convertible} to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getConvertibleConverter();
    
    /**
     * Returns the converter which converts objects of type {@link ReadOnlyProperty} to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getPropertyConverter();
    
    
    /**
     * Returns the converter which converts objects of type {@link Collection} to and from the format.
     * 
     * @return the converter which converts objects of type {@link Collection} to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getCollectionConverter();
    
    /**
     * Returns the converter which converts objects of type {@link Object[]} to and from the format.
     * 
     * @return the converter which converts objects of type {@link Object[]} to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getArrayConverter();
    
    /**
     * Returns the converter which converts objects of type {@link Map} to and from the format.
     * 
     * @return the converter which converts objects of type {@link Map} to and from the format.
     */    
    @Pure
    protected abstract @Nonnull C getMapConverter();
    
    /**
     * Returns the converter which maps objects of one type to another using the {@link TypeMapper} and then converts it to and from the format.
     *
     * @param typeMapper the type mapper maps the field type to a convertible type and vice versa.
     *  
     * @return the converter which maps objects of one type to another using the {@link TypeMapper} and then converts it to and from the format.
     */
    @Pure
    protected abstract @Nonnull C getTypeConverter(@Nonnull TypeMapper<?, ?> typeMapper);
    
    /* -------------------------------------------------- Registered and Cached Converters -------------------------------------------------- */
    
    /**
     * Maps each types to its converter. 
     */
    private final @Nonnull @NonFrozen FreezableMap<Class<?>, C> converters = FreezableHashMap.get();
    
    /**
     * Registers a converter for a specific type.
     * 
     * @param type The type for which the specific converter should be registered.
     * @param converter The converter which should be used when an object of the specific type must be converted.
     */
    protected final void registerConverter(@Nonnull Class<?> type, @Nonnull C converter) {
        converters.put(type, converter);
    }
    
    /* -------------------------------------------------- Retrieve Converter -------------------------------------------------- */
    
    /**
     * Returns the type converter for a specific type.
     * 
     * @param type The type which is used to find the correct converter.
     *             
     * @return The type converter for the specific type.
     * 
     * @throws ConverterNotFoundException if no type converter was found for the specified type.
     */
    public final @Nonnull C getConverter(@Nonnull Class<?> type) throws ConverterNotFoundException {
        @Nullable C converter = converters.get(type);
        
        if (converter == null) {
            if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                converter = getBooleanConverter();
            } else if (type.equals(Byte.class) || type.equals(byte.class)) {
                converter = getInteger08Converter();
            } else if (type.equals(Short.class) || type.equals(short.class)) {
                converter = getInteger16Converter();
            } else if (type.equals(Integer.class) || type.equals(int.class)) {
                converter = getInteger32Converter();
            } else if (type.equals(Long.class) || type.equals(long.class)) {
                converter = getInteger64Converter();
            } else if (type.equals(BigInteger.class)) {
                converter = getIntegerConverter();
            } else if (type.equals(Character.class) || type.equals(char.class)) {
                converter = getCharacterConverter();
            } else if (type.equals(String.class)) {
                converter = getStringConverter();
            } else if (type.equals(Byte[].class) || type.equals(byte[].class)) {
                converter = getBinaryConverter();
            } else if (Convertible.class.isAssignableFrom(type)) {
                converter = getConvertibleConverter();
            } else if (ReadOnlyProperty.class.isAssignableFrom(type)) {
                converter = getPropertyConverter();
            } else if (Collection.class.isAssignableFrom(type)) {
                converter = getCollectionConverter();
            } else if (type.isArray()) {
                converter = getArrayConverter();
            } else if (Map.class.isAssignableFrom(type)) {
                converter = getMapConverter();
            } else if (TypeMapper.class.isAssignableFrom(type)) {
                try {
                    final @Nonnull TypeMapper<?, ?> typeMapperInstance = (TypeMapper<?, ?>) type.newInstance();
                    converter = getTypeConverter(typeMapperInstance);
                } catch (@Nonnull InstantiationException | IllegalAccessException exception) {
                    throw ConverterNotFoundException.get(type, exception);
                }
            } else {
                throw ConverterNotFoundException.get(type);
            }
            
            converters.put(type, converter);
        }
        return converter;
    }
    
    /* -------------------------------------------------- Field Converter -------------------------------------------------- */
    
    /**
     * Returns a converter for a specific field. The decision, which converter to return, is 
     * either made based on the annotation of the field or the type of the field.
     * 
     * @param field the field for which a converter is returned.
     *              
     * @return the type converter for the field.
     * 
     * @throws ConverterNotFoundException 
     */
    public final @Nonnull C getConverter(@Nonnull Field field) throws ConverterNotFoundException {
        final boolean isConvertToConvertibleTypeAnnotationPresent = field.isAnnotationPresent(ConvertToConvertibleType.class);
        if (isConvertToConvertibleTypeAnnotationPresent) {
            final @Nonnull ConvertToConvertibleType convertedWith = field.getAnnotation(ConvertToConvertibleType.class);
            return getConverter(convertedWith.value());
        } else {
            return getConverter(field.getType());
        }
    }
    
}
