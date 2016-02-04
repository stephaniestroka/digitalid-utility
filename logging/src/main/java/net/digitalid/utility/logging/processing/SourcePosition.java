package net.digitalid.utility.logging.processing;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

/**
 * This class models a position in the source code.
 */
public class SourcePosition {
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    /**
     * Stores the non-nullable element of this position.
     */
    private final Element element;
    
    /**
     * Returns the non-nullable element of this position.
     */
    public Element getElement() {
        return element;
    }
    
    /* -------------------------------------------------- Annotation Mirror -------------------------------------------------- */
    
    /**
     * Stores the nullable annotation mirror of this position.
     */
    private final AnnotationMirror annotationMirror;
    
    /**
     * Returns the nullable annotation mirror of this position.
     */
    public AnnotationMirror getAnnotationMirror() {
        return annotationMirror;
    }
    
    /* -------------------------------------------------- Annotation Value -------------------------------------------------- */
    
    /**
     * Stores the nullable annotation value of this position.
     */
    private final AnnotationValue annotationValue;
    
    /**
     * Returns the nullable annotation value of this position.
     */
    public AnnotationValue getAnnotationValue() {
        return annotationValue;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a source position with the given arguments.
     */
    protected SourcePosition(Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
        assert element != null : "The given element is not null.";
        assert annotationMirror != null || annotationValue == null : "If the given annotation mirror is null, the given annotation value is also null.";
        
        this.element = element;
        this.annotationMirror = annotationMirror;
        this.annotationValue = annotationValue;
    }
    
    /**
     * Returns a source position with the given non-nullable arguments.
     * 
     * @require element != null : "The given element is not null.";
     * @require annotationMirror != null : "The given annotation mirror is not null.";
     * @require annotationValue != null : "The given annotation Value is not null.";
     */
    public static SourcePosition of(Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
        assert element != null : "The given element is not null.";
        assert annotationMirror != null : "The given annotation mirror is not null.";
        assert annotationValue != null : "The given annotation Value is not null.";
        
        return new SourcePosition(element, annotationMirror, annotationValue);
    }
    
    /**
     * Returns a source position with the given non-nullable arguments.
     * 
     * @require element != null : "The given element is not null.";
     * @require annotationMirror != null : "The given annotation mirror is not null.";
     */
    public static SourcePosition of(Element element, AnnotationMirror annotationMirror) {
        assert element != null : "The given element is not null.";
        assert annotationMirror != null : "The given annotation mirror is not null.";
        
        return new SourcePosition(element, annotationMirror, null);
    }
    
    /**
     * Returns a source position with the given non-nullable element.
     * 
     * @require element != null : "The given element is not null.";
     */
    public static SourcePosition of(Element element) {
        assert element != null : "The given element is not null.";
        
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
