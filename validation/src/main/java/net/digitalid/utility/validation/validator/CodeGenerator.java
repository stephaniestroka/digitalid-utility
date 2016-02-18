package net.digitalid.utility.validation.validator;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This interface marks classes that generate code during annotation processing.
 * 
 * @see AnnotationValidator
 */
public abstract class CodeGenerator {
    
    /**
     * Checks whether the given annotation is correctly used on the given element.
     */
    @Pure
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
        AnnotationLog.verbose("Unimplemented usage check for '" + getClass().getName() + "'.");
    }
    
}
