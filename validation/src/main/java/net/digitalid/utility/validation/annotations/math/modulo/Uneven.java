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
 * This annotation indicates that a numeric value is uneven.
 * 
 * @see Even
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Generator(Uneven.Generator.class)
@TargetTypes({long.class, int.class, short.class, byte.class, BigInteger.class, Numerical.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Uneven {
    
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
                return Contract.with("# == null || #.getValue().getLowestSetBit() == 0", "The # has to be null or uneven but was $.", element);
            } else if (ProcessingUtility.isAssignable(element, BigInteger.class)) {
                return Contract.with("# == null || #.getLowestSetBit() == 0", "The # has to be null or uneven but was $.", element);
            } else {
                return Contract.with("# % 2 == 1", "The # has to be uneven but was $.", element);
            }
        }
        
    }
    
}
