package net.digitalid.utility.freezable.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.validation.annotations.meta.MethodValidator;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;

/**
 * This annotation indicates that a method may only be called on {@link NonFrozen non-frozen} objects.
 * 
 * @see Freezable
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@MethodValidator(NonFrozenRecipient.Validator.class)
public @interface NonFrozenRecipient {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends MethodAnnotationValidator {
        
        @Pure
        @Override
        public @Nonnull Class<?> getReceiverType() {
            return Freezable.class;
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return Contract.with("!isFrozen()", "The method # may only be called on non-frozen objects.", element);
        }
        
    }
    
}
