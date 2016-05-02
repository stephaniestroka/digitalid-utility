package net.digitalid.utility.freezable.annotations;

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

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.validator.TypeAnnotationValidator;

/**
 * This annotation indicates that the objects of the annotated type can be {@link FreezableInterface#freeze() frozen}
 * and thereby irrevocably transition from a {@link Mutable mutable} into an {@link Immutable immutable} state.
 * There has to be a {@link ReadOnly read-only} type for very freezable type that provides read-only access.
 * 
 * @see ReadOnly
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TypeValidator(Freezable.Validator.class)
public @interface Freezable {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the type which provides read-only access to the annotated type.
     */
    @Nonnull Class<?> value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of the surrounding annotation.
     */
    @Stateless
    public static class Validator extends TypeAnnotationValidator {
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
            if (!StaticProcessingEnvironment.getTypeUtils().isSubtype(element.asType(), StaticProcessingEnvironment.getElementUtils().getTypeElement(FreezableInterface.class.getCanonicalName()).asType())) {
                ProcessingLog.error("The freezable type $ has to implement the freezable interface.", SourcePosition.of(element), element);
            }
            for (@Nonnull ExecutableElement method : ElementFilter.methodsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers((TypeElement) element))) {
                if (ProcessingUtility.isDeclaredInDigitalIDLibrary(method)) {
                    if (!ProcessingUtility.hasAnnotation(element, Pure.class) && !ProcessingUtility.hasAnnotation(element, Impure.class)) {
                        ProcessingLog.error("Each method of the freezable type $ has to be either pure or impure.", SourcePosition.of(method), element);
                    }
                    if (!method.getModifiers().contains(Modifier.STATIC) && ProcessingUtility.hasAnnotation(element, Impure.class) && !ProcessingUtility.hasAnnotation(element, NonFrozenRecipient.class)) {
                        ProcessingLog.error("Each impure non-static method of the freezable type $ has to be also annotated with '@NonFrozenRecipient'.", SourcePosition.of(method), element);
                    }
                }
            }
        }
        
    }
    
}
