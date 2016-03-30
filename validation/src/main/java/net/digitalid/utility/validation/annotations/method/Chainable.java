package net.digitalid.utility.validation.annotations.method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.immutable.collections.ImmutableSet;
import net.digitalid.utility.tuples.annotations.Pure;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.processing.TypeImporter;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This annotation indicates that a method returns the recipient of the call (the 'this' object).
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(Chainable.Validator.class)
public @interface Chainable {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends ValueAnnotationValidator {
        
        private static final @Nonnull ImmutableSet<Class<?>> targetTypes = ImmutableSet.<Class<?>>with(Object.class);
        
        @Pure
        @Override
        public @Nonnull ImmutableSet<Class<?>> getTargetTypes() {
            return targetTypes;
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return Contract.with("# == this", "The # has to be this but was $.", element);
        }
        
    }
    
}
