package net.digitalid.utility.validation.validator;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.validation.annotations.meta.MethodValidator;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.processing.utility.ProcessingUtility;

/**
 * A method annotation validator validates the state in which the annotated method is called.
 * 
 * @see MethodValidator
 */
@Stateless
public abstract class MethodAnnotationValidator extends AnnotationHandler implements ContractGenerator {
    
    /* -------------------------------------------------- Receiver Type -------------------------------------------------- */
    
    /**
     * Returns the type to whose methods the surrounding annotation can be applied.
     */
    @Pure
    public abstract @Nonnull Class<?> getReceiverType();
    
    /* -------------------------------------------------- Usage Check -------------------------------------------------- */
    
    @Pure
    @Override
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
        if (element.getKind() != ElementKind.METHOD && element.getKind() != ElementKind.CONSTRUCTOR) {
            ProcessingLog.error("The method annotation $ may only be used on methods and constructors.", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt());
        } else if (!ProcessingUtility.isAssignable(element.getEnclosingElement(), getReceiverType())) { // ((ExecutableElement) element).getReceiverType() is only possible in Java 1.8.
            ProcessingLog.error("The method annotation $ cannot be used on $.", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt(), element);
        }
    }
    
}
