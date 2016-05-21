package net.digitalid.utility.validation.validators;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.processing.ErrorLogger;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class declares the target types for index annotations.
 * 
 * @see net.digitalid.utility.validation.annotations.index
 */
@Stateless
public abstract class IndexValidator implements ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull FiniteIterable<@Nonnull Class<?>> targetTypes = FiniteIterable.of(int.class);
    
    @Pure
    @Override
    public @Nonnull FiniteIterable<@Nonnull Class<?>> getTargetTypes() {
        return targetTypes;
    }
    
    /* -------------------------------------------------- Usage Check -------------------------------------------------- */
    
    @Pure
    @Override
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
        ValueAnnotationValidator.super.checkUsage(element, annotationMirror, errorLogger);
        
        if (!ProcessingUtility.hasMethod(ProcessingUtility.getSurroundingType(element), "size", int.class)) {
            errorLogger.log("The annotation $ may only be used in types with an 'int size()' method:", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt());
        }
    }
    
}
