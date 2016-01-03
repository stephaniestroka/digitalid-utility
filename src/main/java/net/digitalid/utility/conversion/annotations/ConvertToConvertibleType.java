package net.digitalid.utility.conversion.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.digitalid.utility.conversion.TypeToTypeMapper;

/**
 * Specifies a typeToTypeMapper which converts a fields type to another convertible type before converting it to a format.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConvertToConvertibleType {
    Class<? extends TypeToTypeMapper> typeToTypeMapper();
}
