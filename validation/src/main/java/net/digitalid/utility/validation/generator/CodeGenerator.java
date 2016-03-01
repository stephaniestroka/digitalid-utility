package net.digitalid.utility.validation.generator;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.string.StringCase;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This interface marks classes that generate code during annotation processing.
 * 
 * @see ContractGenerator
 * @see TypeValidator
 */
@Stateless
public abstract class CodeGenerator {
    
    /* -------------------------------------------------- Usage -------------------------------------------------- */
    
    /**
     * Checks whether the given annotation is correctly used on the given element.
     */
    @Pure
    public abstract void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror);
    
    /* -------------------------------------------------- Utility -------------------------------------------------- */
    
    /**
     * Returns the name of the surrounding annotation.
     */
    @Pure
    public @Nonnull String getName() {
        final @Nonnull String name = getClass().getName();
        return name.contains("$") ? name.substring(name.lastIndexOf('.') + 1, name.indexOf('$')) : name;
    }
    
    /**
     * Returns the decamelized name of the surrounding annotation.
     * 
     * @see StringCase#decamelize(java.lang.String)
     */
    @Pure
    public @Nonnull String getDecamelizedName() {
        return StringCase.decamelize(getName());
    }
    
}
