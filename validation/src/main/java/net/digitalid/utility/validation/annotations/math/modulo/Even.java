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

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.interfaces.BigIntegerNumerical;
import net.digitalid.utility.interfaces.LongNumerical;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.validators.ModuloValidator;

/**
 * This annotation indicates that a numeric value is even.
 * 
 * @see Uneven
 */
@Documented
@Target(ElementType.TYPE_USE)
@ValueValidator(Even.Validator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Even {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends ModuloValidator {
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
            if (ProcessingUtility.isAssignable(element, BigIntegerNumerical.class)) {
                return Contract.with("# == null || #.getValue().getLowestSetBit() != 0", "The # has to be null or even but was $.", element);
            } else if (ProcessingUtility.isAssignable(element, BigInteger.class)) {
                return Contract.with("# == null || #.getLowestSetBit() != 0", "The # has to be null or even but was $.", element);
            } else if (ProcessingUtility.isAssignable(element, LongNumerical.class)) {
                return Contract.with("# == null || #.getValue() % 2 == 0", "The # has to be null or even but was $.", element);
            } else {
                return Contract.with("# % 2 == 0", "The # has to be even but was $.", element);
            }
        }
        
    }
    
}
