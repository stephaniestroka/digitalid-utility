package net.digitalid.utility.conversion.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

/**
 * .
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenericTypes {
    
    /**
     * 
     * 
     * @return 
     */
    @Nonnull Class<?>[] value();
    
}
