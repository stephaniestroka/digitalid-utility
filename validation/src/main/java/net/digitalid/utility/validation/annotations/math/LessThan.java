package net.digitalid.utility.validation.annotations.math;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigInteger;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.validation.annotations.meta.TargetTypes;
import net.digitalid.utility.validation.annotations.meta.Validator;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.processing.ProcessingUtility;
import net.digitalid.utility.validation.processing.TypeImporter;
import net.digitalid.utility.validation.validator.AnnotationValidator;
import net.digitalid.utility.validation.validator.GeneratedContract;

/**
 * This annotation indicates that a numeric value is less than the given value.
 * 
 * @see GreaterThan
 * @see LessThanOrEqualTo
 * @see GreaterThanOrEqualTo
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Validator(LessThan.Validator.class)
@TargetTypes({long.class, int.class, short.class, byte.class, BigInteger.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface LessThan {
    
    /**
     * Returns the value which the annotated numeric value is less than.
     */
    long value();
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator extends AnnotationValidator {
        
        @Pure
        @Override
        public @Nonnull GeneratedContract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @Nonnull TypeImporter typeImporter) {
            if (ProcessingUtility.isAssignable(element, BigInteger.class)) {
                return GeneratedContract.with("# == null || #.compareTo(" + typeImporter.importIfPossible(BigInteger.class) + ".valueOf(@)) < 0", "The # has to be null or less than @ but was $.", element, annotationMirror);
            } else {
                return GeneratedContract.with("# < @", "The # has to be less than @ but was $.", element, annotationMirror);
            }
        }
        
    }
    
}
