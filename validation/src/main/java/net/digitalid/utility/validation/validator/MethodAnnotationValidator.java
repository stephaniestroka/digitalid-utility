package net.digitalid.utility.validation.validator;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.meta.MethodValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.processing.ErrorLogger;

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
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
        if (element.getKind() != ElementKind.METHOD && element.getKind() != ElementKind.CONSTRUCTOR) {
            errorLogger.log("The method annotation $ may only be used on methods and constructors.", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt());
        } else if (!ProcessingUtility.isRawlyAssignable(element.getEnclosingElement(), getReceiverType())) { // ((ExecutableElement) element).getReceiverType() is only possible in Java 1.8.
            errorLogger.log("The method annotation $ can only be used in $.", SourcePosition.of(element, annotationMirror), getAnnotationNameWithLeadingAt(), getReceiverType().getCanonicalName());
        }
    }
    
}
