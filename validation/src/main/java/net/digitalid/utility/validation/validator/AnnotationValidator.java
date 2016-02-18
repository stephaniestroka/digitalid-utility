package net.digitalid.utility.validation.validator;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

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
     * 
     */
    @Pure
    @Override
    public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
        // TODO: Implementation based on @TargetTypes. Overriden by annotation validators like @Immutable to ensure that no mutable methods are present.
    }
    
    /**
     * In case the element kind is method, use the variable 'result' in the condition.
     * TODO: Write the documentation here!
     */
    @Pure
    public @Nonnull GeneratedContract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
        return GeneratedContract.with("true", "This contract is always fulfilled.");
    }
    
    /* -------------------------------------------------- Utility -------------------------------------------------- */
    
}
