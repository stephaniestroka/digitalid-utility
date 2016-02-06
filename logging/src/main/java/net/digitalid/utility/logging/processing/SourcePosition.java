package net.digitalid.utility.logging.processing;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import net.digitalid.utility.contracts.Require;

/**
 * This class models a position in the source code.
 */
public class SourcePosition {
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    private final Element element;
    
    /**
     * Returns the non-nullable element of this position.
     */
    public Element getElement() {
        return element;
    }
    
    /* -------------------------------------------------- Annotation Mirror -------------------------------------------------- */
    
    private final AnnotationMirror annotationMirror;
    
    /**
     * Returns the nullable annotation mirror of this position.
     */
    public AnnotationMirror getAnnotationMirror() {
        return annotationMirror;
    }
    
    /* -------------------------------------------------- Annotation Value -------------------------------------------------- */
    
    private final AnnotationValue annotationValue;
    
    /**
     * Returns the nullable annotation value of this position.
     */
    public AnnotationValue getAnnotationValue() {
        return annotationValue;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected SourcePosition(Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
        Require.that(element != null).orThrow("The element may not be null.");
        Require.that(annotationMirror != null || annotationValue == null).orThrow("If the annotation mirror is null, the annotation value has also to be null.");
        
        this.element = element;
        this.annotationMirror = annotationMirror;
        this.annotationValue = annotationValue;
    }
    
    /**
     * Returns a source position with the given non-nullable arguments.
     * 
     * @require element != null : "The element may not be null.";
     * @require annotationMirror != null : "The annotation mirror may not be null.";
     * @require annotationValue != null : "The annotation Value may not be null.";
     */
    public static SourcePosition of(Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
        Require.that(element != null).orThrow("The element may not be null.");
        Require.that(annotationMirror != null).orThrow("The annotation mirror is not null.");
        Require.that(annotationValue != null).orThrow("The annotation Value is not null.");
        
        return new SourcePosition(element, annotationMirror, annotationValue);
    }
    
    /**
     * Returns a source position with the given non-nullable arguments.
     * 
     * @require element != null : "The element may not be null.";
     * @require annotationMirror != null : "The annotation mirror may not be null.";
     */
    public static SourcePosition of(Element element, AnnotationMirror annotationMirror) {
        Require.that(element != null).orThrow("The element may not be null.");
        Require.that(annotationMirror != null).orThrow("The annotation mirror may not be null.");
        
        return new SourcePosition(element, annotationMirror, null);
    }
    
    /**
     * Returns a source position with the given non-nullable element.
     * 
     * @require element != null : "The element may not be null.";
     */
    public static SourcePosition of(Element element) {
        Require.that(element != null).orThrow("The element may not be null.");
        
        return new SourcePosition(element, null, null);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Override
    public String toString() {
        final StringBuilder string = new StringBuilder("'");
        final Element enclosingElement = element.getEnclosingElement();
        if (enclosingElement != null && (enclosingElement.getKind().isClass() || enclosingElement.getKind().isInterface())) {
            string.append(enclosingElement.getSimpleName()).append("#");
        }
        if (element.getKind() == ElementKind.PACKAGE || element.getKind().isClass() || element.getKind().isInterface()) {
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
