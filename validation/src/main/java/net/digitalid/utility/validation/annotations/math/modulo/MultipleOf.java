package net.digitalid.utility.validation.annotations.math.modulo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigInteger;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.meta.Generator;
import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.generator.ContractGenerator;
import net.digitalid.utility.validation.interfaces.Numerical;
import net.digitalid.utility.validation.processing.ProcessingUtility;
import net.digitalid.utility.validation.processing.TypeImporter;

/**
 * This annotation indicates that a numeric value is a multiple of the given value.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Generator(MultipleOf.Generator.class)
@TargetTypes({long.class, int.class, short.class, byte.class, BigInteger.class, Numerical.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface MultipleOf {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the value of which the annotated numeric value is a multiple of.
     */
    long value();
    
    /* -------------------------------------------------- Generator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Generator extends ContractGenerator {
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            if (ProcessingUtility.isAssignable(element, Numerical.class)) {
                return Contract.with("# == null || #.getValue().mod(" + typeImporter.importIfPossible(BigInteger.class) + ".valueOf(@).equals(" + typeImporter.importIfPossible(BigInteger.class) + ".ZERO)) > 0", "The # has to be null or a multiple of @ but was $.", element, annotationMirror);
            } else if (ProcessingUtility.isAssignable(element, BigInteger.class)) {
                return Contract.with("# == null || #.mod(" + typeImporter.importIfPossible(BigInteger.class) + ".valueOf(@).equals(" + typeImporter.importIfPossible(BigInteger.class) + ".ZERO)) > 0", "The # has to be null or a multiple of @ but was $.", element, annotationMirror);
            } else {
                return Contract.with("# % @ == 0", "The # has to be a multiple of @ but was $.", element, annotationMirror);
            }
        }
        
    }
    
}
