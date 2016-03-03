package net.digitalid.utility.validation.annotations.type.nesting;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;

import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.generator.ContractGenerator;
import net.digitalid.utility.validation.generators.NestingKindContractGenerator;
import net.digitalid.utility.validation.processing.TypeImporter;

/**
 * This annotation indicates that a class or type element has one of the given nesting kinds.
 * 
 * @see NestingKind
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Generator(NestingOf.Generator.class)
@TargetTypes({Class.class, TypeElement.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
public @interface NestingOf {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the nesting kinds one of which the annotated type or element has to have.
     */
    @Nonnull NestingKind[] value();
    
    /* -------------------------------------------------- Generator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Generator extends ContractGenerator {
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            final @Nonnull StringBuilder condition = new StringBuilder("# == null");
            for (@Nonnull NestingKind kind : element.getAnnotation(NestingOf.class).value()) {
                condition.append(" || ").append(NestingKindContractGenerator.getCondition(element, kind, typeImporter));
            }
            return Contract.with(condition.toString(), "The # has to be null or have one of the specified nesting kinds but was $.", element);
        }
        
    }
    
}
