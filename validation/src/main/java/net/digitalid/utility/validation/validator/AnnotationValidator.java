package net.digitalid.utility.validation.validator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;

import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.logging.processing.SourcePosition;
import net.digitalid.utility.validation.annotations.meta.MethodAnnotation;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.processing.ProcessingUtility;
import net.digitalid.utility.validation.processing.TypeImporter;

/**
 * An annotation validator {@link #checkUsage(javax.lang.model.element.Element, javax.lang.model.type.DeclaredType) checks the use}
 * of and {@link #generateContract(javax.lang.model.element.Element, javax.lang.model.element.AnnotationMirror) generates the contract}
 * for an annotation during annotation processing. This class provides a default implementation for both of these validation phases.
 * 
 * @see Validator
 * @see GeneratedContract
 */
@Stateless
public abstract class AnnotationValidator extends CodeGenerator {
    
    /* -------------------------------------------------- Processing -------------------------------------------------- */
    
    /**
     * Checks whether the given annotation is used correctly on the given element.
     * This method can be overridden if more than the target types are to be checked.
     */
    @Pure
    @Override
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
        final @Nonnull String annotationName = "@" + annotationMirror.getAnnotationType().asElement().getSimpleName();
        
        final @Nullable TargetTypes targetTypes = getClass().getAnnotation(TargetTypes.class);
        if (targetTypes != null) {
            boolean elementAssignableToTargetType = false;
            for (@Nonnull Class<?> targetType : targetTypes.value()) {
                if (ProcessingUtility.isAssignable(element, targetType)) { elementAssignableToTargetType = true; }
            }
            if (!elementAssignableToTargetType) {
                AnnotationLog.error("The element $ is not assignable to a target type of $.", SourcePosition.of(element, annotationMirror), element, annotationName);
            }
        }
        
        final @Nullable MethodAnnotation methodAnnotation = getClass().getAnnotation(MethodAnnotation.class);
        if (methodAnnotation != null) {
            if (element.getKind() != ElementKind.METHOD) {
                AnnotationLog.error("The method annotation $ may only be used on methods.", SourcePosition.of(element, annotationMirror), annotationName);
            } else if (!ProcessingUtility.isAssignable(((ExecutableElement) element).getReceiverType(), methodAnnotation.value())) {
                AnnotationLog.error("The method annotation $ cannot be used on $.", SourcePosition.of(element, annotationMirror), annotationName, element);
            }
        }
    }
    
    /**
     * Generates the contract for the given element which is annotated with the given annotation mirror.
     * The annotation mirror can be used to retrieve possible annotation values.
     * The type importer can be used to import referenced types.
     */
    @Pure
    public abstract @Nonnull GeneratedContract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter);
    
    /* -------------------------------------------------- Utility -------------------------------------------------- */
    
    /**
     * Returns the name of the variable whose value is to be validated.
     */
    @Pure
    public static @Nonnull String getName(@Nonnull Element element) {
        return element.getKind() == ElementKind.METHOD ? "result" : element.getSimpleName().toString();
    }
    
}
