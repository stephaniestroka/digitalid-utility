package net.digitalid.utility.generator.annotations.generators;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.processing.logging.ErrorLogger;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validator.TypeAnnotationValidator;

/**
 * This class implements the usage check for the generate annotations.
 * 
 * @see GenerateBuilder
 * @see GenerateSubclass
 * @see GenerateConverter
 */
@Stateless
public class GenerateAnnotationValidator implements TypeAnnotationValidator {
    
    @Pure
    @Override
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
        if (((TypeElement) element).getNestingKind() != NestingKind.TOP_LEVEL) {
            errorLogger.log("Classes can only be generated for top-level types.", SourcePosition.of(element, annotationMirror));
        }
    }
    
}
