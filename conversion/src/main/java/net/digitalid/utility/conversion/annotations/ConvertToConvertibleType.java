package net.digitalid.utility.conversion.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.conversion.converter.TypeMapper;

/**
 * Specifies a type mapper which converts a field type to a convertible type before converting it to a format.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConvertToConvertibleType {
    
    /**
     * Returns the type mapper used to map the type.
     * 
     * @return the type mapper used to map the type.
     */
    @Nonnull Class<? extends TypeMapper<?, ?>> value();
    
}
