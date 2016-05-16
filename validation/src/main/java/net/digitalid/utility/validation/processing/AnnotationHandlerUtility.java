package net.digitalid.utility.validation.processing;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.state.Modifiable;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.logging.Log;
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

import com.sun.tools.javac.code.Type;

import static net.digitalid.utility.processing.utility.StaticProcessingEnvironment.*;

/**
 * This class provides useful methods to retrieve the annotation handlers from an element.
 */
@Utility
public class AnnotationHandlerUtility {
    
    /* -------------------------------------------------- Annotation Handlers -------------------------------------------------- */
    
    private static final @Nonnull Map<@Nonnull String, @Nonnull AnnotationHandler> cachedAnnotationHandlers = new HashMap<>();
    
    /**
     * Returns the cache key for the given annotation mirror and meta-annotation type.
     */
    @Pure
    private static @Nonnull String getAnnotationHandlerCacheKey(@Nonnull AnnotationMirror annotationMirror, @Nonnull Class<? extends Annotation> metaAnnotationType) {
        return ProcessingUtility.getQualifiedName(annotationMirror) + "$" + metaAnnotationType.getCanonicalName();
    }
    
    /**
     * Returns an instance of an annotation handler for the given annotation mirror with the given meta-annotation type or null if no annotation handler for the given annotation mirror and meta-annotation type was found.
     */
    @Pure
    private static @Nullable <H extends AnnotationHandler> AnnotationHandler getCachedAnnotationHandler(@Nonnull AnnotationMirror annotationMirror, @Nonnull Class<? extends Annotation> metaAnnotationType, @Nonnull Class<H> annotationHandlerType) {
        final @Nonnull String cacheKey = getAnnotationHandlerCacheKey(annotationMirror, metaAnnotationType);
        if (cachedAnnotationHandlers.containsKey(cacheKey)) {
            final @Nullable AnnotationHandler cachedAnnotationHandler = cachedAnnotationHandlers.get(cacheKey);
            return annotationHandlerType.isInstance(cachedAnnotationHandler) ? cachedAnnotationHandler : null;
        } else {
            return null;
        }
    }
    
    /**
     * Returns the binary name of the annotation handler implementation.
     */
    @Pure
    private static @Nullable String getAnnotationHandlerImplementationBinaryName(@Nonnull AnnotationMirror annotationMirror, @Nonnull Class<? extends Annotation> metaAnnotationType) {
        final @Nonnull TypeElement annotationElement = (TypeElement) annotationMirror.getAnnotationType().asElement();
        final @Nullable AnnotationValue metaAnnotationValue = ProcessingUtility.getAnnotationValue(annotationElement, metaAnnotationType);
        if (metaAnnotationValue != null) {
            final @Nonnull DeclaredType annotationHandlerImplementationType = (DeclaredType) metaAnnotationValue.getValue();
            ProcessingLog.verbose("The declared annotation handler type is $ for", SourcePosition.of(annotationElement), annotationHandlerImplementationType);
            final @Nonnull TypeElement annotationHandlerImplementationElement = (TypeElement) annotationHandlerImplementationType.asElement();
            return getElementUtils().getBinaryName(annotationHandlerImplementationElement).toString();
        } else {
            ProcessingLog.debugging("No value declared for meta-annotation $ on", SourcePosition.of(annotationElement), "@" + metaAnnotationType.getSimpleName());
            return null;
        }
    }
    
    /**
     * Returns an instance of the annotation handler class for a given annotation handler implementation binary name.
     */
    @Pure
    @SuppressWarnings("unchecked")
    private static <H extends AnnotationHandler> @Nonnull H getAnnotationHandlerImplementation(@Nonnull String annotationHandlerImplementationBinaryName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ProcessingLog.debugging("Trying to retrieve the class for name $.", annotationHandlerImplementationBinaryName);
        final @Nonnull Class<?> annotationHandlerImplementationClass = Class.forName(annotationHandlerImplementationBinaryName);
        ProcessingLog.debugging("Annotation handler class: $.", annotationHandlerImplementationClass);
        return (H) annotationHandlerImplementationClass.newInstance();
    }
    
    /**
     * Returns the simple name of the given annotation.
     */
    @Pure
    private static @Nonnull String getAnnotationName(@Nonnull AnnotationMirror annotationMirror) {
        return "@" + annotationMirror.getAnnotationType().asElement().getSimpleName();
    }
    
    /**
     * Return all annotation mirrors for a given element.
     */
    private static @Nonnull FiniteIterable<AnnotationMirror> getAnnotationMirrors(@Nonnull Element element) {
        @Nonnull List<AnnotationMirror> typeUseAnnotations = new LinkedList<>();
        if (element instanceof ExecutableElement) {
            TypeMirror typeMirror = ((ExecutableElement) element).getReturnType();
            if (typeMirror instanceof Type.AnnotatedType) {
                Type.AnnotatedType annotatedType = (Type.AnnotatedType) ((ExecutableElement) element).getReturnType();
                typeUseAnnotations.addAll(annotatedType.getAnnotationMirrors());
            } else if (typeMirror instanceof Type.ArrayType) {
                typeUseAnnotations.addAll(((Type.ArrayType) typeMirror).getComponentType().getAnnotationMirrors());
            }
        } else {
            TypeMirror typeMirror = element.asType();
            if (typeMirror instanceof Type.ArrayType) {
                typeUseAnnotations.addAll(((Type.ArrayType) typeMirror).getComponentType().getAnnotationMirrors());
            }
            typeUseAnnotations.addAll(element.asType().getAnnotationMirrors());
        }
        return FiniteIterable.of(typeUseAnnotations).combine(FiniteIterable.of(getElementUtils().getAllAnnotationMirrors(element)));
    }
    
    /**
     * Returns the annotation handlers of the given type which are found with the given meta-annotation type on the annotations of the given element.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static @Capturable <H extends AnnotationHandler> @Modifiable @Nonnull Map<@Nonnull AnnotationMirror, @Nonnull H> getAnnotationHandlers(@Nonnull Element element, @Nonnull Class<? extends Annotation> metaAnnotationType, @Nonnull Class<H> annotationHandlerType) {
        final @Nonnull Map<@Nonnull AnnotationMirror, @Nonnull H> result = new LinkedHashMap<>();
        for (@Nonnull AnnotationMirror annotationMirror : getAnnotationMirrors(element)) {
            final @Nullable AnnotationHandler cachedAnnotationHandler = getCachedAnnotationHandler(annotationMirror, metaAnnotationType, annotationHandlerType);
            if (cachedAnnotationHandler != null) {
                cachedAnnotationHandler.checkUsage(element, annotationMirror);
                result.put(annotationMirror, (H) cachedAnnotationHandler);
            } else {
                final @Nullable String annotationHandlerImplementationBinaryName = getAnnotationHandlerImplementationBinaryName(annotationMirror, metaAnnotationType);
                if (annotationHandlerImplementationBinaryName != null) {
                    try {
                        final @Nonnull H annotationHandler = getAnnotationHandlerImplementation(annotationHandlerImplementationBinaryName);
                        if (annotationHandlerType.isAssignableFrom(annotationHandler.getClass())) {
                            cachedAnnotationHandlers.put(getAnnotationHandlerCacheKey(annotationMirror, metaAnnotationType), annotationHandler);
                            ProcessingLog.debugging("Found the annotation handler $ for", SourcePosition.of(element), getAnnotationName(annotationMirror));
                            annotationHandler.checkUsage(element, annotationMirror);
                            result.put(annotationMirror, annotationHandler);
                        } else {
                            ProcessingLog.error("The annotation handler $ is not assignable to $:", SourcePosition.of(element), getAnnotationName(annotationMirror), annotationHandler.getClass().getCanonicalName());
                        }
                    } catch (@Nonnull ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
                        ProcessingLog.error("Could not instantiate the annotation handler $ for", SourcePosition.of(element), annotationHandlerImplementationBinaryName);
                        Log.error("Problem:", exception);
                    }
                }
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
