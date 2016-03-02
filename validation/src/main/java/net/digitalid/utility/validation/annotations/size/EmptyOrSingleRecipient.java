package net.digitalid.utility.validation.annotations.size;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.MethodAnnotation;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.generator.ContractGenerator;
import net.digitalid.utility.validation.interfaces.Countable;
import net.digitalid.utility.validation.processing.TypeImporter;

/**
 * This annotation indicates that a method should only be invoked on {@link EmptyOrSingle empty or single} objects.
 */
@Documented
@Target(ElementType.METHOD)
@MethodAnnotation(Countable.class)
@Retention(RetentionPolicy.RUNTIME)
@Generator(EmptyOrSingleRecipient.Generator.class)
public @interface EmptyOrSingleRecipient {
    
    /* -------------------------------------------------- Generator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Generator extends ContractGenerator {
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            return Contract.with("size() <= 1", "The size of this countable has to be zero or one but was $.", element);
        }
        
    }
    
}
