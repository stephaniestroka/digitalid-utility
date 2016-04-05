package net.digitalid.utility.processing.logging;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class models a position in the source code.
 */
@Immutable
public class SourcePosition {
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    private final @Nonnull Element element;
    
    /**
     * Returns the element of this position.
     */
    @Pure
    public @Nonnull Element getElement() {
        return element;
    }
    
    /* -------------------------------------------------- Annotation Mirror -------------------------------------------------- */
    
    private final @Nullable AnnotationMirror annotationMirror;
    
    /**
     * Returns the annotation mirror of this position.
     */
    @Pure
    public @Nullable AnnotationMirror getAnnotationMirror() {
        return annotationMirror;
    }
    
    /* -------------------------------------------------- Annotation Value -------------------------------------------------- */
    
    private final @Nullable AnnotationValue annotationValue;
    
    /**
     * Returns the annotation value of this position.
     */
    @Pure
    public @Nullable AnnotationValue getAnnotationValue() {
        return annotationValue;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected SourcePosition(@Nonnull Element element, @Nullable AnnotationMirror annotationMirror, @Nullable AnnotationValue annotationValue) {
        Require.that(element != null).orThrow("The element may not be null.");
        Require.that(annotationMirror != null || annotationValue == null).orThrow("If the annotation mirror is null, the annotation value has also to be null.");
        
        this.element = element;
        this.annotationMirror = annotationMirror;
        this.annotationValue = annotationValue;
    }
    
    /**
     * Returns a source position with the given arguments.
     */
    @Pure
    public static @Nonnull SourcePosition of(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull AnnotationValue annotationValue) {
        Require.that(element != null).orThrow("The element may not be null.");
        Require.that(annotationMirror != null).orThrow("The annotation mirror is not null.");
        Require.that(annotationValue != null).orThrow("The annotation Value is not null.");
        
        return new SourcePosition(element, annotationMirror, annotationValue);
    }
    
    /**
     * Returns a source position with the given arguments.
     */
    @Pure
    public static @Nonnull SourcePosition of(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
        Require.that(element != null).orThrow("The element may not be null.");
        Require.that(annotationMirror != null).orThrow("The annotation mirror may not be null.");
        
        return new SourcePosition(element, annotationMirror, null);
    }
    
    /**
     * Returns a source position with the given element.
     */
    @Pure
    public static @Nonnull SourcePosition of(@Nonnull Element element) {
        Require.that(element != null).orThrow("The element may not be null.");
        
        return new SourcePosition(element, null, null);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        final @Nonnull StringBuilder string = new StringBuilder("'");
        final @Nullable Element enclosingElement = element.getEnclosingElement();
        if (enclosingElement != null && (enclosingElement.getKind().isClass() || enclosingElement.getKind().isInterface())) {
            string.append(enclosingElement.getSimpleName()).append("#");
        }
        if (element.getKind() == ElementKind.PACKAGE || element.getKind().isClass() || element.getKind().isInterface()) {
            if (element.getKind() == ElementKind.ANNOTATION_TYPE) { string.append("@"); }
            string.append(element.getSimpleName());
        } else {
            string.append(element);
        }
        string.append("'");
        
        if (annotationMirror != null) {
            string.append(" for '@").append(annotationMirror.getAnnotationType().asElement().getSimpleName()).append("'");
        }
        
        if (annotationValue != null) {
            string.append(" at '").append(annotationValue.toString()).append("'");
        }
        
        return string.toString();
    }
    
}
