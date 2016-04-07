package net.digitalid.utility.validation.annotations.type.nesting;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.immutable.ImmutableSet;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;
import net.digitalid.utility.validation.validators.NestingKindValidator;

/**
 * This annotation indicates that a class or type element has one of the given nesting kinds.
 * 
 * @see NestingKind
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(NestingOf.Validator.class)
public @interface NestingOf {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the nesting kinds one of which the annotated type or element has to have.
     */
    @Nonnull @NonNullableElements NestingKind[] value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends ValueAnnotationValidator {
        
        private static final @Nonnull ImmutableSet<@Nonnull Class<?>> targetTypes = ImmutableSet.with(Class.class, TypeElement.class);
        
        @Pure
        @Override
        public @Nonnull ImmutableSet<@Nonnull Class<?>> getTargetTypes() {
            return targetTypes;
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
            final @Nonnull StringBuilder condition = new StringBuilder("# == null");
            for (@Nonnull NestingKind kind : element.getAnnotation(NestingOf.class).value()) {
                condition.append(" || ").append(NestingKindValidator.getCondition(element, kind, typeImporter));
            }
            return Contract.with(condition.toString(), "The # has to be null or have one of the specified nesting kinds but was $.", element);
        }
        
    }
    
}
