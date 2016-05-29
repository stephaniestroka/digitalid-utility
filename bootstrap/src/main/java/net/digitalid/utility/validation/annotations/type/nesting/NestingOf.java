package net.digitalid.utility.validation.annotations.type.nesting;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.NestingKind;

import net.digitalid.utility.validation.annotations.elements.NonNullableElements;

/**
 * This annotation indicates that a class or type element has one of the given nesting kinds.
 * 
 * @see NestingKind
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NestingOf {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the nesting kinds one of which the annotated type or element has to have.
     */
    @Nonnull @NonNullableElements NestingKind[] value();
    
}
