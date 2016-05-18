package net.digitalid.utility.validation.annotations.type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;
import net.digitalid.utility.validation.processing.ErrorLogger;
import net.digitalid.utility.validation.validator.TypeAnnotationValidator;

/**
 * This annotation indicates that an interface is intended to be a <i>functional interface</i> as defined by the Java Language Specification.
 * It should only be used on interface declarations and one may not add more abstract methods later on as other code might depend on this.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TypeValidator(Functional.Validator.class)
public @interface Functional {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of the surrounding annotation.
     */
    @Stateless
    public static class Validator extends TypeAnnotationValidator {
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
            if (element.getKind() != ElementKind.INTERFACE) {
                errorLogger.log("Only an interface can be functional.", SourcePosition.of(element));
            }
            boolean found = false;
            for (@Nonnull ExecutableElement method : ProcessingUtility.getAllMethods((TypeElement) element)) {
                if (method.getModifiers().contains(Modifier.ABSTRACT)) {
                    if (found) {
                        errorLogger.log("The functional interface $ may have only one abstract method.", SourcePosition.of(method), element);
                    } else {
                        found = true;
                    }
                }
            }
            if (!found) {
                errorLogger.log("The functional interface $ must have an abstract method.", SourcePosition.of(element), element);
            }
        }
        
    }
    
}
