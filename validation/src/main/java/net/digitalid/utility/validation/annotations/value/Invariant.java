package net.digitalid.utility.validation.annotations.value;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.immutable.ImmutableSet;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.string.JavaExpression;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This annotation indicates the condition that a value has to fulfill.
 * You can use this annotation if you don't want to write a dedicated annotation for some rare case.
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(Invariant.Validator.class)
public @interface Invariant {
    
    /* -------------------------------------------------- Values -------------------------------------------------- */
    
    /**
     * Returns the condition which the generated field or parameter has to fulfill.
     */
    @Nonnull @JavaExpression String condition();
    
    /**
     * Returns the message which is thrown if the given condition is not fulfilled.
     */
    @Nonnull String message();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends ValueAnnotationValidator {
        
        private static final @Nonnull ImmutableSet<@Nonnull Class<?>> targetTypes = ImmutableSet.<Class<?>>with();
        
        @Pure
        @Override
        public @Nonnull ImmutableSet<@Nonnull Class<?>> getTargetTypes() {
            return targetTypes;
        }
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {}
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
            final @Nullable AnnotationValue condition = ProcessingUtility.getAnnotationValue(annotationMirror, "condition");
            final @Nullable AnnotationValue message = ProcessingUtility.getAnnotationValue(annotationMirror, "message");
            return Contract.with(String.valueOf(condition != null ? condition.getValue() : null), String.valueOf(message != null ? message.getValue() : null), element);
        }
        
    }
    
}
