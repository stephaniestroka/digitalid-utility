package net.digitalid.utility.validation.processing;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.logging.Log;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.meta.MethodValidator;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Utility;
import net.digitalid.utility.validation.validator.AnnotationHandler;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;
import net.digitalid.utility.validation.validator.TypeAnnotationValidator;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 *
 */
@Utility
public class ValidatorProcessingUtility {
    
    /* -------------------------------------------------- Annotation Handlers -------------------------------------------------- */
    
    private static final @NonNullableElements @Nonnull Map<String, AnnotationHandler> cachedAnnotationHandlers = new HashMap<>();
    
    /**
     * Returns the annotation handlers of the given type which are found with the given meta-annotation type on the annotations of the given element.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static @Nonnull @NonNullableElements <G extends AnnotationHandler> Map<AnnotationMirror, G> getAnnotationHandlers(@Nonnull Element element, @Nonnull Class<? extends Annotation> metaAnnotationType, @Nonnull Class<G> annotationHandlerType) {
        final @Nonnull @NonNullableElements Map<AnnotationMirror, G> result = new LinkedHashMap<>();
        for (@Nonnull AnnotationMirror annotationMirror : StaticProcessingEnvironment.getElementUtils().getAllAnnotationMirrors(element)) {
            final @Nonnull String qualifiedAnnotationName = ProcessingUtility.getQualifiedName(annotationMirror);
            final @Nullable AnnotationHandler cachedAnnotationHandler = cachedAnnotationHandlers.get(qualifiedAnnotationName);
            if (cachedAnnotationHandler != null) {
                if (annotationHandlerType.isInstance(cachedAnnotationHandler)) {
                    cachedAnnotationHandler.checkUsage(element, annotationMirror);
                    result.put(annotationMirror, (G) cachedAnnotationHandler);
                }
            } else {
                final @Nonnull TypeElement annotationElement = (TypeElement) annotationMirror.getAnnotationType().asElement();
                final @Nonnull String annotationName = "@" + annotationElement.getSimpleName();
                final @Nullable AnnotationValue metaAnnotationValue = ProcessingUtility.getAnnotationValue(annotationElement, metaAnnotationType);
                if (metaAnnotationValue != null) {
                    final @Nonnull DeclaredType annotationHandlerImplementationType = (DeclaredType) metaAnnotationValue.getValue();
                    ProcessingLog.verbose("The declared annotation handler type is $.", annotationHandlerImplementationType);
                    final @Nonnull TypeElement annotationHandlerImplementationElement = (TypeElement) annotationHandlerImplementationType.asElement();
                    final @Nonnull String annotationHandlerImplementationBinaryName = StaticProcessingEnvironment.getElementUtils().getBinaryName(annotationHandlerImplementationElement).toString();
                    try {
                        ProcessingLog.debugging("Trying to retrieve class for name $", annotationHandlerImplementationBinaryName);
                        final @Nonnull Class<?> annotationHandlerImplementationClass = Class.forName(annotationHandlerImplementationBinaryName);
                        ProcessingLog.debugging("Annotation handler class: $", annotationHandlerImplementationClass);
                        if (annotationHandlerType.isAssignableFrom(annotationHandlerImplementationClass)) {
                            final @Nonnull G annotationHandler = (G) annotationHandlerImplementationClass.newInstance();
                            cachedAnnotationHandlers.put(qualifiedAnnotationName, annotationHandler);
                            annotationHandler.checkUsage(element, annotationMirror);
                            result.put(annotationMirror, annotationHandler);
                            ProcessingLog.debugging("Found the annotation handler $ for", SourcePosition.of(element), annotationName);
                        } else {
                            ProcessingLog.error("The annotation handler $ is not assignable to $:", SourcePosition.of(element), annotationName, annotationHandlerImplementationClass.getCanonicalName());
                        }
                    } catch (@Nonnull ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
                        ProcessingLog.error("Could not instantiate the annotation handler $ for", SourcePosition.of(element), annotationHandlerImplementationBinaryName);
                        Log.error("Problem:", exception);
                    }
                }
            }
        }
        return Collections.unmodifiableMap(result);
    }
    
    /**
     * Returns the method validators mapped from their corresponding annotation mirror with which the given element is annotated.
     */
    @Pure
    public static @Nonnull @NonNullableElements Map<AnnotationMirror, MethodAnnotationValidator> getMethodValidators(@Nonnull Element element) {
        return getAnnotationHandlers(element, MethodValidator.class, MethodAnnotationValidator.class);
    }
    
    /**
     * Returns the value validators mapped from their corresponding annotation mirror with which the given element is annotated.
     */
    @Pure
    public static @Nonnull @NonNullableElements Map<AnnotationMirror, ValueAnnotationValidator> getValueValidators(@Nonnull Element element) {
        return getAnnotationHandlers(element, ValueValidator.class, ValueAnnotationValidator.class);
    }
    
    /**
     * Returns the type validators mapped from their corresponding annotation mirror with which the given type element is annotated.
     */
    @Pure
    public static @Nonnull @NonNullableElements Map<AnnotationMirror, TypeAnnotationValidator> getTypeValidators(@Nonnull TypeElement element) {
        return getAnnotationHandlers(element, TypeValidator.class, TypeAnnotationValidator.class);
    }
    
}
