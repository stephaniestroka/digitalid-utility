package net.digitalid.utility.validation.annotations.generation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines when the field should be assigned to it's value. This is particularly interesting for {@link Derive @Derive} annotations, because they might depend on other variables. @OrderOfAssignment gives you control over which assignment comes first in the generated code..
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface OrderOfAssignment {
    
    public int value();
    
    
}
