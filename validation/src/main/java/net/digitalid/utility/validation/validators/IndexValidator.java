package net.digitalid.utility.validation.validators;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.immutable.ImmutableSet;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class declares the target types for index annotations.
 * 
 * @see net.digitalid.utility.validation.annotations.index
 */
@Stateless
public abstract class IndexValidator extends ValueAnnotationValidator {
    
    /* -------------------------------------------------- Target Types -------------------------------------------------- */
    
    private static final @Nonnull ImmutableSet<@Nonnull Class<?>> targetTypes = ImmutableSet.<Class<?>>with(int.class);
    
    @Pure
    @Override
    public @Nonnull ImmutableSet<@Nonnull Class<?>> getTargetTypes() {
        return targetTypes;
    }
    
    /* -------------------------------------------------- Usage Check -------------------------------------------------- */
    
    @Pure
    @Override
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
        super.checkUsage(element, annotationMirror);
        
        if (!ProcessingUtility.hasMethod(ProcessingUtility.getSurroundingType(element), "size", int.class)) {
            ProcessingLog.error("The annotation $ may only be used in types with an 'int size()' method:", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt());
        }
    }
    
}
