package net.digitalid.utility.validation.validators;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.processing.logging.ErrorLogger;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.generation.Default;
import net.digitalid.utility.validation.annotations.generation.Derive;
import net.digitalid.utility.validation.annotations.generation.Normalize;
import net.digitalid.utility.validation.annotations.generation.Recover;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class checks the usage of code generation annotations.
 * 
 * @see Default
 * @see Derive
 * @see Normalize
 */
@Stateless
public class GenerationValidator implements ValueAnnotationValidator {
    
    /* -------------------------------------------------- Usage Check -------------------------------------------------- */
    
    @Pure
    @Override
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
        if (element.getKind() == ElementKind.PARAMETER) {
            final @Nonnull Element enclosingElement = element.getEnclosingElement();
            if (!(enclosingElement.getKind() == ElementKind.CONSTRUCTOR && ProcessingUtility.getConstructors(ProcessingUtility.getSurroundingType(element)).isSingle() || ProcessingUtility.hasAnnotation(enclosingElement, Recover.class))) {
                errorLogger.log("The generation annotation may only be used on parameters of a unique constructor or an executable annotated with '@Recover'.", SourcePosition.of(element, annotationMirror));
            }
        } else if (element.getKind() == ElementKind.METHOD) {
            final @Nonnull ExecutableElement method = (ExecutableElement) element;
            if (!method.getModifiers().contains(Modifier.ABSTRACT)/* || !ProcessingUtility.isGetter(method)*/) { // TODO: @Default also has to be allowed on generated properties.
                errorLogger.log("The generation annotation may only be used on abstract getters.", SourcePosition.of(element, annotationMirror));
            }
        } else {
            errorLogger.log("The generation annotation may only be used on parameters and methods.", SourcePosition.of(element, annotationMirror));
        }
    }
    
}
