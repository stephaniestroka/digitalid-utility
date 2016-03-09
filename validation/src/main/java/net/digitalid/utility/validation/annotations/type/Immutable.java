package net.digitalid.utility.validation.annotations.type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import net.digitalid.utility.logging.processing.ProcessingLog;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.logging.processing.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.processing.ProcessingUtility;
import net.digitalid.utility.validation.validator.TypeAnnotationValidator;

/**
 * This annotation indicates that the objects of the annotated class are immutable.
 * <p>
 * An object is considered immutable, if its representation (usually the data that is included in its block) is fixed.
 * Other objects that are not fully part of its representation but can nonetheless be reached through its fields may still be mutable.
 * <p>
 * It should always be safe to share immutable objects between various instances and threads.
 * However, it is in general not guaranteed that the hash of immutable objects stays the same.
 * In other words, an immutable object is only conceptually immutable but its values may change.
 * (This is the case with references to persons, which remain constant but can still be merged.)
 * 
 * @see Mutable
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TypeValidator(Immutable.Validator.class)
public @interface Immutable {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of the surrounding annotation.
     */
    @Stateless
    public static class Validator extends TypeAnnotationValidator {
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
            for (@Nonnull ExecutableElement method : ElementFilter.methodsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers((TypeElement) element))) {
                if (ProcessingUtility.isDeclaredInDigitalIDLibrary(method) && !method.getModifiers().contains(Modifier.STATIC) && !ProcessingUtility.hasAnnotation(element, Pure.class)) {
                    ProcessingLog.error("The immutable type $ may only contain non-static methods that are pure.", SourcePosition.of(method), element);
                }
            }
        }
        
    }
    
}
