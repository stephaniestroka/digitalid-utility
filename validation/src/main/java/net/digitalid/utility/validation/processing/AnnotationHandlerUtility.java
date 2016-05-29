package net.digitalid.utility.validation.processing;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.state.Modifiable;
import net.digitalid.utility.processing.logging.ErrorLogger;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.meta.MethodValidator;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Utility;
import net.digitalid.utility.validation.validator.AnnotationHandler;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;
import net.digitalid.utility.validation.validator.TypeAnnotationValidator;
import net.digitalid.utility.validation.validator.ValueAnnotationValidator;

/**
 * This class provides useful methods to retrieve the annotation handlers from an element.
 */
@Utility
public abstract class AnnotationHandlerUtility {
    
    private static final @Nonnull Map<@Nonnull String, @Nonnull AnnotationHandler> cachedAnnotationHandlers = new HashMap<>();
    
    /**
     * Returns the annotation handlers of the given type which are found with the given meta-annotation type on the annotations of the given element.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static @Capturable <H extends AnnotationHandler> @Modifiable @Nonnull Map<@Nonnull AnnotationMirror, @Nonnull H> getAnnotationHandlers(@Nonnull Element element, @Nonnull Class<? extends Annotation> metaAnnotationType, @Nonnull Class<H> annotationHandlerType) {
        final @Nonnull Map<@Nonnull AnnotationMirror, @Nonnull H> result = new LinkedHashMap<>();
        for (@Nonnull AnnotationMirror annotationMirror : ProcessingUtility.getAnnotationMirrors(element)) {
            final @Nullable H annotationHandler;
            final @Nonnull String cacheKey = ProcessingUtility.getQualifiedName(annotationMirror) + "$" + metaAnnotationType.getCanonicalName();
            if (cachedAnnotationHandlers.containsKey(cacheKey)) {
                final @Nullable AnnotationHandler cachedAnnotationHandler = cachedAnnotationHandlers.get(cacheKey);
                annotationHandler = annotationHandlerType.isInstance(cachedAnnotationHandler) ? (H) cachedAnnotationHandler : null;
            } else {
                final @Nonnull TypeElement annotationElement = (TypeElement) annotationMirror.getAnnotationType().asElement();
                final @Nullable AnnotationValue metaAnnotationValue = ProcessingUtility.getAnnotationValue(annotationElement, metaAnnotationType);
                annotationHandler = ProcessingUtility.getInstance(metaAnnotationValue, annotationHandlerType);
                cachedAnnotationHandlers.put(cacheKey, annotationHandler);
            }
            if (annotationHandler != null) {
                ProcessingLog.debugging("Found the annotation handler $ for", SourcePosition.of(element, annotationMirror), annotationHandler.getClass().getCanonicalName());
                annotationHandler.checkUsage(element, annotationMirror, ErrorLogger.INSTANCE);
                result.put(annotationMirror, annotationHandler);
            }
        }
        return result;
    }
    
    /**
     * Returns the method validators mapped from their corresponding annotation mirror with which the given element is annotated.
     */
    @Pure
    public static @Capturable @Modifiable @Nonnull Map<@Nonnull AnnotationMirror, @Nonnull MethodAnnotationValidator> getMethodValidators(@Nonnull Element element) {
        return getAnnotationHandlers(element, MethodValidator.class, MethodAnnotationValidator.class);
    }
    
    /**
     * Returns the value validators mapped from their corresponding annotation mirror with which the given element is annotated.
     */
    @Pure
    public static @Capturable @Modifiable @Nonnull Map<@Nonnull AnnotationMirror, @Nonnull ValueAnnotationValidator> getValueValidators(@Nonnull Element element) {
        return getAnnotationHandlers(element, ValueValidator.class, ValueAnnotationValidator.class);
    }
    
    /**
     * Returns the type validators mapped from their corresponding annotation mirror with which the given type element is annotated.
     */
    @Pure
    public static @Capturable @Modifiable @Nonnull Map<@Nonnull AnnotationMirror, @Nonnull TypeAnnotationValidator> getTypeValidators(@Nonnull TypeElement element) {
        return getAnnotationHandlers(element, TypeValidator.class, TypeAnnotationValidator.class);
    }
    
}
