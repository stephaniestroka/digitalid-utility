package net.digitalid.utility.conversion.model.utility;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A field type signature encapsulates the type mirror and the annotation mirrors of a field.
 */
@Immutable
public class FieldTypeSignature {
    
    /* -------------------------------------------------- Type Mirror -------------------------------------------------- */
    
    /**
     * The type mirror of the field.
     */
    private @Nonnull TypeMirror typeMirror;
    
    /**
     * Returns the type mirror of the field.
     */
    @Pure
    public @Nonnull TypeMirror getTypeMirror() {
        return typeMirror;
    }
    
    @Pure
    public boolean isAssignable(@Nonnull Class<?> clazz) {
        return ProcessingUtility.isRawlyAssignable(typeMirror, clazz);
    }
    
    /* -------------------------------------------------- Annotation Mirrors -------------------------------------------------- */
    
    /**
     * The annotation mirrors of the field.
     */
    private @Nonnull FiniteIterable<@Nonnull AnnotationMirror> annotationMirrors;
    
    /**
     * Returns the annotation mirrors of the field.
     */
    @Pure
    public @Nonnull FiniteIterable<@Nonnull AnnotationMirror> getAnnotationMirrors() {
        return annotationMirrors;
    }
    
    /**
     * Returns true if the field is annotated with @MaxSize and the value is lower or equal than the given max size parameter value.
     */
    @Pure
    public boolean hasMaxSize(int maxSize) {
        boolean hasMaxSize = true;
        final @Nullable AnnotationMirror first = annotationMirrors.findFirst(annotationMirror -> ProcessingUtility.getQualifiedName(annotationMirror).equals(MaxSize.class.getName()));
        hasMaxSize = hasMaxSize && first != null;
        if (first != null) {
            final @Nullable AnnotationValue annotationValue = ProcessingUtility.getAnnotationValue(first);
            Require.that(annotationValue != null).orThrow("MaxSize requires an annotation value.");
            hasMaxSize = hasMaxSize && ((int) annotationValue.getValue()) <= maxSize;
        }
        return hasMaxSize;
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new field type signature from a given type mirror and annotation mirrors.
     */
    FieldTypeSignature(@Nonnull TypeMirror typeMirror, @Nonnull FiniteIterable<@Nonnull AnnotationMirror> annotationMirrors) {
        this.typeMirror = typeMirror;
        this.annotationMirrors = annotationMirrors;
    }
    
    /**
     * Returns a field type signature for the given type mirror and annotation mirrors.
     */
    @Pure
    public static @Nonnull FieldTypeSignature of(@Nonnull TypeMirror typeMirror, @Nonnull FiniteIterable<@Nonnull AnnotationMirror> annotationMirrors) {
        return new FieldTypeSignature(typeMirror, annotationMirrors);
    }
    
}
