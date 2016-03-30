package net.digitalid.utility.validation.validator;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * An annotation handler checks the use of and generates code for an annotation during annotation processing.
 * 
 * @see MethodAnnotationValidator
 * @see ValueAnnotationValidator
 * @see TypeAnnotationValidator
 */
@Stateless
public abstract class AnnotationHandler {
    
    /* -------------------------------------------------- Usage Check -------------------------------------------------- */
    
    /**
     * Checks whether the given annotation is used correctly on the given element.
     */
    @Pure
    public abstract void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror);
    
    /* -------------------------------------------------- Annotation Name -------------------------------------------------- */
    
    /**
     * Returns the name of the surrounding annotation.
     */
    @Pure
    public @Nonnull String getAnnotationName() {
        final @Nonnull String name = getClass().getName();
        return name.contains("$") ? name.substring(name.lastIndexOf('.') + 1, name.indexOf('$')) : name;
    }
    
    /**
     * Returns the name of the surrounding annotation with a leading at symbol.
     */
    @Pure
    public @Nonnull String getAnnotationNameWithLeadingAt() {
        return "@" + getAnnotationName();
    }
    
    /**
     * Returns the decamelized name of the surrounding annotation.
     * 
     * @see StringCase#decamelize(java.lang.String)
     */
    @Pure
    public @Nonnull String getDecamelizedAnnotationName() {
        return StringCase.decamelize(getAnnotationName());
    }
    
}
