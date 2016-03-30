package net.digitalid.utility.freezable.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.ReadOnly;
import net.digitalid.utility.immutable.collections.ImmutableSet;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.tuples.annotations.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.processing.TypeImporter;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This annotation indicates that a freezable object is {@link Freezable#isFrozen() frozen}.
 * 
 * @see NonFrozen
 * @see Freezable
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(Frozen.Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Frozen {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends ValueAnnotationValidator {
        
        private static final @Nonnull ImmutableSet<Class<?>> targetTypes = ImmutableSet.<Class<?>>with(ReadOnly.class);
        
        @Pure
        @Override
        public @Nonnull ImmutableSet<Class<?>> getTargetTypes() {
            return targetTypes;
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return Contract.with("#.isFrozen()", "The # has to be frozen.", element);
        }
        
    }
    
}
